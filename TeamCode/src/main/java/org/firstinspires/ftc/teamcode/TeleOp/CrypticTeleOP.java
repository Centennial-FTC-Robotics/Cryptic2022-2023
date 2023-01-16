package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import Cryptic.OurRobot;
import Cryptic.Subsystems.Drivetrain;

@TeleOp(name="CrypticTeleOP")
public class CrypticTeleOP extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        OurRobot robot = new OurRobot();
        robot.initialize(this);
        //inital servo positions
        robot.intake.leftPivotServo.setPosition(0.95);
        robot.intake.rightPivotServo.setPosition(0.05);
        robot.intake.clawServo.setPosition(0.77);
        robot.intake.horiServo.setPosition(0.05);
        waitForStart();
        boolean intakeTog = true;
        if (isStopRequested()) return;
        boolean intakeArmLock = false;
        double slideValue = 0;
        double intakeRight = 0.05;
        double intakeLeft = 0.95;
        double claw = 0.77;
        boolean clawTog = true;
        boolean clawLock = false;
        double horiSlide = 0.05;
        double factor;
        boolean timerReached = true;
        boolean setUp = true;
        double initalSlidePos = robot.outtake.outtakeMotor.getCurrentPosition();
        ElapsedTime mytimer = new ElapsedTime();
        ElapsedTime intakeSequence2 = new ElapsedTime();
        ElapsedTime intakeSequence = new ElapsedTime();
        boolean activateSequence2 = false;
        while(opModeIsActive()){

            telemetry.addData("timer value", intakeSequence2.time());
            telemetry.addData("timer value", intakeSequence2.time());
            if (gamepad2.left_bumper) {
                intakeSequence2.reset();
                activateSequence2 = true;
                if (setUp) {
                    intakeLeft = 0.05;
                    intakeRight = 0.95;
                    claw = 0.65;
                    horiSlide = 0.22;
                } else {
                    claw = 0.77;
                }
            }
            if (intakeSequence2.time() > 0.2 && intakeSequence2.time() < 0.4 && setUp && activateSequence2)  {
                horiSlide = 0.22;
                setUp = false;
                activateSequence2 = false;
            } else if(intakeSequence2.time() > 0.1 && intakeSequence2.time() < 0.2 && activateSequence2 && !setUp) {
                intakeLeft = 0.95;
                intakeRight = 0.05;
                horiSlide = 0.05;
            } else if (intakeSequence2.time() > 1.2 && intakeSequence2.time() < 1.3 && activateSequence2 && !setUp) {
                claw = 0.55;
            } else if (intakeSequence2.time() > 1.4 && intakeSequence2.time() < 1.6 && activateSequence2) {
                intakeLeft = 0.05;
                intakeRight = 0.95;
                claw = 0.65;
            } else if (intakeSequence2.time() > 1.6 && intakeSequence2.time() < 1.8 && activateSequence2) {
                horiSlide = 0.22;
                activateSequence2 = false;
            }

//            if (gamepad2.left_bumper) {
//                intakeSequence.reset();
//                intakeLeft = 0.05;
//                intakeRight = 0.95;
//                claw = 0.55;
//            }
//            if (intakeSequence.time() > 0.2 && intakeSequence.time() < 0.4 && activateSequence2) {
//                horiSlide = 0.4;
//            } else if (intakeSequence.time() > 0.8 && intakeSequence.time() < 1 && activateSequence2) {
//                claw = 0.77;
//            } else if (intakeSequence.time() > 1.2 && intakeSequence.time() < 1.4 && activateSequence2) {
//                intakeLeft = 0.95;
//                intakeRight = 0.05;
//                horiSlide = 0.05;
//            } else if (intakeSequence.time() > 2.3 && intakeSequence.time() < 2.5 && activateSequence2) {
//                claw = 0.55;
//            }

            if(gamepad1.right_bumper){
                factor = 0.3;
            }
            else{
                factor = 1;
            }
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double leftFrontPower = (y + x + rx) / denominator;
            double leftBackPower = (y - x + rx) / denominator;
            double rightFrontPower = (y - x - rx) / denominator;
            double rightBackPower = (y + x - rx) / denominator;
            robot.dt.rightFront.setPower(rightFrontPower * factor);
            robot.dt.rightBack.setPower(rightBackPower * factor);
            robot.dt.leftBack.setPower(leftBackPower * factor);
            robot.dt.leftFront.setPower(leftFrontPower * factor);

            //outtake
            telemetry.addData("Servo Position", robot.outtake.outtakeServo.getPosition());

            if (gamepad1.y) {
                robot.outtake.outtakeServo.setPosition(1);
            } else {
                robot.outtake.outtakeServo.setPosition(0.1);
            }
            //slides
            if(gamepad1.dpad_up)
                slideValue = 0.5;
            else if(gamepad1.dpad_down && robot.outtake.outtakeMotor.getCurrentPosition() > initalSlidePos) {
                slideValue = -0.5;
            } else {
                slideValue = 0;
            }
            telemetry.addData("initial count",initalSlidePos);
            telemetry.addData("outtake encoder count",robot.outtake.outtakeMotor.getCurrentPosition());
            robot.outtake.outtakeMotor.setPower(slideValue);

            //intake pivot
            if (gamepad2.a){
                intakeArmLock = true;
            }

            if (intakeArmLock && !gamepad2.a) {
                intakeArmLock = false;
                if (intakeTog) {
                    intakeLeft = 0.05;
                    intakeRight = 0.95;
                    intakeTog = false;
                } else {
                    intakeLeft = 0.95;
                    intakeRight = 0.05;
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
                    claw = 0.55;

                    clawTog = false;
                } else {
                    claw = 0.77;
                    clawTog = true;
                }

            }

            robot.intake.clawServo.setPosition(claw);

            //&& horiSlide <= 1 && horiSlide >= 0


            if(gamepad2.left_stick_y < -0.1 && horiSlide < 1 ) {
                horiSlide = horiSlide - gamepad2.left_stick_y * 0.002;
            }

            if(gamepad2.left_stick_y > 0.1 && horiSlide > 0 ) {
                setUp = true;
                horiSlide = horiSlide - gamepad2.left_stick_y * 0.002;
            }

            if(gamepad2.x) {
                horiSlide = 0;
            }
            if (horiSlide < 0.05 && timerReached) {
                mytimer.reset();
                timerReached = false;
            }
            if (mytimer.time() > 0.5 && mytimer.time() < 0.7) {
                horiSlide = 0.05;
                timerReached = true;
            }

            telemetry.addData("horislide value", robot.intake.horiServo.getPosition());

            robot.intake.horiServo.setPosition(horiSlide);

            telemetry.update();
        }
    }
}