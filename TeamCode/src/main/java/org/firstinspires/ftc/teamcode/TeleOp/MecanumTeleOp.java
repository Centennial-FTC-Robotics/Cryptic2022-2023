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
        //inital servo positions
        robot.intake.leftPivotServo.setPosition(0.1);
        robot.intake.rightPivotServo.setPosition(0.9);
        robot.intake.clawServo.setPosition(0.5);
        waitForStart();
        boolean intakeTog = true;
        if (isStopRequested()) return;
        boolean intakeArmLock = false;
        double slideValue = 0;
        double intakeRight = 0.9;
        double intakeLeft = 0.1;
        double claw = 0.5;
        boolean clawTog = true;
        boolean clawLock = false;
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

            //outtake
            telemetry.addData("Servo Position", robot.outtake.outtakeServo.getPosition());

            if (gamepad2.y) {
                robot.outtake.outtakeServo.setPosition(0.7);
            } else {
                robot.outtake.outtakeServo.setPosition(0.1);
            }
            //slides
            if(gamepad2.dpad_up)
                slideValue = 0.5;
            else if(gamepad2.dpad_down)
                slideValue = -0.5;
            else{
                slideValue = 0;
            }
            robot.outtake.outtakeMotor.setPower(slideValue);

            //intake pivot
            if (gamepad2.a){
                intakeArmLock = true;
            }

            if (intakeArmLock && !gamepad2.a) {
                intakeArmLock = false;
                if (intakeTog) {
                    intakeLeft = 0.95;
                    intakeRight = 0.05;
                    intakeTog = false;
                } else {
                    intakeLeft = 0.1;
                    intakeRight = 0.9;
                    intakeTog = true;
                }

            }
            robot.intake.leftPivotServo.setPosition(intakeLeft);
            robot.intake.rightPivotServo.setPosition(intakeRight);

            //claw
            if (gamepad2.b){
                clawLock = true;
            }

            if (clawLock && !gamepad2.b) {
                clawLock = false;
                if (clawTog) {
                    claw = 0.75;

                    clawTog = false;
                } else {
                    claw = 0.5;
                    clawTog = true;
                }

            }
            robot.intake.clawServo.setPosition(claw);
            telemetry.update();
        }
    }
}