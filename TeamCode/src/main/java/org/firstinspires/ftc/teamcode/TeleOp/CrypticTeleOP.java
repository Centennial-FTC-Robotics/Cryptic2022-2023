package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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
        //robot.intake.horiMotor.setTargetPosition(1);
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
        int horiSlide = 0;
        double factor;
        boolean timerReached = true;
        boolean setUp = true;
        boolean outtakeSequence = false;
        boolean outtakeSequenceLock = true;
        double initalSlidePos = robot.outtake.outtakeMotor.getCurrentPosition();
        ElapsedTime mytimer = new ElapsedTime();
        ElapsedTime intakeSequence2 = new ElapsedTime();
        ElapsedTime intakeSequence = new ElapsedTime();
        ElapsedTime outtakeSequenceTimer = new ElapsedTime();
        boolean activateSequence2 = false;
        while(opModeIsActive()){

            telemetry.addData("timer value", intakeSequence2.time());
            if (gamepad2.left_bumper) {
                intakeSequence2.reset();
                activateSequence2 = true;
                if (setUp) {
                    intakeLeft = 0.05;
                    intakeRight = 0.95;
                    claw = 0.65;
                    horiSlide = 40;
                } else {
                    horiSlide = 700;
                    robot.intake.horiMotor.setPower(0.7);

                }
            }
            //setup
            if (intakeSequence2.time() > 0.2 && intakeSequence2.time() < 0.6 && setUp && activateSequence2) {
                horiSlide = 400;
                robot.intake.horiMotor.setPower(0.5);
            } else if (intakeSequence2.time() > 0.6 && intakeSequence2.time() < 0.7 && setUp && activateSequence2) {
                robot.intake.horiMotor.setPower(0);
                setUp = false;
                activateSequence2 = false;
            //cycle
            } else if(intakeSequence2.time() > 0.1 && intakeSequence2.time() < 0.3 && activateSequence2 && !setUp) {
                robot.intake.horiMotor.setPower(0);
                claw = 0.77;
            } else if(intakeSequence2.time() > 0.3 && intakeSequence2.time() < 0.4 && activateSequence2 && !setUp) {
                intakeLeft = 0.95;
                intakeRight = 0.05;
                horiSlide = 1;
                //automatic retraction
            } else if (intakeSequence2.time() > 0.4 && robot.intake.touch.isPressed() && activateSequence2 && !setUp) {
                claw = 0.5;
            } else if (intakeSequence2.time() > 1.4 && intakeSequence2.time() < 1.6 && activateSequence2 && !setUp) {
                intakeLeft = 0.05;
                intakeRight = 0.95;
                claw = 0.65;

            } else if (intakeSequence2.time() > 1.6 && intakeSequence2.time() < 2.1 && activateSequence2 && !setUp) {
                horiSlide = 400;
                robot.intake.horiMotor.setPower(0.5);
                outtakeSequence = true;
            } else if (intakeSequence2.time() > 2.1 && intakeSequence2.time() < 2.2 && activateSequence2 && !setUp) {
                robot.intake.horiMotor.setPower(0);
                activateSequence2 = false;
            }

            //outtake Sequence
            if (outtakeSequence && outtakeSequenceLock) {
                outtakeSequenceTimer.reset();
                outtakeSequenceLock = false;
            }
            if (outtakeSequence && outtakeSequenceTimer.time() < 1.1) {
                slideValue = 0.8;
                robot.outtake.outtakeServo.setPosition(1);
            } else if (outtakeSequence && outtakeSequenceTimer.time() > 1.1 && robot.outtake.outtakeMotor.getCurrentPosition() > initalSlidePos) {
                slideValue = -0.8;
                robot.outtake.outtakeServo.setPosition(0.1);
            } else if (outtakeSequence) {
                slideValue = 0;
                outtakeSequence = false;
                outtakeSequenceLock = true;
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
                factor = 0.7;
            }
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;
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
            } else if (outtakeSequenceLock){
                robot.outtake.outtakeServo.setPosition(0.1);
            }

            //slides
            if(gamepad1.dpad_up)
                slideValue = 0.5;
            else if(gamepad1.dpad_down && robot.outtake.outtakeMotor.getCurrentPosition() > initalSlidePos) {
                slideValue = -0.5;
            } else if (outtakeSequenceLock){
                slideValue = 0;
            }
            telemetry.addData("initial count",initalSlidePos);
            telemetry.addData("outtake encoder count",robot.outtake.outtakeMotor.getCurrentPosition());
            robot.outtake.outtakeMotor.setPower(slideValue);

            //intake pivot
            if (gamepad2.a){
                intakeArmLock = true;
                setUp = true;
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


            if(gamepad2.left_stick_y < -0.1 && horiSlide > 0) {
                robot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.intake.horiMotor.setPower(-gamepad2.left_stick_y);
                horiSlide = robot.intake.horiMotor.getCurrentPosition();
            } else if(gamepad2.left_stick_y > 0.1 && horiSlide < 550) {
                robot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.intake.horiMotor.setPower(-gamepad2.left_stick_y);
                horiSlide = robot.intake.horiMotor.getCurrentPosition();
            } else {
                //robot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                if (horiSlide < 2) {
                    if (!robot.intake.touch.isPressed()) {
                        robot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        robot.intake.horiMotor.setPower(-0.7);
                    } else {
                        robot.intake.horiMotor.setPower(0);
                        horiSlide = 3;
                    }
                } else {
//                    robot.intake.horiMotor.setTargetPosition((int) horiSlide);
//                    robot.intake.horiMotor.setPower(0.5);
//                    robot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
            }

            if(gamepad2.x) {
                horiSlide = 1;
            }
            if (horiSlide < 0.05 && timerReached) {
                mytimer.reset();
                timerReached = false;
            }
            if (mytimer.time() > 0.5 && mytimer.time() < 0.7) {
                horiSlide = 1;
                timerReached = true;
            }

            telemetry.addData("horislide value", robot.intake.horiMotor.getCurrentPosition());
            telemetry.addData("limit switch", robot.intake.touch.isPressed());
            telemetry.update();
        }
    }
}