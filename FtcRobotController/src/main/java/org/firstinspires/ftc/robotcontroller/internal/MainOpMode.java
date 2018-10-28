package org.firstinspires.ftc.robotcontroller.internal;

/**
 * Created by kevinrockwell on 9/9/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="MOM", group="Competition Op Modes")
public class MainOpMode extends LinearOpMode {

    //Declare Hardware
    private DcMotor armMotor;
    private DcMotor leftFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;
    private DcMotor leadScrew;
    private CRServo armServo1;
    private CRServo armServo2;
    private Servo collector;

    //Adjusts drive power
    public double adjustPower(double power) {
        if (power < 0)
            return -(power * power);
        else
            return power * power;

    }

    public void addTelemetry(String caption, String value, int... times) {
        if (times.length > 0) {
            for (int n : times) {
                while (n-- > 0)
                    telemetry.addData(caption, value);
            }
        }
        else
            telemetry.addData(caption, value);
        telemetry.update();
    }

    public void runOpMode() {
        addTelemetry("Status:", "Initialising");

        //Initialise Hardware
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leadScrew = hardwareMap.get(DcMotor.class, "leadScrew");
        armServo1 = hardwareMap.get(CRServo.class, "armServo1");
        armServo2 = hardwareMap.get(CRServo.class, "armServo2");
        collector = hardwareMap.get(Servo.class, "collector");

        //Correct motor directions
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set driving variables
        boolean tankDrive, slowDrive;
        tankDrive = true;
        slowDrive = false;
        int delayCount = 0;
        double left, right;
        double speedCorrection = 1;
        double servoPower = 1;
        double motorPower = 0.5;
        double collectorPos = 0.5;
        collector.setPosition(collectorPos);

        waitForStart();
        addTelemetry("Status:", "Starting OpMode");
        while (opModeIsActive()) {
            if (gamepad2.a)
                armMotor.setPower(-motorPower);
            else if (gamepad2.b)
                armMotor.setPower(motorPower);
            else
                armMotor.setPower(0);

            if (gamepad2.dpad_left)
                armServo1.setPower(servoPower);
            else if (gamepad2.dpad_right)
                armServo1.setPower(servoPower);
            else
                armServo1.setPower(0);

            if (gamepad2.dpad_up)
                armServo2.setPower(servoPower);
            else if (gamepad2.dpad_down)
                armServo2.setPower(-servoPower);
            else
                armServo2.setPower(0);

            if (gamepad2.right_trigger > 0.0) {
                if (collectorPos < 1) {
                    collector.setPosition(collectorPos + (0.01 * gamepad2.right_trigger));
                    collectorPos = collector.getPosition();
                }
            }
            else if (gamepad2.left_trigger > 0.0) {
                if (collectorPos > 0) {
                    collector.setPosition(collectorPos - (0.01 * gamepad2.left_trigger));
                    collectorPos = collector.getPosition();
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
                if (slowDrive && delayCount == 0) {
                    slowDrive = false;
                    speedCorrection = 1;
                    delayCount = 200;
                }
                else if (!slowDrive && delayCount == 0) {
                    slowDrive = true;
                    speedCorrection = 3.3;
                    delayCount = 200;
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

            /*Adjust speeds to give finer control, by squaring previous power. Will reduce until
            power = 1, since power must be <= 1 to work, this will make it a little easier to
            control.
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

            delayCount -= 10;
            sleep(10);
        }
    }
}
