package org.firstinspires.ftc.robotcontroller.internal;

/**
 * Created by kevinrockwell on 9/9/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="MOM", group="Competition Op Modes")
public class MainOpMode extends BaseOpMode
{

    public void runOpMode() {
        addTelemetry("Status:", "Initialising");

        initializeHardware();

        //Set driving variables
        boolean tankDrive, slowDrive, collectorOn;
        tankDrive = true;
        slowDrive = false;
        collectorOn = false;
        int slowDriveDelay = 0;
        int collectorDelay = 0;
        double left, right;
        double speedCorrection = 1;
        double servoPower = 1;
        double motorPower = 0.5;

        waitForStart();
        addTelemetry("Status:", "Starting OpMode");
        while (opModeIsActive()) {
            //Gamepad 2 controls arm stuff
            //TODO figure out if leadscrew should be gamepad 1 or 2
            if (gamepad2.a)
                armMotor.setPower(-motorPower);
            else if (gamepad2.b)
                armMotor.setPower(motorPower);
            else
                armMotor.setPower(0);

            if (gamepad2.dpad_up) {
                if (!collectorOn && collectorDelay == 0) {
                    collectorOn = true;
                    collectorDelay = 100;
                }
                else if (collectorOn && collectorDelay == 0) {
                    collectorDelay = 100;
                    collectorOn = false;
                }

            }

            if (gamepad1.right_bumper)
                leadScrew.setPower(1);
            else if (gamepad1.left_bumper)
                leadScrew.setPower(-1);
            else
                leadScrew.setPower(0);


            //Get Driving Modes
            if (gamepad1.dpad_up) {
                tankDrive = true;
                addTelemetry("Tank Drive", "True", 4);
            }
            else if (gamepad1.dpad_down) {
                tankDrive = false;
                addTelemetry("Single Drive", "True", 4);
            }



            if (gamepad1.dpad_right) {
                if (slowDrive && slowDriveDelay == 0) {
                    slowDrive = false;
                    speedCorrection = 1;
                    slowDriveDelay = 200;
                }
                else if (!slowDrive && slowDriveDelay == 0) {
                    slowDrive = true;
                    speedCorrection = 3.3;
                    slowDriveDelay = 200;
                }
            }

            if (tankDrive) {
                left = gamepad1.left_stick_y;
                right = gamepad1.right_stick_y;
            }
            else {
                double drive = gamepad1.left_stick_y;
                double turn = gamepad1.left_stick_x;

                // Combine drive and turn for blended motion.
                left = drive + turn;
                right = drive - turn;

                // Normalize the values so neither exceed +/- 1.0
                double maxPower = Math.max(Math.abs(left), Math.abs(right));
                if (maxPower > 1.0) {
                    left /= maxPower;
                    right /= maxPower;
                }
            }

            /*Adjust speeds to give finer control, by squaring previous power. Will reduce while
            -1≤power≤1. Since power must be <= 1 to work, this will make it a little easier to
            control driving (hopefully)
             */

            left = adjustPower(left);
            right = adjustPower(right);

            telemetry.addData("Left power: ", left);
            telemetry.addData("Right power: ", right);
            telemetry.addData("left_stick_y: ", gamepad1.left_stick_y);
            telemetry.addData("right_stick_x: ", gamepad1.right_stick_x);
            telemetry.update();

            leftBackMotor.setPower(left / speedCorrection);
            rightBackMotor.setPower(right / speedCorrection);
            leftFrontMotor.setPower(left / speedCorrection);
            rightFrontMotor.setPower(right / speedCorrection);
            // End code for driving
            if (slowDriveDelay > 0)
                slowDriveDelay -= 10;
            if (collectorDelay > 0)
                collectorDelay -= 10;
            sleep(10);
        }
    }
}
