package Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Cryptic.OurRobot;
import Cryptic.Superclasses.Subsystem;

public class Outtake implements Subsystem {
    public Servo outtakeServo ;
    public DcMotor outtakeMotor;
    public void initialize(LinearOpMode opMode){
        outtakeServo = opMode.hardwareMap.get(Servo.class, "outtakeServo");
        outtakeMotor = opMode.hardwareMap.get(DcMotor.class, "outtakeMotor");
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void score(LinearOpMode opMode) {
        ElapsedTime timey = new ElapsedTime();
        timey.reset();
        if (0 < timey.time() && timey.time() < 1.6) {
            outtakeMotor.setTargetPosition(2300);
            outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            outtakeMotor.setPower(0.6);
        } else if (1.6 < timey.time() && timey.time() < 2.2) {
            outtakeServo.setPosition(1);
        } else if (2.2 < timey.time() && timey.time() < 3.2) {
            outtakeServo.setPosition(0.0);
        } else if (3.2 < timey.time() && timey.time() < 4.2) {
            outtakeMotor.setTargetPosition(0);
            outtakeMotor.setPower(-0.6);
        }
    }
}