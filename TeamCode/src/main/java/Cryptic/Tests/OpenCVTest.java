package Cryptic.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Cryptic.OurRobot;
import Cryptic.Subsystems.Vision;

@Autonomous(name="OpenCVTest")
public class OpenCVTest extends LinearOpMode{
    OurRobot robot = new OurRobot();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(this);

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addLine(Vision.Pipeline.getAnalysis());
            telemetry.update();
        }
    }
}
