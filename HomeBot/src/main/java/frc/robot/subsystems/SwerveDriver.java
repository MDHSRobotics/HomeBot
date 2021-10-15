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
import frc.robot.subsystems.constants.EncoderConstants;

public class SwerveDriver extends SubsystemBase {
    public static final double kMaxSpeed = 1.0; // 1 meter per second
    public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

    // Control if you want to move from the point of view of the robot or from the point of view of the field
    public static final boolean fieldRelative = false;
    
    // Control whether you want the thumbstick axes to be flipped in the opposite direction.
    public static final boolean isYFlipped = false;
    public static final boolean isXFlipped = false;
    public static final boolean isOmegaFlipped = false;

    // The kinematics object is created from the locations of the swerve modules
    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(Devices.frontLeftSwerveModule.getLocation(), Devices.frontRightSwerveModule.getLocation(), Devices.rearLeftSwerveModule.getLocation(), Devices.rearRightSwerveModule.getLocation());

    // The odometry object takes the kinematics object and the current angle of the robot (the odometry should be updated)
    private final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics, getAngle());

    public SwerveDriver() {
        BotSensors.gyro.reset();
    }

    // Returns the current gyro angle
    public Rotation2d getAngle() {
        return Rotation2d.fromDegrees(-BotSensors.gyro.getAngle()); // Flipping the angle because WPILib gyros are CW positive.
    }

    // Drive using a vertical, horizontal, and rotational velocity
    public void drive(double xVel, double yVel, double omega) {
        double m_xVel = xVel;
        double m_yVel = yVel;
        double m_omega = omega;
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(0, 1, 0, Rotation2d.fromDegrees(0));
        SwerveModuleState[] swerveModuleStates = m_kinematics.toSwerveModuleStates(speeds);

        // Flip axis directions 
        if (isXFlipped) {
            m_xVel = -xVel;
        }
        if (isOmegaFlipped) {
            m_omega = -omega;
        }

        // Choose between robot or field relative driving
        if (fieldRelative) {
            swerveModuleStates = m_kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(m_xVel, m_yVel, m_omega, getAngle()));
        } 
        else {
            swerveModuleStates = m_kinematics.toSwerveModuleStates(new ChassisSpeeds(m_xVel, m_yVel, m_omega));
        }

        SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, kMaxSpeed);

        // Set the state of each swerve module in order to achieve the specified drive velocities
        
        // Devices.frontLeftSwerveModule.setDesiredState(swerveModuleStates[0]);
        // Devices.frontRightSwerveModule.setDesiredState(swerveModuleStates[1]);
        // Devices.rearLeftSwerveModule.setDesiredState(swerveModuleStates[2]);
        // Devices.rearRightSwerveModule.setDesiredState(swerveModuleStates[3]);

        // Logger.info(swerveModuleStates[0].toString());
    }

    // The odometry object updates its position given a current gyro angle and current module states
    public void updateOdometry() {
        Pose2d pose = m_odometry.update(getAngle(), Devices.frontLeftSwerveModule.getCurrentState(), Devices.frontRightSwerveModule.getCurrentState(), Devices.rearLeftSwerveModule.getCurrentState(), Devices.rearRightSwerveModule.getCurrentState());
        //Logger.info(pose.toString());
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

    public void resetPositionOfMotors() {
        Devices.frontLeftSwerveModule.rotateMotorsToSetpoint();
        Devices.frontRightSwerveModule.rotateMotorsToSetpoint();
        Devices.rearLeftSwerveModule.rotateMotorsToSetpoint();
        Devices.rearRightSwerveModule.rotateMotorsToSetpoint();
    }
}