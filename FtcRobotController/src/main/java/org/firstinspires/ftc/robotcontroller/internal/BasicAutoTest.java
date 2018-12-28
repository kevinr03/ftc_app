package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BasicAutoTest", group="Testing")
public class BasicAutoTest extends BaseAuto {

    double wheelDiameter = 4.0;
    int ticksPerRev = 1120;
    boolean isDriving = false;

    public void runOpMode() {


        initializeHardware();
        waitForStart();
        //extendLeadScrew();
        while (leadScrew.isBusy()) {
            telemetry.addData("Leadscrew Position:", leadScrew.getCurrentPosition());
            telemetry.update();
        }
        //leadScrew.setPower(0);
        driveInches(-3, -.6);
        while (leftFrontMotor.isBusy() || rightFrontMotor.isBusy()) {
            telemetry.addData("Left Motor Position:", leftFrontMotor.getCurrentPosition());
            telemetry.addData("Right Motor Position:", rightFrontMotor.getCurrentPosition());
        }
        stopDrive();



    }

}
