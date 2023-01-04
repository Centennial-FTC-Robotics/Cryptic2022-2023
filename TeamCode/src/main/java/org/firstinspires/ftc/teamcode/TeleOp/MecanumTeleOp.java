package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Cryptic.OurRobot;
import Cryptic.Subsystems.Drivetrain;

@TeleOp(name="First OpMode")
public class MecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        OurRobot robot = new OurRobot();
        robot.initialize(this);

        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){
            double y = -gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double leftFrontPower = (y + x + rx) / denominator;
            double leftBackPower = (y - x + rx) / denominator;
            double rightFrontPower = (y - x - rx) / denominator;
            double rightBackPower = (y + x - rx) / denominator;
            robot.dt.rightFront.setPower(rightFrontPower);
            robot.dt.rightBack.setPower(rightBackPower);
            robot.dt.leftBack.setPower(leftBackPower);
            robot.dt.leftFront.setPower(leftFrontPower);
        }
    }
}