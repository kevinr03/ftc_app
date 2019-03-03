package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="CraterSide", group="Testing")
public class CraterSide extends BaseAuto {

    public void runOpMode() {

        addTelemetry("status: ", "Initializing");
        setupAuto();

        waitForStart();
        startAuto();
        stopDrive();
        int goldPos = scanMineral(3000);

        telemetry.addData("GoldPos = ", goldPos);
        telemetry.update();
        if (goldPos == -1 || goldPos == -2) {
            //Gold Position Left
            dumpMarker();
            driveInches(1, 1);
            runToPos(getTicks(14.5), -getTicks(14.5), 1);
            driveInches(30, .6);

        }
        else if (goldPos == 0) {
            //Gold Position Center
            dumpMarker();
            driveInches(-1, -1);
            runToPos(getTicks(3), -getTicks(3), 1);
            driveInches(27, .6);
        }
        else if (goldPos == 1) {
            //Gold Position Left
            dumpMarker();
            driveInches(1, 1);
            runToPos(-getTicks(3), getTicks(3), 1);
            driveInches(33, .6);
        }
    }
}
