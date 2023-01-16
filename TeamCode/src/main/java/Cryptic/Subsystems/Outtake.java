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
        ElapsedTime timeScore = new ElapsedTime();
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setTargetPosition(2300);
        outtakeMotor.setPower(0.5);
        while (timeScore.time() < 0.5){}
        outtakeServo.setPosition(1);
        opMode.sleep(500);
        outtakeServo.setPosition(0.1);
        opMode.sleep(200);
        outtakeMotor.setTargetPosition(0);
        outtakeMotor.setPower(-0.5);
        opMode.sleep(1500);
    }
}