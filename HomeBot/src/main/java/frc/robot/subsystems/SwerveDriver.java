package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.BotSensors;
import frc.robot.consoles.Logger;

public class SwerveDriver extends SubsystemBase {

    
    // Control whether you want the thumbstick axes to be flipped in the opposite direction.
    public static final boolean isYFlipped = false;
    public static final boolean isXFlipped = false;
    public static final boolean isOmegaFlipped = false;

    // Drive using a vertical, horizontal, and rotational velocity
    public void drive(double xVel, double yVel, double omega) {
    }

    // Stop all the swerve modules
    public void stop() {
        Devices.frontLeftSwerveModule.stopModule();
        Devices.frontRightSwerveModule.stopModule();
        Devices.rearLeftSwerveModule.stopModule();
        Devices.rearRightSwerveModule.stopModule();
    }

    public void testMotors() {
        Devices.frontLeftSwerveModule.testModule();
        Devices.frontRightSwerveModule.testModule();
        Devices.rearLeftSwerveModule.testModule();
        Devices.rearRightSwerveModule.testModule();
    }
}