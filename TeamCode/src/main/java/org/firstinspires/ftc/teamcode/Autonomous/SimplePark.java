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
public class SimplePark extends LinearOpMode {

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

        robot.dt.move(1, 3, Drivetrain.moveType.DRIVE, 1.5, this);

        if (parkLocation == Vision.pLocation.LOCATION1) {
            robot.dt.move(0.45, -tileLength - 2.5, Drivetrain.moveType.STRAFE, 1.5, this);
        } else if (parkLocation == Vision.pLocation.LOCATION3) {
            robot.dt.move(0.45, tileLength + 2.5, Drivetrain.moveType.STRAFE, 1.5, this);
        }

        robot.dt.move(0.45, tileLength + 6, Drivetrain.moveType.DRIVE, 1.5, this);
    }
}