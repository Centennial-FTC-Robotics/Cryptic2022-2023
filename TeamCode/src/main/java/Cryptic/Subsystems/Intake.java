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

public class Intake  implements Subsystem{
    public Servo rightPivotServo;
    public Servo leftPivotServo;
    public Servo clawServo;
    public static Servo horiServo;
    public void initialize(LinearOpMode opMode){
        rightPivotServo = opMode.hardwareMap.get(Servo.class, "right");
        leftPivotServo = opMode.hardwareMap.get(Servo.class, "left");
        clawServo = opMode.hardwareMap.get(Servo.class, "claw");
        horiServo = opMode.hardwareMap.get(Servo.class,"hori");

    }
    public enum intakeDo {
        EXTEND,
        RETRACT
    }
    public void extendIntake(int increment) {
        //if (timey.time() < 4.0) {
        horiServo.setPosition(0.55);
        leftPivotServo.setPosition(0.40 - (increment * 0.07));
        rightPivotServo.setPosition(0.60 + (increment * 0.07));
        //clawServo.setPosition(0.55);
        //}
    }
    public void retractIntake() {
        clawServo.setPosition(0.77);
        //horiServo.setPosition(0.05);
        leftPivotServo.setPosition(0.95);
        rightPivotServo.setPosition(0.05);
        //clawServo.setPosition(0.55);
    }
}
