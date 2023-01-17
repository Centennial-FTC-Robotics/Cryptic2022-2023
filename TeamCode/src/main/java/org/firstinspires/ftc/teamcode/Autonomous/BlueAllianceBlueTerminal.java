package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Cryptic.OurRobot;
import Cryptic.Subsystems.Drivetrain;
import Cryptic.Subsystems.Vision;

@Autonomous
public class BlueAllianceBlueTerminal extends LinearOpMode {

    double tileLength = 24.5;

    @Override
    public void runOpMode() throws InterruptedException {

        OurRobot robot = new OurRobot();
        robot.initialize(this);

        waitForStart();

        //Vision.pLocation parkLocation = Vision.Pipeline.getAnalysis();

        robot.dt.move(Drivetrain.moveType.DRIVE, tileLength * 3);
        robot.dt.move(Drivetrain.moveType.TURN, 105);
        for (int i = 0; i < 3; i++) {
            //cycle();
        }
        robot.intake.leftPivotServo.setPosition(0.05);
        robot.intake.rightPivotServo.setPosition(0.95);
        //robot.outtake.score(this);
        robot.dt.move(Drivetrain.moveType.TURN, -15);
        robot.dt.move(Drivetrain.moveType.STRAFE, 2);
        //if (parkLocation == Vision.pLocation.LOCATION1) robot.dt.move(Drivetrain.moveType.DRIVE, -tileLength);
        //else if (parkLocation == Vision.pLocation.LOCATION2);
        //else if (parkLocation == Vision.pLocation.LOCATION3) robot.dt.move(Drivetrain.moveType.DRIVE, tileLength);
    }

    public void cycle() {
        //OurRobot.intake.extendIntake();
        //OurRobot.outtake.score(this);
        //OurRobot.intake.retractIntake();
    }
}