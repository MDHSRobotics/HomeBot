
package frc.robot.subsystems.constants;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
/**
 * The Constants class provides a convenient place for teams to hold
 * subsystem-wide numerical or boolean constants. This class should not be used
 * for any other purpose. All constants should be declared globally (i.e. public
 * static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class PathConstants {

    public static final int kLeftMotor1Port = 0;
    public static final int kLeftMotor2Port = 1;
    public static final int kRightMotor1Port = 2;
    public static final int kRightMotor2Port = 3;

    public static final int[] kLeftEncoderPorts = new int[] { 0, 1 };
    public static final int[] kRightEncoderPorts = new int[] { 2, 3 };
    public static final boolean kLeftEncoderReversed = false;
    public static final boolean kRightEncoderReversed = true;

    public static final double kTrackwidthMeters = 0.69;
    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final int kEncoderCPR = 2048;
    public static final double kWheelDiameterMeters = 0.1524;
    public static final double kEncoderDistancePerPulse =
            // Assumes the encoders are directly mounted on the wheel shafts
            (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

    public static final boolean kGyroReversed = true;

    // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
    // These characterization values MUST be determined either experimentally or
    // theoretically
    // for *your* robot's drive.
    // The Robot Characterization Toolsuite provides a convenient tool for obtaining
    // these
    // values for your robot.
    public static final double ksVolts = 0.669;
    public static final double kvVoltSecondsPerMeter = 0.000325;
    public static final double kaVoltSecondsSquaredPerMeter = 0.0000814;

    // Example value only - as above, this must be tuned for your drive!
    public static final double kPDriveVel = 9.35 * ( 1 / Math.pow(10, 51));

    public static final class OIConstants {
        public static final int kDriverControllerPort = 1;
    }
}
