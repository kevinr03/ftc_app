package org.firstinspires.ftc.robotcontroller.internal;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import static java.lang.Math.abs;

public abstract class BaseAuto extends BaseOpMode {

    public String vuforiaKey = "AUAchNn/////AAAAGfqAcfY2+0TviBOpWNWvbFVO+Ki3ke54hx4bK3LAyMEOoMpSZ8pC6zWh" +
            "9BQwmaUwpR8FxMbNylft5qxYuRVSaA5ijKZj2Gd5F4m8TKzk9YD+ZTRH0T/bzvhZLMr1IEnUKN0wyLqGqQqv" +
            "I05qNqNahVd9OAHgy+MnrcWfrF1Ta1GUzQGc18K2qC7mioQFIJhc/KMCaFhmOer2sjtmxIp/kak0iDJfp77f" +
            "/8kWvyV2IlnlR187HHWg1mgF9ZZspTYArFZa150FozF7PF7cR9xOuQZT7LuiwO/Ia64M/qa4vcOTlcHVtz6C" +
            "VVC54KW1AAhQEg3p5kkG1hGbHJtvGovp7PKfragvZascLTnkCt4XK28C";

    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;

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
        double lpower, rpower;
        telemetry.addLine("Run To Pos:");
        telemetry.addData("Left Ticks:", lticks);
        telemetry.addData("Right Ticks:", rticks);
        power = abs(power);
        telemetry.addData("Power", power);
        telemetry.update();
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Left Current Pos:", leftFrontMotor.getCurrentPosition());
        telemetry.addData("Right Current Pos", rightFrontMotor.getCurrentPosition());
        telemetry.update();
        int leftTarget = leftFrontMotor.getCurrentPosition() + lticks;
        int rightTarget = rightFrontMotor.getCurrentPosition() + rticks;

        if (rticks < 0)
            rpower = -power;
        else
            rpower = power;
        if (lticks < 0)
            lpower = -power;
        else
            lpower = power;
        telemetry.addData("Left Target:", leftTarget);
        telemetry.addData("Right Target:", rightTarget);
        telemetry.addData("Power:", power);
        telemetry.update();

        rightFrontMotor.setPower(rpower);
        rightBackMotor.setPower(rpower);
        leftFrontMotor.setPower(lpower);
        leftBackMotor.setPower(lpower);

        while (abs(rightFrontMotor.getCurrentPosition()) < abs(rightTarget) ||
                abs(leftFrontMotor.getCurrentPosition()) < abs(leftTarget)) {
            telemetry.addData("Left Current Pos:", leftFrontMotor.getCurrentPosition());
            telemetry.addData("Right Current Pos:", rightFrontMotor.getCurrentPosition());
            telemetry.update();
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
        telemetry.addData("Ticks", ticks);
        telemetry.addData("Power", power);
        telemetry.update();
        runToPos(ticks, ticks, power);
    }

    public int getTicks(double inches) {
        double rotations;
        int ticks;
        rotations = inches / (wheelDiameter * Math.PI);
        ticks = (int) (rotations * ticksPerRev);
        return ticks;
    }

    public void driveInches(double inches, double power) {
        int ticks = getTicks(inches);
        driveTicks(ticks, power);
    }

    public void turn(double degrees, double power) {
        //TODO
    }

    public void stopDrive() {
        rightBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
    }

    public void extendLeadScrew() {
        leadScrew.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leadScrew.setTargetPosition(8350); //DO NOT CHANGE VALUE
        leadScrew.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leadScrew.setPower(1);
        while (leadScrew.isBusy()) {}
        leadScrew.setPower(0);
    }

    public void startAuto() {
        extendLeadScrew();
        driveInches(-1.75, 0.3);
        runToPos(getTicks(-3.5), getTicks(3.5), .6);
        driveInches(-2, .3);
        runToPos(getTicks(33), getTicks(-33), .6);
    }

}
