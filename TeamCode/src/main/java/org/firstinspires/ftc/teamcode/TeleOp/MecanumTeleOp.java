package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Cryptic.OurRobot;
import Cryptic.Subsystems.Drivetrain;

@TeleOp(name="First OpMode")
public class MecanumTeleOp extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        OurRobot robot = new OurRobot();
        robot.initialize(this);

        waitForStart();
        boolean intake = true;
        if (isStopRequested()) return;
        int counter = 0;
        double slideValue = 0;
        while(opModeIsActive()){
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
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
            telemetry.addData("Servo Position", robot.outtake.outtakeServo.getPosition());
            telemetry.update();
            if (gamepad2.b) {
                    robot.outtake.outtakeServo.setPosition(0.7);
            } else {
                robot.outtake.outtakeServo.setPosition(0.3);
            }
            if(gamepad2.dpad_up)
                slideValue = 0.5;
            else if(gamepad2.dpad_down)
                slideValue = -0.5;
            else{
                slideValue = 0;
            }
            robot.outtake.outtakeMotor.setPower(slideValue);
            OurRobot.dt.leftFront.setPower(leftFrontPower);
            robot.intake.leftPivotServo.setPosition(0);

            if(gamepad2.a){ //intake
                if(intake){
                    robot.intake.leftPivotServo.setPosition(0.7);
                    robot.intake.rightPivotServo.setPosition(0.7);
                    robot.intake.clawServo.setPosition(1);
                } else {
                    OurRobot.intake.leftPivotServo.setPosition(0);
                    robot.intake.rightPivotServo.setPosition(0);
                    robot.intake.clawServo.setPosition(0);
                }
                if(!gamepad2.a)
                    intake = !intake;
                telemetry.addData(intake.toString(), 3);
                telemetry.update();

            }
        }
    }
}