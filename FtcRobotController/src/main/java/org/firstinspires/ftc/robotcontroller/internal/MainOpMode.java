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
        boolean tankDrive, slowDrive, collectorUp, collectorDown;
        tankDrive = true;
        slowDrive = collectorUp = collectorDown = false;
        int slowDriveDelay = 0;
        int collectorUpDelay = 0;
        int collectorDownDelay = 0;
        double left, right;
        double speedCorrection = 1;
        double servoPower = 1;
        double motorPower = 0.5;

        waitForStart();
        addTelemetry("Status:", "Starting OpMode");
        while (opModeIsActive()) {
            //Gamepad 2 controls arm stuff

            if (gamepad2.a) {
                mineralLifter.setPower(-0.5);
            } else if (gamepad2.y) {
                mineralLifter.setPower(0.5);
            } else {
                mineralLifter.setPower(0);
            }

            if (gamepad2.x) {
                basketExtension.setPower(0.5);
            } else if (gamepad2.b) {
                basketExtension.setPower(-0.5);
            } else {
                basketExtension.setPower(0);
            }

            if (gamepad2.right_bumper) {
                basketSwivel.setPower(0.5);
            } else if (gamepad2.left_bumper) {
                basketSwivel.setPower(-0.5);
            } else {
                basketSwivel.setPower(0);
            }

            if (gamepad2.right_trigger > 0.5) {
                collector.setPower(1);
            } else if (gamepad2.left_trigger > 0.5) {
                collector.setPower(-1);
            } else {
                collector.setPower(0);
            }


            //TODO Flipper code here (triggers)
            //Flipper code
            if (gamepad2.right_trigger > 0)
                flipper.setPower(adjustPower(gamepad2.right_trigger));
            else if (gamepad2.left_trigger > 0)
                flipper.setPower(-adjustPower(gamepad2.left_trigger));
            else
                flipper.setPower(0);

            //Collector control
            if (gamepad2.dpad_up) {
                if (collectorDown || (!collectorUp && collectorUpDelay == 0)) {
                    collectorUp = true;
                    collectorDown = false;
                    collectorUpDelay = 150;
                    collectorDownDelay = 0;
                    collector.setPower(servoPower);
                } else if (collectorUp && collectorUpDelay == 0) {
                    collectorUpDelay = 150;
                    collectorUp = false;
                    collector.setPower(0);
                }
            } else if (gamepad2.dpad_down) {
                if (collectorUp || (!collectorDown && collectorDownDelay == 0)) {
                    collectorDown = true;
                    collectorUp = false;
                    collectorDownDelay = 150;
                    collectorUpDelay = 0;
                    collector.setPower(-servoPower);
                } else if (collectorDown && collectorDownDelay == 0) {
                    collectorDown = false;
                    collectorDownDelay = 150;
                    collector.setPower(0);
                }
            }

        }

        //Gamepad 1 does the other stuff
        if (gamepad1.right_bumper)
            leadScrew.setPower(-1);
        else if (gamepad1.left_bumper)
            leadScrew.setPower(1);
        else
            leadScrew.setPower(0);


        //Get Driving Modes
        if (gamepad1.dpad_up) {
            tankDrive = true;
            addTelemetry("Tank Drive", "True", 4);
        } else if (gamepad1.dpad_down) {
            tankDrive = false;
            addTelemetry("Single Drive", "True", 4);
        }


        if (gamepad1.dpad_right) {
            if (slowDrive && slowDriveDelay == 0) {
                slowDrive = false;
                speedCorrection = 1;
                slowDriveDelay = 200;
            } else if (!slowDrive && slowDriveDelay == 0) {
                slowDrive = true;
                speedCorrection = 3.3;
                slowDriveDelay = 200;
            }
        }

        if (tankDrive) {
            left = getLeft();
            right = getRight();
        } else {
            double drive = gamepad1.left_stick_y;
            double turn = getLeft();

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
        telemetry.addData("right_stick_y: ", gamepad1.right_stick_y);
        telemetry.update();

        leftBackMotor.setPower(left / speedCorrection);
        rightBackMotor.setPower(right / speedCorrection);
        leftFrontMotor.setPower(left / speedCorrection);
        rightFrontMotor.setPower(right / speedCorrection);
        // End code for driving
        if (slowDriveDelay > 0)
            slowDriveDelay -= 10;
        if (collectorUpDelay > 0)
            collectorUpDelay -= 10;
        if (collectorDownDelay > 0)
            collectorDownDelay -= 10;
        sleep(10);
    }
}

