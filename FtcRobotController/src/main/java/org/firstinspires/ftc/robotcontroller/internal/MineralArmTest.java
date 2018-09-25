package org.firstinspires.ftc.robotcontroller.internal;

/**
 * Created by kevinrockwell on 9/9/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="MineralArmTest", group="Testing")
public class MineralArmTest extends LinearOpMode {

    //Declare Hardware
    private DcMotor motor;
    private CRServo servo1;
    private CRServo servo2;
    private Servo collector;

    public void runOpMode() {
        double power = 0.5;
        double collectorPos = 0.5;
        telemetry.addData("Status:", "Initialising");
        telemetry.update();
        //Initialise Hardware
        motor = hardwareMap.get(DcMotor.class, "motor");
        servo1 = hardwareMap.get(CRServo.class, "servo1");
        servo2 = hardwareMap.get(CRServo.class, "servo2");
        collector = hardwareMap.get(Servo.class, "collector");
        collector.setPosition(collectorPos);

        waitForStart();
        telemetry.addData("Status:", "Starting OpMode");
        telemetry.update();
        while (opModeIsActive()) {
            if (gamepad1.a)
                motor.setPower(power);
            else if (gamepad1.b)
                motor.setPower(-power);
            else
                motor.setPower(0);

            if (gamepad1.dpad_left)
                servo1.setPower(power);
            else if (gamepad1.dpad_right)
                servo1.setPower(-power);
            else
                servo1.setPower(0);

            if (gamepad1.dpad_up)
                servo2.setPower(power);
            else if (gamepad1.dpad_down)
                servo2.setPower(-power);
            else
                servo2.setPower(0);

            if (gamepad1.right_trigger > 0.1) {
                if (collectorPos < 1) {
                    collector.setPosition(collectorPos + 0.05);
                    collectorPos += 0.05;
                }
            }
            else if (gamepad1.left_trigger > 0.1) {
                if (collectorPos > 0) {
                    collector.setPosition(collectorPos - 0.05);
                    collectorPos -= 0.05;
                }
            }
            sleep(10);
        }
    }
}
