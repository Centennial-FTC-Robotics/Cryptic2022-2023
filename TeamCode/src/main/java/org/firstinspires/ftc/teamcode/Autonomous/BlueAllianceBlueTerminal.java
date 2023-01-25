package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import Cryptic.OurRobot;
import Cryptic.Subsystems.Drivetrain;
import Cryptic.Subsystems.Vision;
import Cryptic.Tests.CycleTest;

@Autonomous
public class BlueAllianceBlueTerminal extends LinearOpMode {

    double tileLength = 24.5;

    @Override
    public void runOpMode() throws InterruptedException {

        OurRobot robot = new OurRobot();
        robot.initialize(this);

        waitForStart();

        Vision.pLocation parkLocation = Vision.Pipeline.getAnalysis(this);
        robot.dt.move(1, 3, Drivetrain.moveType.DRIVE, 1.5,this);

        if (parkLocation == Vision.pLocation.LOCATION1) {
            robot.dt.move(0.45, -tileLength - 2.5 , Drivetrain.moveType.STRAFE, 1.5,this);
        } else if (parkLocation == Vision.pLocation.LOCATION3) {
            robot.dt.move(0.45, tileLength + 2.5  , Drivetrain.moveType.STRAFE, 1.5,this);
        }

        robot.dt.move(0.45, tileLength + 6, Drivetrain.moveType.DRIVE, 1.5,this);
//        robot.dt.move(1, (tileLength * 2) + 9, Drivetrain.moveType.DRIVE, 1.5,this);
//        robot.dt.turn(1, 105, 0.8, this);
//        for (int i = 0; i < 5; i++) {
//            cycle(i);
//        }
//            //robot.intake.leftPivotServo.setPosition(0.05);
//            //robot.intake.rightPivotServo.setPosition(0.95);
//            //robot.outtake.score(this);
//            //robot.dt.turn(1, -15);
//            //robot.dt.move(1, 2, Drivetrain.moveType.STRAFE);
//            //if (parkLocation == Vision.pLocation.LOCATION1) robot.dt.move(Drivetrain.moveType.DRIVE, -tileLength);
//            //else if (parkLocation == Vision.pLocation.LOCATION2);
//            //else if (parkLocation == Vision.pLocation.LOCATION3) robot.dt.move(Drivetrain.moveType.DRIVE, tileLength);
//    }
//
//    public void cycle(int increment) {
//        ElapsedTime timey = new ElapsedTime();
//
//        while (timey.time() < 6.3) {
//            if (timey.time() > 0.2) {
//                OurRobot.outtake.score(timey, this);
//            }
//            if (timey.time() < 3.0) {
//                OurRobot.intake.extendIntake(increment);
//            } else if (timey.time() < 4.0) {
//                OurRobot.intake.clawServo.setPosition(0.77);
//            } else if (4.0 > timey.time() && timey.time() < 4.5) {
//                OurRobot.intake.retractIntake();
//            } if (4.3 < timey.time() && timey.time() < 5.7) {
//                OurRobot.intake.horiServo.setPosition(0.05);
//            } else if (timey.time() < 6.3) {
//                OurRobot.intake.clawServo.setPosition(0.55);
//            }
//        }
    }
}