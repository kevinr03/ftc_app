package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="LeadScrewTest", group="Testing")
public class LeadScrewTest extends LinearOpMode {
    private DcMotor motor;

    public void runOpMode () {
        telemetry.addData("Status:", "Initializing");
        telemetry.update();
        //declare hardware
        motor = hardwareMap.get(DcMotor.class, "motor");
        double motorPower = 0.5;
        waitForStart();
        telemetry.addData("Status:", "Starting");
        while (opModeIsActive()) {
            if (gamepad1.a)
                motor.setPower(motorPower);
            else if (gamepad1.b)
                motor.setPower(-motorPower);
            else
                motor.setPower(0);
        }
    }
}
