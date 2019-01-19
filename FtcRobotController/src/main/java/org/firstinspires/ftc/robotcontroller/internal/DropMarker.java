package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="DropMarker", group="CompAuto")
public class DropMarker extends BaseAuto {
    public void runOpMode() {
        initializeHardware();
        waitForStart();
        startAuto();
        stopDrive();
        driveInches(44, .6);
        runToPos(getTicks(60), getTicks(-60), -.4);
        dumpMarker();
        runToPos(getTicks(30), getTicks(-30), .7);
        driveInches(60, .6);
        runToPos(getTicks(5), getTicks(-5), .4);
        driveInches(4, .4);
        basketSwivel.setPower(.5);
        sleep(2000);
        basketSwivel.setPower(0);
    }

}
