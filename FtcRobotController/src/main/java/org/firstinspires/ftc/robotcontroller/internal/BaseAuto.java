package org.firstinspires.ftc.robotcontroller.internal;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import static java.lang.Math.abs;

public abstract class BaseAuto extends BaseOpMode {

    String vuforiaKey = "AUAchNn/////AAAAGfqAcfY2+0TviBOpWNWvbFVO+Ki3ke54hx4bK3LAyMEOoMpSZ8pC6zWh" +
            "9BQwmaUwpR8FxMbNylft5qxYuRVSaA5ijKZj2Gd5F4m8TKzk9YD+ZTRH0T/bzvhZLMr1IEnUKN0wyLqGqQqv" +
            "I05qNqNahVd9OAHgy+MnrcWfrF1Ta1GUzQGc18K2qC7mioQFIJhc/KMCaFhmOer2sjtmxIp/kak0iDJfp77f" +
            "/8kWvyV2IlnlR187HHWg1mgF9ZZspTYArFZa150FozF7PF7cR9xOuQZT7LuiwO/Ia64M/qa4vcOTlcHVtz6C" +
            "VVC54KW1AAhQEg3p5kkG1hGbHJtvGovp7PKfragvZascLTnkCt4XK28C";

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;

    //For Driving Functions
    double wheelDiameter = 4.0;
    int ticksPerRev = 1120;
    boolean isDriving = false;

    //for tfod
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    public void setupAuto() {
        initializeHardware();
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Vuforia Setup
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = vuforiaKey;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        //tfod setup
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public void runToPos(int rticks, int lticks, double power) {
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int leftTarget = leftFrontMotor.getCurrentPosition() + lticks;
        int rightTarget = rightFrontMotor.getCurrentPosition() + rticks;

        telemetry.addData("Left Target:", leftTarget);
        telemetry.addData("Right Target:", rightTarget);
        telemetry.addData("Power:", power);
        telemetry.update();

        rightFrontMotor.setPower(power);
        leftFrontMotor.setPower(power);
        rightBackMotor.setPower(power);
        leftBackMotor.setPower(power);

        while (abs(rightFrontMotor.getCurrentPosition()) < abs(rightTarget) ||
                abs(leftFrontMotor.getCurrentPosition()) < abs(leftTarget)) {
            if (!(abs(rightFrontMotor.getCurrentPosition()) < abs(rightTarget))) {
                rightFrontMotor.setPower(0);
                rightBackMotor.setPower(0);
            } else if (!(abs(leftFrontMotor.getCurrentPosition()) < abs(leftTarget))) {
                leftFrontMotor.setPower(0);
                leftBackMotor.setPower(0);
            }
            sleep(10);
        }
        stopDrive();
    }

    private void driveTicks(int ticks, double power) {
        runToPos(ticks, ticks, power);
    }

    public void driveInches(double inches, double power) {
        double rotations = inches * wheelDiameter * Math.PI;
        int ticks = (int) rotations * ticksPerRev;
        driveTicks(ticks, power);
    }

    public void stopDrive() {
        rightBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
    }

}
