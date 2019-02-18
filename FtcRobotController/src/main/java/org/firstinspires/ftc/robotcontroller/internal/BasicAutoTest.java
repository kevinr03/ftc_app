package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import static java.lang.Math.PI;

@Autonomous(name="BasicAutoTest", group="Testing")
public class BasicAutoTest extends BaseAuto {

    public void runOpMode() {

        addTelemetry("status: ", "Initializing");
        setupAuto();

        waitForStart();
        startAuto();
        stopDrive();
        int goldPos = scanMineral(3000);
        //driveInches(44, .6);
        telemetry.addData("GoldPos = ", goldPos);
        telemetry.update();
        if (goldPos == -1 || goldPos == -2) {
            //Gold Position Left
            driveInches(1, 1);
            runToPos(getTicks(14.5), -getTicks(14.5), 1);
            sleep(500);
            driveInches(40, .6);
        }
        else if (goldPos == 0) {
            //Gold Position Center
            runToPos(getTicks(1.17), 0, .6);
            driveInches(40, .6);
        }
        else if (goldPos == 1) {
            //Gold Position Left
            driveInches(1, 1);
            runToPos(0, getTicks(20), 1);
            driveInches(40, .6);
        }
    }
}
