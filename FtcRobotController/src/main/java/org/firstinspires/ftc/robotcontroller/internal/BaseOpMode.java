package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class BaseOpMode extends LinearOpMode
{

    //Declare Hardware
    DcMotor mineralLifter;
    DcMotor basketSwivel;
    DcMotor basketExtension;
    DcMotor leftFrontMotor;
    DcMotor leftBackMotor;
    DcMotor rightFrontMotor;
    DcMotor rightBackMotor;
    DcMotor leadScrew;
    CRServo collector;
    CRServo flipper;

    /*Adjusts drive power by simply squaring the initial power. Squaring is close enough to the
    Desired exponential shift & a lot clearer, not to mention less work */
    double adjustPower(double power) {
        if (power < 0)
            return -(power * power);
        else
            return power * power;

    }

    void addTelemetry(String caption, String value, int... times) {
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

    void initializeHardware () {
        //Initialize Hardware
        basketSwivel = hardwareMap.get(DcMotor.class, "basketSwivel");
        basketExtension = hardwareMap.get(DcMotor.class, "basketExtension");
        mineralLifter = hardwareMap.get(DcMotor.class, "mineralLifter");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leadScrew = hardwareMap.get(DcMotor.class, "leadScrew");
        flipper = hardwareMap.get(CRServo.class, "flipper");
        collector = hardwareMap.get(CRServo.class, "collector");



        //Correct motor & CR Servo directions
        mineralLifter.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        collector.setDirection(DcMotorSimple.Direction.REVERSE);
    }

}
