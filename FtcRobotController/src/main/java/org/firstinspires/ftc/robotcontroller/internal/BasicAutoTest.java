package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="BasicAutoTest", group="Testing")
public class BasicAutoTest extends LinearOpMode {

    DcMotor leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor, leadScrew;
    double wheelDiameter = 4.0;
    int ticksPerRev = 1120;
    boolean isDriving = false;

    public void runOpMode() {

        //initialize hardware
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leadScrew = hardwareMap.get(DcMotor.class, "leadScrew");
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (leftFrontMotor.isBusy() || rightFrontMotor.isBusy()) {
            driveInches(4.0, 0.3);
            finishDrive();
        }


    }
    private void driveTicks(int ticks, double power) {
        leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
        rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(power);
        rightFrontMotor.setPower(power);
        leftBackMotor.setPower(power);
        rightBackMotor.setPower(power);
        isDriving = true;

    }

    public void driveInches(double inches, double power) {
        double rotations = inches * wheelDiameter * Math.PI;
        int ticks = (int) rotations * ticksPerRev;
        driveTicks(ticks, power);
    }

    private void stopDrive() {
        rightBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
    }

    public void finishDrive() {
        if (isDriving) {
            if (!rightFrontMotor.isBusy()) {
                rightFrontMotor.setPower(0);
                rightBackMotor.setPower(0);
            }
            if (!leftFrontMotor.isBusy()) {
                leftFrontMotor.setPower(0);
                leftBackMotor.setPower(0);
            }
            if (!leftFrontMotor.isBusy() && !rightFrontMotor.isBusy()) {
                stopDrive();
            }
        }
        else
            stopDrive();

    }
}
