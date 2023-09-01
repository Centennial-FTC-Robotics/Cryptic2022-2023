package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Cryptic.OurRobot;
import Cryptic.Subsystems.Drivetrain;
import Cryptic.Subsystems.Vision;
import Cryptic.Tests.CycleTest;

@Autonomous
public class BlueAllianceBlueTerminalMediumJunction extends LinearOpMode {

    double tileLength = 24.5;
    public boolean pressed = false;

    @Override
    public void runOpMode() throws InterruptedException {

        OurRobot robot = new OurRobot();
        robot.initialize(this);

        robot.intake.clawServo.setPosition(0.55);
        Vision.pLocation parkLocation = Vision.Pipeline.getAnalysis(this);
        while(!opModeIsActive()){
            parkLocation = Vision.Pipeline.getAnalysis(this);
            telemetry.addData("signal", parkLocation);
            telemetry.update();
        }
        waitForStart();
/*
        Vision.pLocation parkLocation = Vision.Pipeline.getAnalysis(this);
        robot.dt.move(1, 3, Drivetrain.moveType.DRIVE, 1.5, this);

        if (parkLocation == Vision.pLocation.LOCATION1) {
            robot.dt.move(0.45, -tileLength - 2.5, Drivetrain.moveType.STRAFE, 1.5, this);
        } else if (parkLocation == Vision.pLocation.LOCATION3) {
            robot.dt.move(0.45, tileLength + 2.5, Drivetrain.moveType.STRAFE, 1.5, this);
        }

        robot.dt.move(0.45, tileLength + 6, Drivetrain.moveType.DRIVE, 1.5, this);
        */
        robot.dt.move(0.7, 3, Drivetrain.moveType.DRIVE, 0.4, this);
        robot.dt.move(0.7, -(tileLength * 1) - 24.5, Drivetrain.moveType.STRAFE, 2.4, this);
        robot.dt.turn(0.7, -17, 0.5, this);
        robot.dt.move(0.7, -2.2, Drivetrain.moveType.DRIVE, 0.3, this);
        for (int i = 0; i < 5; i++) {
            cycle(i);
        }
        ElapsedTime timey = new ElapsedTime();

        robot.intake.leftPivotServo.setPosition(0.33);
        robot.intake.rightPivotServo.setPosition(0.35);
        while (timey.time() < 3) {
            if (0.2 < timey.time() && timey.time() < 1.6) {
                robot.outtake.outtakeMotor.setTargetPosition(2500);
                robot.outtake.outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.outtake.outtakeMotor.setPower(1);
            }
            if (0.8 < timey.time() && timey.time() < 1.8) {
                robot.outtake.outtakeServo.setPosition(1);
            }
            if (1.8 < timey.time() && timey.time() < 2.8) {
                robot.outtake.outtakeServo.setPosition(0.1);
            }
            if (2.0 < timey.time() && timey.time() < 3.0) {
                robot.outtake.outtakeMotor.setTargetPosition(10);
                robot.outtake.outtakeMotor.setPower(-0.6);
            }
        }

        robot.dt.move(0.7, 2.3, Drivetrain.moveType.DRIVE, 0.3, this);
        robot.dt.turn(0.7, 105, 0.8, this);
        robot.dt.move(0.7, 11.2, Drivetrain.moveType.DRIVE, 1, this);
        if (parkLocation == Vision.pLocation.LOCATION1) {

            robot.dt.move(0.45, tileLength + 2.5, Drivetrain.moveType.STRAFE, 1.5, this);
        } else if (parkLocation == Vision.pLocation.LOCATION3) {
            robot.dt.move(0.45, -tileLength - 2.5, Drivetrain.moveType.STRAFE, 1.5, this);
        }
        //robot.intake.leftPivotServo.setPosition(0.05);
        //robot.intake.rightPivotServo.setPosition(0.95);
        //robot.outtake.score(this);
        //robot.dt.turn(1, -15);
        //robot.dt.move(1, 2, Drivetrain.moveType.STRAFE);
        //if (parkLocation == Vision.pLocation.LOCATION1) robot.dt.move(Drivetrain.moveType.DRIVE, -tileLength);
        //else if (parkLocation == Vision.pLocation.LOCATION2);
        //else if (parkLocation == Vision.pLocation.LOCATION3) robot.dt.move(Drivetrain.moveType.DRIVE, tileLength);
    }
    public void cycle (int increment){
        ElapsedTime timey = new ElapsedTime();
        while (timey.time() < 4.0) {
            OurRobot.outtake.score(timey, 420);
            if (timey.time() > 0 && timey.time() < 2.0) {
                OurRobot.intake.clawServo.setPosition(0.55);
                OurRobot.intake.extendIntake(increment);
            } if (timey.time() < 0.8) {
                OurRobot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                OurRobot.intake.horiMotor.setPower(0.9);
            } else if (timey.time() < 2.0) {
                OurRobot.intake.horiMotor.setPower(0);
            }
            if (2.0 < timey.time() && timey.time() < 2.3) {
                OurRobot.intake.clawServo.setPosition(0.77);
            } if (2.3 < timey.time() && timey.time() < 3.4) {
                OurRobot.intake.retractIntake();
            }
            if (2.6 < timey.time() && !OurRobot.intake.touch.isPressed()) {
                OurRobot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                OurRobot.intake.horiMotor.setPower(-0.9);
            }else if (2.6 < timey.time()){
                OurRobot.intake.horiMotor.setPower(0);
            }
            if (3.5 < timey.time() && timey.time() < 4.0) {
                pressed = false;
                OurRobot.intake.clawServo.setPosition(0.5);
            }
        }
    }
}