package Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Cryptic.OurRobot;
import Cryptic.Superclasses.Subsystem;

public class Intake  implements Subsystem{
    public Servo rightPivotServo;
    public Servo leftPivotServo;
    public Servo clawServo;
    public static TouchSensor touch;
    public static DcMotor horiMotor;
    public void initialize(LinearOpMode opMode){
        rightPivotServo = opMode.hardwareMap.get(Servo.class, "right");
        touch = opMode.hardwareMap.get(TouchSensor.class, "touch");
        leftPivotServo = opMode.hardwareMap.get(Servo.class, "left");
        clawServo = opMode.hardwareMap.get(Servo.class, "claw");
        horiMotor = opMode.hardwareMap.get(DcMotor.class,"hori");
        horiMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horiMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horiMotor.setDirection(DcMotor.Direction.FORWARD);
        horiMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public enum intakeDo {
        EXTEND,
        RETRACT
    }
    public void extendIntake(int increment) {
        //if (timey.time() < 4.0) {
        leftPivotServo.setPosition(0.2 - (increment * 0.04));
        rightPivotServo.setPosition(0.47 + (increment * 0.04));
        //clawServo.setPosition(0.55);
        //}
    }
    public void retractIntake() {
        clawServo.setPosition(0.77);
        //horiServo.setPosition(0.05);
        leftPivotServo.setPosition(0.63);
        rightPivotServo.setPosition(0.05);
        //clawServo.setPosition(0.55);
    }
}
