package org.firstinspires.ftc.teamcode.Autonomous;

import java.util.*;
import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import Cryptic.OurRobot;

@TeleOp(name="allenTest")
public class autonomous1 extends LinearOpMode {
    OurRobot robot = new OurRobot();
    @Override
    public void runOpMode() throws InterruptedException {
        OurRobot.initialize(this);

        waitForStart();

        ElapsedTime timer = new ElapsedTime();

        telemetry.addData("Status", "Running");
        telemetry.update();
        timer.reset();
        while (timer.milliseconds() < 10000) {
            robot.dt.rightFront.setPower(0.7);
            robot.dt.leftFront.setPower(0.7);
            robot.dt.rightBack.setPower(0.7);
            robot.dt.leftBack.setPower(0.7);
        }
    }
}