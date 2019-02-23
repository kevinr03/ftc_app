package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="LeadscrewTestAuto", group="")
public class FieldTestAuto extends BaseAuto {

    public void runOpMode() {
        leadScrew.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leadScrew.setTargetPosition(leadScrew.getCurrentPosition() + (ticksPerRev / 2));
        leadScrew.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leadScrew.setPower(.3);
        while (leadScrew.isBusy()) {}
        leadScrew.setPower(0);
    }

}
