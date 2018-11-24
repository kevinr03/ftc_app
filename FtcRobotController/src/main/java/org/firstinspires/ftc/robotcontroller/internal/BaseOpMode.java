package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class BaseOpMode extends LinearOpMode {

    //Declare Hardware
    DcMotor armMotor;
    DcMotor leftFrontMotor;
    DcMotor leftBackMotor;
    DcMotor rightFrontMotor;
    DcMotor rightBackMotor;
    DcMotor leadScrew;
    CRServo armServo1;
    CRServo armServo2;
    Servo collector;

    /*Adjusts drive power by simply squaring the initial power. Squaring is close enough to the
    Desired exponential shift & a lot clearer, not to mention less work */
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

    public void initializeHardware () {
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
    }

}
