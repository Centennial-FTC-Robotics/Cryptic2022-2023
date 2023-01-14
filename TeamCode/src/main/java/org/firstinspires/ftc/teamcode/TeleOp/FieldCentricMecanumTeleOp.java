package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Cryptic.OurRobot;

@TeleOp(name="Field Centric Mecanum TeleOp")
public class FieldCentricMecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        OurRobot robot = new OurRobot();
        robot.initialize(this);

        robot.dt.leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.dt.rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.dt.leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.dt.rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        if (isStopRequested()) return;

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Read inverse IMU heading, as the IMU heading is CW positive
            double botHeading = robot.imu.imu.getAngularOrientation().firstAngle;

            double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
            double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            robot.dt.leftFront.setPower(frontLeftPower);
            robot.dt.leftBack.setPower(backLeftPower);
            robot.dt.rightFront.setPower(frontRightPower);
            robot.dt.rightBack.setPower(backRightPower);
        }
    }
}