package Cryptic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Cryptic.Subsystems.Drivetrain;
import Cryptic.Subsystems.IMU;
import Cryptic.Subsystems.Vision;
import Cryptic.Superclasses.Subsystem;

public class OurRobot {

    public static Drivetrain dt = new Drivetrain();
    public static Vision vision = new Vision();
    public static IMU imu = new IMU();

    private static final Subsystem[] Subsystems = {
            dt,
            vision,
            imu
    };

    public static void initialize(LinearOpMode opMode) {
        for (int i = 0; i < Subsystems.length; i++) {
            Subsystems[i].initialize(opMode);
        }
    }
}
