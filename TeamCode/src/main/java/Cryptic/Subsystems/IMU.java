package Cryptic.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import Cryptic.OurRobot;
import Cryptic.Superclasses.Subsystem;

public class IMU implements Subsystem {

    public BNO055IMU imu;
    private double angle;
    private double lastIMUReading;
    private static double zero = 0;
    static final double P_TURN_COEFF = 0.05;
    static final double HEADING_THRESHOLD = 0.5;
    static final double powerCap = 0.07;

    public void initialize(LinearOpMode opMode) {
        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
        //while IMU is not calibrated
        while (opMode.opModeIsActive() && !imu.isGyroCalibrated());
        lastIMUReading = imu.getAngularOrientation().firstAngle;
    }

    public static double normalize(double angle) {
        // returns the angle geometrically equivalent to the input angle but between -180 and 180
        angle = angle % 360;
        if (angle > 180) {
            angle -= 360;
        }
        if (angle < -180) {
            angle += 360;
        }
        return angle;
    }
    public static double normalizeRadians(double angle) {
        // returns the angle geometrically equivalent to the input angle but between -180 and 180
        angle = angle % (2*Math.PI);
        if (angle > Math.PI) {
            angle -= (2*Math.PI);
        }
        if (angle < -Math.PI) {
            angle += (2*Math.PI);
        }
        return angle;
    }

    public void gyroTurn(double speed, double angle, LinearOpMode opMode){
        double error, steer;
        double leftSpeed, rightSpeed;
        error = getError (angle, opMode);
        ElapsedTime time = new ElapsedTime();
        while (time.milliseconds() < 2000) {
            steer = Range.clip(P_TURN_COEFF * error, -speed, speed);
            if (steer > -powerCap && steer < 0) {
                steer = -powerCap;
            } else if (steer < powerCap) {
                steer = powerCap;
            }
            rightSpeed = steer;
            leftSpeed = -rightSpeed;

            OurRobot.dt.leftFront.setPower(leftSpeed);
            OurRobot.dt.rightFront.setPower(rightSpeed);
            OurRobot.dt.rightBack.setPower(rightSpeed);
            OurRobot.dt.leftBack.setPower(leftSpeed);

            OurRobot.intake.horiMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            OurRobot.intake.horiMotor.setPower(-0.2);

            error = getError(angle, opMode);
        }
    }
    public double getError (double targetAngle, LinearOpMode opMode){
        double angleError;
        Orientation orientation = imu.getAngularOrientation(
                AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        angleError = targetAngle - orientation.thirdAngle; //changed from third angle to second angle
        opMode.telemetry.addData("firstangle", orientation.firstAngle);
        opMode.telemetry.addData("secondangle", orientation.secondAngle);
        opMode.telemetry.addData("thirdangle", orientation.thirdAngle);
        opMode.telemetry.update();

        normalize(angleError);
        return angleError;
    }

    public void setZero(double zeroAngle){
        zero = zeroAngle;
    }

    public double zero(){
        return zero;
    }

    public double angle() {
        return angle + zero;
    }

    public void update() {
        Orientation orientation = imu.getAngularOrientation(
                AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        double newAngle = orientation.thirdAngle;      //changed from first angle to second angle
        angle += normalize(newAngle - lastIMUReading);
        lastIMUReading = newAngle;
    }
}
