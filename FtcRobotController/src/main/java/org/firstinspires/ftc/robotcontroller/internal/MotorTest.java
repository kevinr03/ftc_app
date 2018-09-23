package org.firstinspires.ftc.robotcontroller.internal;

/**
 * Created by kevinrockwell on 9/9/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="MotorTest", group="Testing")
public class MotorTest extends LinearOpMode {

    //Declare Hardware
    private DcMotor motor;


    public void runOpMode() {
        double power = 0.5;
        telemetry.addData("Status:", "Initialising");

        //Initialise Hardware
        motor = hardwareMap.get(DcMotor.class, "motor");

        while (opModeIsActive()) {
            if (gamepad1.a) {
                motor.setPower(power);
                sleep(3000);
                motor.setPower(0);
            }
            else if (gamepad1.b) {
                motor.setPower(-power);
                sleep(3000);
                motor.setPower(0);
            }
        }
    }
}
