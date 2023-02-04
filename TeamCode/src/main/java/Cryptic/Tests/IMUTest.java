package Cryptic.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Cryptic.OurRobot;

@Autonomous
public class IMUTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        OurRobot robot = new OurRobot();
        robot.initialize(this);
        waitForStart();
        while(true) {
            robot.dt.turn(0.7, 88, 0.8, this);
        }
    }
}
