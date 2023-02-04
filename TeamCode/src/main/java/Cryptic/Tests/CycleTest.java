package Cryptic.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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
        //OurRobot.outtake.score();
        for (int i = 0; i < 5; i++) {
            cycle(i);
        }
    }
            //robot.outtake.outtakeServo.setPosition(1);
            //robot.outtake.outtakeServo.setPosition(0);
            //robot.intake.clawServo.setPosition(0.55);
            //robot.intake.clawServo.setPosition(0.77);
/*
        for (int i = 0; i < 7; i++) {
            cycle();
        }

         */
    public void cycle (int increment){
        ElapsedTime timey = new ElapsedTime();

        while (timey.time() < 6.3) {
            OurRobot.outtake.score(timey);
            if (timey.time() > 0 && timey.time() < 3.0) {
                OurRobot.intake.extendIntake(increment);
            } if (timey.time() < 0.1) {
                OurRobot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                OurRobot.intake.horiMotor.setPower(0.7);
            } else if (timey.time() < 3.0) {
                OurRobot.intake.horiMotor.setPower(0);
            }
            if (timey.time() > 3.0 && timey.time() < 4.0) {
                OurRobot.intake.clawServo.setPosition(0.77);
            } if (4.0 < timey.time() && timey.time() < 4.5) {
                OurRobot.intake.retractIntake();
            }
            if (4.3 < timey.time() && timey.time() < 5.7) {
                if (!OurRobot.intake.touch.isPressed()) {
                    OurRobot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    OurRobot.intake.horiMotor.setPower(-0.7);
                }
            } if (5.7 < timey.time() && timey.time() < 6.3) {
                OurRobot.intake.clawServo.setPosition(0.55);
            }
        }
    }
}
