package frc.robot.devices;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.devices.DevTalonFX;
import frc.robot.subsystems.SwerveDriver;
import frc.robot.subsystems.utils.EncoderUtils;

public class DevSwerveModule {

    private static final double kModuleMaxAngularVelocity = SwerveDriver.kMaxAngularSpeed;
    private static final double kModuleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared

    //talonFX motor controllers for drive and turn for each swerve module
    private final DevTalonFX m_driveMotor;
    private final DevTalonFX m_turningMotor;

    //Typical battery voltage
    public final double batteryVoltage = 12;

    //translation objects that represent the respective distance a swerve module is to the center of the robot (in meters)
    private final Translation2d location;

    private final PIDController m_drivePIDController = new PIDController(1, 0, 0);
    
    private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));
    
    // Gains are for example purposes only - must be determined for your own robot!
    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);
    
    /**
     * Constructs a SwerveModule.
     * 
     * @param driveMotor talonFX for the drive motor.
     * @param turnMotor talonFX for the turning motor.
     * @param xpos horizontal position (in meters) from the center of the robot
     * @param ypos vertical position (in meters) from the center of the robot
     */
    public DevSwerveModule(DevTalonFX driveMotor, DevTalonFX turnMotor, double xpos, double ypos) {
        m_driveMotor = driveMotor;
        m_turningMotor = turnMotor;
        location = new Translation2d(xpos, ypos);

        // Limit the PID Controller's input range between -pi and pi and set the input
        // to be continuous.
        m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    /**
     * Sets the desired state for the module.
     * 
     * @param state Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState state) {
        // Calculate the drive output from the drive PID controller.
        final double driveOutput = m_drivePIDController.calculate(m_driveMotor.getSelectedSensorVelocity(), state.speedMetersPerSecond);

        final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);

        // Calculate the turning motor output from the turning PID controller.
        final double turnOutput = m_turningPIDController.calculate(EncoderUtils.translateTicksToRadians(m_turningMotor.getSelectedSensorPosition()), state.angle.getRadians());

        final double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);

        //creates a percentage value for set by adding the voltages required for the respective motors and dividing by the current maximum battery voltage
        m_driveMotor.set((driveOutput + driveFeedforward) / batteryVoltage);
        m_turningMotor.set((turnOutput + turnFeedforward) / batteryVoltage);
    }

    /**
     * Returns the current state of the module.
     *
     * @return The current state of the module.
     */

    public SwerveModuleState getState() {
        return new SwerveModuleState(m_driveMotor.getSelectedSensorVelocity(), new Rotation2d(EncoderUtils.translateTicksToRadians(m_turningMotor.getSelectedSensorPosition())));
    }

    public Translation2d getLocation() {
        return location;
    }
    
}