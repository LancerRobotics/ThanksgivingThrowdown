package org.firstinspires.ftc.teamcode.lancers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;

@TeleOp()
public class LancersTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException  {
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            final double speedMultiplier = gamepad1.a ? 1.0d : 0.8d;

            final DcMotor leftFront = hardwareMap.dcMotor.get(LancersBotConfig.FRONT_LEFT_MOTOR);
            final DcMotor leftRear = hardwareMap.dcMotor.get(LancersBotConfig.BOTTOM_LEFT_MOTOR);
            final DcMotor rightFront = hardwareMap.dcMotor.get(LancersBotConfig.FRONT_RIGHT_MOTOR);
            final DcMotor rightRear = hardwareMap.dcMotor.get(LancersBotConfig.BOTTOM_RIGHT_MOTOR);

            final DcMotor slidesMotor = hardwareMap.dcMotor.get(LancersBotConfig.SLIDES_MOTOR);
            final Servo clawServo = hardwareMap.servo.get(LancersBotConfig.CLAW_SERVO);

            // Gamepad positions; Motors are swapped
            final double ly = -gamepad1.left_stick_y * speedMultiplier; // Remember, Y stick value is reversed
            final double lx = gamepad1.left_stick_x * speedMultiplier; // Counteract imperfect strafing
            final double rx = gamepad1.right_stick_x * speedMultiplier;

            final double spy = gamepad2.left_trigger;
            final double sny = gamepad2.right_trigger;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            final double denominator = Math.max(Math.abs(ly) + Math.abs(lx) + Math.abs(rx), 1);

            final double frontLeftPower = (ly + lx + rx) / denominator;
            final double backLeftPower = (ly - lx + rx) / denominator;
            final double frontRightPower = (ly - lx - rx) / denominator;
            final double backRightPower = (ly + lx - rx) / denominator;

            double slidesPower = 0.0d;
            final float TRIGGER_THRESHOLD = 0.15f;

            if (spy > TRIGGER_THRESHOLD){
                //telemetry.addData("SPY USED!!!", spy);
                slidesPower = spy;
                slidesPower = (spy - TRIGGER_THRESHOLD) * (1f / (1f - TRIGGER_THRESHOLD));
            }
            if (sny > TRIGGER_THRESHOLD){
                //telemetry.addData("SNY USED!!!", sny);
                slidesPower =  -(sny - TRIGGER_THRESHOLD) * (1f / (1f - TRIGGER_THRESHOLD));
            }

            double currentServoPosition = clawServo.getPosition();
            final double openServoPosition = 0;
            final double closeServoPosition = 0.5;
            final double servoSpeed = 0.01;

            if ((gamepad2.a) && currentServoPosition < closeServoPosition){
                currentServoPosition += servoSpeed;
            }
            else if ((gamepad2.b) && currentServoPosition > openServoPosition){
                currentServoPosition -= servoSpeed;
            }

            currentServoPosition = Range.clip(currentServoPosition, openServoPosition, closeServoPosition);

            clawServo.setPosition(currentServoPosition);

            leftFront.setPower(-frontLeftPower);
            leftRear.setPower(-backLeftPower);
            rightFront.setPower(frontRightPower);
            rightRear.setPower(backRightPower);

            slidesMotor.setPower(slidesPower);
        }
    }
}