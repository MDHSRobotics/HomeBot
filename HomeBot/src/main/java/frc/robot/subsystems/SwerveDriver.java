package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.BotSensors;

public class SwerveDriver extends SubsystemBase {
    public static final double kMaxSpeed = 3.0; // 3 meters per second
    public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

    //control if you want to move from the point of view of the robot or from the point of view of the field
    public static final boolean fieldRelative = true;
    
    //control whether you want the thumbstick axes to be flipped in the opposite direction.
    public static final boolean isYLeftFlipped = false;
    public static final boolean isXLeftFlipped = false;
    public static final boolean isXRightFlipped = false;

    //the kinematics object is created from the locations of the swerve modules
    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(Devices.frontLeftSwerveModule.getLocation(), Devices.frontRightSwerveModule.getLocation(), Devices.rearLeftSwerveModule.getLocation(), Devices.rearRightSwerveModule.getLocation());

    //the odometry object takes the kinematics object and the current angle of the robot (the odometry should be updated)
    private final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics, getAngle());

    public SwerveDriver() {
        BotSensors.gyro.reset();
    }

    //returns the negative angle that the gyro returns
    public Rotation2d getAngle() {
        // Negating the angle because WPILib gyros are CW positive.
        return Rotation2d.fromDegrees(-BotSensors.gyro.getAngle());
    }

    //drive using a vertical speed, a horizontal speed, and a rotational speed
    public void drive(double xSpeed, double ySpeed, double rot) {
        double m_xSpeed = xSpeed;
        double m_rotSpeed = rot;
        if (isXLeftFlipped) {
            m_xSpeed = -xSpeed;
        }
        if (isXRightFlipped) {
            m_rotSpeed = -rot;
        }
        SwerveModuleState[] swerveModuleStates;
        if (fieldRelative) {
            swerveModuleStates = m_kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(m_xSpeed, ySpeed, m_rotSpeed, getAngle()));
        } else {
            swerveModuleStates = m_kinematics.toSwerveModuleStates(new ChassisSpeeds(m_xSpeed, ySpeed, m_rotSpeed));
        }
        SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, kMaxSpeed);
        Devices.frontLeftSwerveModule.setDesiredState(swerveModuleStates[0]);
        Devices.frontRightSwerveModule.setDesiredState(swerveModuleStates[1]);
        Devices.rearLeftSwerveModule.setDesiredState(swerveModuleStates[2]);
        Devices.rearRightSwerveModule.setDesiredState(swerveModuleStates[3]);
    }

    //the odometry object updates its current angle and swerveModuleStates
    public void updateOdometry() {
        m_odometry.update(getAngle(), Devices.frontLeftSwerveModule.getState(), Devices.frontRightSwerveModule.getState(), Devices.rearLeftSwerveModule.getState(), Devices.rearRightSwerveModule.getState());
    }
    
    // Stop all the drive motors directly
    public void stop() {
        Devices.talonFxSwerveDriveWheelFrontLeft.stopMotor();
        Devices.talonFxSwerveDriveWheelFrontRight.stopMotor();
        Devices.talonFxSwerveDriveWheelRearLeft.stopMotor();
        Devices.talonFxSwerveDriveWheelRearRight.stopMotor();
        Devices.talonFxSwerveTurnWheelFrontLeft.stopMotor();
        Devices.talonFxSwerveTurnWheelFrontRight.stopMotor();
        Devices.talonFxSwerveTurnWheelRearLeft.stopMotor();
        Devices.talonFxSwerveTurnWheelRearRight.stopMotor();
    }
}