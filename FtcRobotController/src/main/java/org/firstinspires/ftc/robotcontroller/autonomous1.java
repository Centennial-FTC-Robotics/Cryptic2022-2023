package org.firstinspires.ftc.robotcontroller;

import java.util.*;
import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="allenTest")
public class autonomous1 extends LinearOpMode {
    private DcMotor rightBack, rightFront, leftBack, leftFront;
    @Override
    public void runOpMode(){
        //do stuff
        rightBack = hardwareMap.get(DcMotor.class, "rb");
        rightFront = hardwareMap.get(DcMotor.class, "rf");
        leftBack = hardwareMap.get(DcMotor.class, "lb");
        leftFront = hardwareMap.get(DcMotor.class, "lf");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
//        rightBack.setPower(1);
//        rightFront.setPower(1);
//        leftBack.setPower(1);
//        leftFront.setPower(1);
        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            rightBack.setPower(-0.7);
            rightFront.setPower(0.7);
            leftBack.setPower(-0.7);
            leftFront.setPower(0.7);

        }

    }
}