package Cryptic.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import Cryptic.OurRobot;
import Cryptic.Subsystems.Intake;

@Autonomous
public class CycleTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        OurRobot robot = new OurRobot();
        robot.initialize(this);

        waitForStart();
        for (int i = 0; i < 7; i++) {
            cycle();
        }
    }
    public void cycle() {
        ElapsedTime timey = new ElapsedTime();

        while (timey.time() < 5) {
            if (timey.time() > 0.2) {
        //        OurRobot.outtake.score(timey, this);
            }
            if (timey.time() < 3.0) {
                //OurRobot.intake.extendIntake();
            } else if (timey.time() < 3.5) {
                OurRobot.intake.clawServo.setPosition(0.77);
            } else if (3.5 < timey.time() && timey.time() < 4.5) {
                OurRobot.intake.retractIntake();
            } else if (timey.time() < 5) {
                OurRobot.intake.clawServo.setPosition(0.55);
            }
        }
    }
}
