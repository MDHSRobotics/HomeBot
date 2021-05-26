package frc.robot.devices;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.subsystems.SwerveDriver;
import frc.robot.subsystems.utils.EncoderUtils;

public class DevSwerveModule {

    // Typical battery voltage
    public static final double BATTERY_VOLTAGE = 12.0;

    // Module objects
    private final DevTalonFX m_driveTalon;
    private final DevTalonFX m_turnTalon;
    private final Translation2d location;
    
    // PID initialization
    private static final double kModuleMaxAngularVelocity = SwerveDriver.kMaxAngularSpeed;  // radians per second squared
    private static final double kModuleMaxAngularAcceleration = 2 * Math.PI;                // radians per second squared
    private final PIDController m_drivePIDController = new PIDController(1, 0, 0);          // initialize PID profile
    private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));
    
    // Gains are for example purposes only - must be determined for your own robot!
    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);
    
    /**
     * Constructs a SwerveModule device.
     * 
     * @param driveTalon talonFX object of the drive motor
     * @param turnTalon talonFX object of the turn motor
     * @param xpos horizontal position of the module (in meters) from the center of the robot
     * @param ypos vertical position of the module (in meters) from the center of the robot
     */
    public DevSwerveModule(DevTalonFX driveTalon, DevTalonFX turnTalon, double xpos, double ypos, boolean SENSOR_PHASE, boolean MOTOR_INVERT) {
        m_driveTalon = driveTalon;
        m_turnTalon = turnTalon;

        m_driveTalon.setSensorPhase(SENSOR_PHASE);
        m_driveTalon.setInverted(MOTOR_INVERT);

        location = new Translation2d(xpos, ypos);

        // Limit the PID Controller's input range between -pi and pi and set the input to be continuous
        m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    /**
     * Sets the desired state for the module.
     * 
     * @param state Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState state) {
        int driveVelocity = m_driveTalon.getSelectedSensorVelocity();
        int turnPosition = m_turnTalon.getSelectedSensorPosition();
        double turnPositionRadians = EncoderUtils.translateTicksToRadians(turnPosition);

        double driveOutput = m_drivePIDController.calculate(driveVelocity, state.speedMetersPerSecond); // Calculate the drive output from the current velocity and a velocity setpoint
        double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond); // Calculate the drive feed forward from a velocity setpoint

        double turnOutput = m_turningPIDController.calculate(turnPositionRadians, state.angle.getRadians()); // Calculate the turning motor output from the current position and a position setpoint
        double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity); // Calculate the turn feed forward from a velocity setpoint

        // Creates a percentage value for set by adding the voltages required for the respective motors and dividing by the current maximum battery voltage
        m_driveTalon.set((driveOutput + driveFeedforward) / BATTERY_VOLTAGE);
        m_turnTalon.set((turnOutput + turnFeedforward) / BATTERY_VOLTAGE);
    }

    /**
     * Returns the current state of the module.
     *
     * @return The current state of the module
     */
    public SwerveModuleState getState() {
        int velocity = m_driveTalon.getSelectedSensorVelocity();
        int position = m_turnTalon.getSelectedSensorPosition();
        double positionRadians = EncoderUtils.translateTicksToRadians(position);
        return new SwerveModuleState(velocity, new Rotation2d(positionRadians));
    }

    /**
     * Returns the location of the swerve module
     * 
     * @return The location of the swerve module
     */
    public Translation2d getLocation() {
        return location;
    }
    
}