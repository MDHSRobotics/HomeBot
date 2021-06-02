package frc.robot.devices;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.SwerveDriver;
import frc.robot.subsystems.utils.EncoderUtils;

public class DevSwerveModule {

    // Typical battery voltage
    public static final double BATTERY_VOLTAGE = 12.0;

    // Accessible module objects
    private final DevTalonFX m_driveTalon;
    private final DevTalonFX m_steerTalon;
    private final Translation2d location;
    
    // PID initialization
    private static final double kModuleMaxAngularVelocity = 0.5;//SwerveDriver.kMaxAngularSpeed;  // radians per second squared
    private static final double kModuleMaxAngularAcceleration = 0.5;//2 * Math.PI;                // radians per second squared
    private final PIDController m_drivePIDController = new PIDController(0.2, 0, 0);          // initialize PID profile
    private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(0.2, 0, 0, new TrapezoidProfile.Constraints(kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));
    
    // Configure feed forward gains (experimentally found)
    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(0, 0.45);
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(0, 0.45);
    
    /**
     * Constructs a SwerveModule device.
     * 
     * @param driveTalon talonFX object of the drive motor
     * @param steerTalon talonFX object of the steer motor
     * @param xpos horizontal position of the module (in meters) from the center of the robot
     * @param ypos vertical position of the module (in meters) from the center of the robot
     */
    public DevSwerveModule(DevTalonFX driveTalon, DevTalonFX steerTalon, double xpos, double ypos, boolean SENSOR_PHASE, boolean MOTOR_INVERT) {
        m_driveTalon = driveTalon;
        m_steerTalon = steerTalon;
        location = new Translation2d(xpos, ypos);

        m_driveTalon.setSensorPhase(SENSOR_PHASE);
        m_driveTalon.setInverted(MOTOR_INVERT);

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
        int turnPosition = m_steerTalon.getSelectedSensorPosition();
        double turnPositionRadians = EncoderUtils.translateTicksToRadians(turnPosition);

        double driveOutput = m_drivePIDController.calculate(EncoderUtils.translateTicksToDistance(driveVelocity, 4 * Math.PI), state.speedMetersPerSecond); // Calculate the drive output from the current velocity and a velocity setpoint
        //conflict between feet per 100 ms and meters per second
        double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond); // Calculate the drive feed forward from a velocity setpoint

        double turnOutput = m_turningPIDController.calculate(turnPositionRadians, state.angle.getRadians()); // Calculate the turning motor output from the current position and a position setpoint
        double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity); // Calculate the turn feed forward from a velocity setpoint

        // Creates a percentage value for set by adding the voltages required for the respective motors and dividing by the current maximum battery voltage
        double driveTalonVoltage = (driveOutput + driveFeedforward) / BATTERY_VOLTAGE;
        double steerTalonVoltage = (turnOutput + turnFeedforward) / BATTERY_VOLTAGE;
        Logger.info("Output: " + driveOutput);
        Logger.info("Output: " + turnOutput);
        Logger.info("Feed: " + driveFeedforward);
        Logger.info("Feed: " + turnFeedforward);
        Logger.info("Voltage: " + driveTalonVoltage);
        Logger.info("Voltage: " + steerTalonVoltage);
        m_driveTalon.set(driveTalonVoltage);
        // m_steerTalon.set(steerTalonVoltage);
    }

    /**
     * Gets the current state of the module.
     *
     * @return The current state of the module
     */
    public SwerveModuleState getCurrentState() {
        int velocity = m_driveTalon.getSelectedSensorVelocity();
        int position = m_steerTalon.getSelectedSensorPosition();
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

    public void stopModule(){
        m_driveTalon.stopMotor();
        m_steerTalon.stopMotor();
    }

    public void testModule() {
        m_driveTalon.set(0.3);
        m_steerTalon.set(0.3);
    }
    
}