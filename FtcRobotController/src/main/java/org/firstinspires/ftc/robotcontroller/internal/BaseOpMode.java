package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class BaseOpMode extends LinearOpMode
{
    //Declare Hardware
    public DcMotor armMotor;
    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;
    public DcMotor leadScrew;
    public CRServo collector;
    public CRServo flipper;

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
        flipper = hardwareMap.get(CRServo.class, "armServo2");
        collector = hardwareMap.get(CRServo.class, "collector");

        //Correct motor directions
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

}
