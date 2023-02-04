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

import Cryptic.Superclasses.Subsystem;

public class Drivetrain implements Subsystem {
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;

    public void initialize(LinearOpMode opMode) {
        leftFront = opMode.hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = opMode.hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = opMode.hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = opMode.hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //resetEncoders();
    }

    public enum moveType {
        DRIVE,
        STRAFE,
        TURN
    }

    public void TargetPos(double EncoderCounts, moveType MoveType) {
        switch (MoveType) {
            case DRIVE:
                leftFront.setTargetPosition((int) -EncoderCounts);
                rightFront.setTargetPosition((int) -EncoderCounts);
                leftBack.setTargetPosition((int) -EncoderCounts);
                rightBack.setTargetPosition((int) -EncoderCounts);
                break;
            case STRAFE:
                leftFront.setTargetPosition((int) -EncoderCounts);
                rightFront.setTargetPosition((int) EncoderCounts);
                leftBack.setTargetPosition((int) EncoderCounts);
                rightBack.setTargetPosition((int) -EncoderCounts);
                break;
            case TURN:
                leftFront.setTargetPosition((int) -EncoderCounts);
                rightFront.setTargetPosition((int) EncoderCounts);
                leftBack.setTargetPosition((int) -EncoderCounts);
                rightBack.setTargetPosition((int) EncoderCounts);
                break;
        }
        resetEncoders();
    }

    public void resetEncoders() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public double INtoEC(double inches) {
        double COUNTS_PER_INCH = 45.2;
        double EncoderCounts = inches * COUNTS_PER_INCH;
        return EncoderCounts;
    }

    public void move(double power, double inches, moveType MoveType, double time, LinearOpMode opMode) {
        double EncoderCounts = INtoEC(inches);
        TargetPos(EncoderCounts, MoveType);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ElapsedTime timey = new ElapsedTime();
        double smoothness = power / 2;
        while (timey.time() < time) {
            /*
            leftFront.setPower(power);
            rightFront.setPower(power);
            leftBack.setPower(power);
            rightBack.setPower(power);
            */

            leftFront.setPower(power - smoothness);
            rightFront.setPower(power - smoothness);
            leftBack.setPower(power - smoothness);
            rightBack.setPower(power - smoothness);
            if (timey.time() > 0 && timey.time() < 1.0 && smoothness >= 0) {
                smoothness -= 0.003;
            }
            Intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            if (!Intake.touch.isPressed()) {
                Intake.horiMotor.setPower(-0.3);
            } else {
                Intake.horiMotor.setPower(0);
            }
        }
        Intake.horiMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    //overloaded method
    public void move(double power, double inches, moveType MoveType, double time, LinearOpMode opMode, int sexism) {
        double EncoderCounts = INtoEC(inches);
        TargetPos(EncoderCounts, MoveType);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ElapsedTime timey = new ElapsedTime();
        double smoothness = power / 2;
        while (timey.time() < time) {
            /*
            leftFront.setPower(power);
            rightFront.setPower(power);
            leftBack.setPower(power);
            rightBack.setPower(power);
            */

            leftFront.setPower(power - smoothness);
            rightFront.setPower(power - smoothness);
            leftBack.setPower(power - smoothness);
            rightBack.setPower(power - smoothness);
            if (timey.time() > 0 && timey.time() < 1.0 && smoothness >= 0) {
                smoothness -= 0.003;
            }
            Intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            if (timey.time() > 0 && timey.time() < 1.5) {
                Intake.horiMotor.setPower(0.5);
            }else if (timey.time() > 2 && !Intake.touch.isPressed()) {
                Intake.horiMotor.setPower(-0.5);
            } else {
                Intake.horiMotor.setPower(0);
            }
        }
        Intake.horiMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    public void turn(double power, double degrees, double time, LinearOpMode opMode) {
        ElapsedTime timey = new ElapsedTime();
        TargetPos(degrees * 8.4, moveType.TURN);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //double smoothness = power / 1.5;
        while (timey.time() < time) {
            leftFront.setPower(power);
            rightFront.setPower(power);
            leftBack.setPower(power);
            rightBack.setPower(power);
            /*
            leftFront.setPower(power - smoothness);
            rightFront.setPower(power - smoothness);
            leftBack.setPower(power - smoothness);
            rightBack.setPower(power - smoothness);
            if (timey.time() > 0 && timey.time() < 1.0 && smoothness >= 0) {
                smoothness -= 0.003;
            }

             */
            Intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            if (!Intake.touch.isPressed()) {
                Intake.horiMotor.setPower(-0.3);
            } else {
                Intake.horiMotor.setPower(0);
            }
        }
        Intake.horiMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }
}