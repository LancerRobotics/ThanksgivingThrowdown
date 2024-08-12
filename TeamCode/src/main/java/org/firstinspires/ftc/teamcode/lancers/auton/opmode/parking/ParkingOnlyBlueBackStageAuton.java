package org.firstinspires.ftc.teamcode.lancers.auton.opmode.parking;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lancers.LancersConstants;
import org.firstinspires.ftc.teamcode.lancers.auton.model.StartPosition;
import org.firstinspires.ftc.teamcode.lancers.auton.opmode.ParkingAutonOpMode;


/**
 * Bootstrap for {@link ParkingAutonOpMode}
 */
@Autonomous(preselectTeleOp = LancersConstants.TELEOP_NAME, group = "ParkingAuton")
public final class ParkingOnlyBlueBackStageAuton extends ParkingAutonOpMode {
    public ParkingOnlyBlueBackStageAuton() {
        super(StartPosition.BLUE_BACK_STAGE);
    }
}
