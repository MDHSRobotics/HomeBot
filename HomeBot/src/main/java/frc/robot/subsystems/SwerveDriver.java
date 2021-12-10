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
import frc.robot.devices.DevSwerveModule;
import static frc.robot.subsystems.Devices.*;

public class SwerveDriver extends SubsystemBase {

    // Constants for PID setpoint algorithm
    public static final double kMaxSpeed = 1.0; // meters per second
    public static final double kMaxAngularSpeed = Math.PI; // radians per second

    // Switch between robot and field relative control
    public static final boolean fieldRelative = false;
    
    // Thumbstick axis inversions
    public static boolean isYFlipped = false;
    private boolean isXFlipped = false;
    private boolean isOmegaFlipped = false;

    // Subsystem calculation objects
    private final SwerveDriveKinematics m_kinematics;
    private final SwerveDriveOdometry m_odometry;

    // Subsystem SwerveModule objects
    private DevSwerveModule m_swerveModuleFL;
    private DevSwerveModule m_swerveModuleFR;
    private DevSwerveModule m_swerveModuleRL;
    private DevSwerveModule m_swerveModuleRR;

    public SwerveDriver() {
        // SwerveModule objects are initialized with their respective talons and positions
        m_swerveModuleFL = new DevSwerveModule(talonFxSwerveDriveFL, talonFxSwerveTurnFL, 0.24, 0.24);
        m_swerveModuleFR = new DevSwerveModule(talonFxSwerveDriveFR, talonFxSwerveTurnFR, 0.24, -0.24);
        m_swerveModuleRL = new DevSwerveModule(talonFxSwerveDriveRL, talonFxSwerveTurnRL, -0.24, 0.24);
        m_swerveModuleRR = new DevSwerveModule(talonFxSwerveDriveRR, talonFxSwerveTurnRR, -0.24, -0.24);

        // A kinematics object is created from the locations of the swerve modules
        m_kinematics = new SwerveDriveKinematics(m_swerveModuleFL.getLocation(), 
                                                 m_swerveModuleFR.getLocation(), 
                                                 m_swerveModuleRL.getLocation(), 
                                                 m_swerveModuleRR.getLocation());

        // An odometry object takes the kinematics object and the current angle of the robot (the odometry should be updated)
        m_odometry = new SwerveDriveOdometry(m_kinematics, getAngle());
    }

    // Returns the current gyro angle
    public Rotation2d getAngle() {
        return Rotation2d.fromDegrees(-BotSensors.gyro.getAngle()); // Flipping the angle because WPILib gyros are CW positive.
    }

    /**
     * Operates each module to achieve robot motion given three joystick inputs
     * 
     * @param xVel
     * @param yVel
     * @param omega
     */
    public void drive(double xVel, double yVel, double omega) {
        double m_xVel = xVel;
        double m_yVel = yVel;
        double m_omega = omega;
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(m_xVel, m_yVel, m_omega, Rotation2d.fromDegrees(0));
        SwerveModuleState[] swerveModuleStates = m_kinematics.toSwerveModuleStates(speeds);

        // Flip axis directions 
        if (isXFlipped) m_xVel = -xVel;
        if (isOmegaFlipped) m_omega = -omega;

        // Choose between robot or field relative driving
        if (fieldRelative) {
            swerveModuleStates = m_kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(m_xVel, m_yVel, m_omega, getAngle()));
        } 
        else {
            swerveModuleStates = m_kinematics.toSwerveModuleStates(new ChassisSpeeds(m_xVel, m_yVel, m_omega));
        }

        SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, kMaxSpeed);

        // Set the state of each swerve module in order to achieve the specified drive velocities
        m_swerveModuleFL.setDesiredState(swerveModuleStates[0]);
        m_swerveModuleFR.setDesiredState(swerveModuleStates[1]);
        m_swerveModuleRL.setDesiredState(swerveModuleStates[2]);
        m_swerveModuleRR.setDesiredState(swerveModuleStates[3]);
    }

    // The odometry object updates its position given a current gyro angle and current module states
    public void updateOdometry() {
        Pose2d pose = m_odometry.update(getAngle(), m_swerveModuleFL.getCurrentState(), m_swerveModuleFR.getCurrentState(), m_swerveModuleRL.getCurrentState(), m_swerveModuleRR.getCurrentState());
    }

    public void resetGyro(){
        BotSensors.gyro.reset();
    }
    
    public void stop() {
        m_swerveModuleFL.stopModule();
        m_swerveModuleFR.stopModule();
        m_swerveModuleRL.stopModule();
        m_swerveModuleRR.stopModule();
    }

    public void testMotors() {
        m_swerveModuleFL.testModule();
        m_swerveModuleFR.testModule();
        m_swerveModuleRL.testModule();
        m_swerveModuleRR.testModule();
    }

    public void resetModulePosition() {
        m_swerveModuleFL.zeroOutModulePositions();
        m_swerveModuleFR.zeroOutModulePositions();
        m_swerveModuleRL.zeroOutModulePositions();
        m_swerveModuleRR.zeroOutModulePositions();
    }

    public void getTurnPositions(){
        Logger.info("Encoder returned ticks: " + talonFxSwerveTurnFL.getSelectedSensorPosition(0));
        Logger.info("Encoder returned ticks: " + talonFxSwerveTurnFR.getSelectedSensorPosition(0));
        Logger.info("Encoder returned ticks: " + talonFxSwerveTurnRL.getSelectedSensorPosition(0));
        Logger.info("Encoder returned ticks: " + talonFxSwerveTurnRR.getSelectedSensorPosition(0));
    }
}