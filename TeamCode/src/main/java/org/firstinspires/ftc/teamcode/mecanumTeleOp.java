package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="First OpMode")
public class mecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        while(opModeIsActive()){
            robot robot = new robot();
            robot.initialize(this);
            waitForStart();
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double leftFrontPower = (y + x + rx) / denominator;
            double leftBackPower = (y - x + rx) / denominator;
            double rightFrontPower = (y - x - rx) / denominator;
            double rightBackPower = (y + x - rx) / denominator;
            robot.rightFront.setPower(rightFrontPower);
            robot.rightBack.setPower(rightBackPower);
            robot.leftBack.setPower(leftBackPower);
            robot.leftFront.setPower(leftFrontPower);
        }
    }
}
