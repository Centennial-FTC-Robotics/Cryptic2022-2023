package Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import Cryptic.Superclasses.Subsystem;

public class Intake  implements Subsystem{
    public Servo rightPivotServo;
    public Servo leftPivotServo;
    public Servo clawServo;
    public void initialize(LinearOpMode opMode){
        rightPivotServo = opMode.hardwareMap.get(Servo.class, "rightPivotServo");
        leftPivotServo = opMode.hardwareMap.get(Servo.class, "leftPivotServo");
        clawServo = opMode.hardwareMap.get(Servo.class, "clawServo");

    }

}
