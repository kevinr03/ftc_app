package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FieldTestAuto", group="")
public class FieldTestAuto extends BaseAuto {

    public void runOpMode() {
        driveInches(5, .3);
        driveInches(-5, -.3);
    }

}
