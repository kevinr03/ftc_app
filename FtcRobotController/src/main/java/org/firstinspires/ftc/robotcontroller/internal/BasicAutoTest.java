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
        scanMineral(5000);
        //driveInches(44, .6);

    }
}
