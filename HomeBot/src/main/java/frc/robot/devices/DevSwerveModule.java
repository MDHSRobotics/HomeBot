package frc.robot.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.consoles.Logger;
import frc.robot.sensors.Gyro;
import frc.robot.subsystems.SwerveDriver;
import frc.robot.subsystems.constants.EncoderConstants;
import frc.robot.subsystems.utils.EncoderUtils;
import frc.robot.subsystems.utils.PIDValues;
import frc.robot.subsystems.utils.TalonUtils;

public class DevSwerveModule {

    // Typical battery voltage
    public static final double BATTERY_VOLTAGE = 12.0;
    public static final double driveGearRatio = 8.16;
    public static final double turnGearRatio = 12.8;

    // Accessible module objects
    private final DevTalonFX m_driveTalon;
    private final DevTalonFX m_steerTalon;
    private final Translation2d location;
    
    // PID initialization
    private static final double kModuleMaxAngularVelocity = 0.5;//SwerveDriver.kMaxAngularSpeed;  // radians per second squared
    private static final double kModuleMaxAngularAcceleration = 0.5;//2 * Math.PI;                // radians per second squared
    private final PIDController m_drivePIDController = new PIDController(0.87, 0, 0);          // initialize PID profile
    public final PIDController m_turningPIDController = new PIDController(0.87, 0, 0);
    
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

        PIDValues pidDriveValues = new PIDValues(0.0, 0.5, 0.0, 0.0);
        TalonUtils.configureTalonWithEncoder(m_driveTalon, false, false, pidDriveValues);
        PIDValues pidTurnValues = new PIDValues(0.0, 0.5, 0.0, 0.0);
        TalonUtils.configureTalonWithEncoder(m_steerTalon, false, false, pidTurnValues);
    }

    /**
     * Sets the desired state for the module.
     * 
     * @param state Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState state) {
        int driveVelocity = (int) state.speedMetersPerSecond;
        int turnPosition = (int) ((state.angle.getRadians() * 4096) / (2 * Math.PI));
        double turnPositionRadians = EncoderUtils.translateTicksToRadians(turnPosition);

        double driveOutput = m_drivePIDController.calculate(EncoderUtils.translateTicksToDistance(driveVelocity * 3.048, 4 * Math.PI), state.speedMetersPerSecond); // Calculate the drive output from the current velocity and a velocity setpoint
        //convert from feet per 100 ms to meters per second
        double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond); // Calculate the drive feed forward from a velocity setpoint

        double turnOutput = m_turningPIDController.calculate(turnPositionRadians, state.angle.getRadians()); // Calculate the turning motor output from the current position and a position setpoint
        double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint()); // Calculate the turn feed forward from a velocity setpoint

        // Creates a percentage value for set by adding the voltages required for the respective motors and dividing by the current maximum battery voltage
        double driveTalonVoltage = (driveOutput + driveFeedforward) / BATTERY_VOLTAGE;
        double steerTalonVoltage = (turnOutput + turnFeedforward) / BATTERY_VOLTAGE;
        m_driveTalon.set(driveTalonVoltage);
        m_steerTalon.set(steerTalonVoltage);
    }

    /**
     * Converts by taking the MPS and dividing it by the cirfcumference to find the ratio. After finding the ratio, you multiply by the ticks per rotation to get ticks per second. Convert from there.
     * @param MPS
     * @param wheelDiameter
     * @return
     */
    public static double translateMetersPerSecondToTPHMS(double MPS, double wheelDiameter) {
        double wheelCircumference = wheelDiameter * Math.PI;
        double TPHMS = ((MPS / wheelCircumference) * EncoderConstants.ENCODER_TPR) / 10;
        return TPHMS;
    }

    /**
     * Gets the current state of the module.
     *
     * @return The current state of the module
     */
    public SwerveModuleState getCurrentState() {
        int velocity = (int) 0;//m_driveTalon.getSelectedSensorVelocity();
        int position = (int) 0;//m_steerTalon.getSelectedSensorPosition();
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
        m_driveTalon.set(ControlMode.Position, 2056);
        m_steerTalon.set(ControlMode.Position, 2056);
    }
    
    public void rotateMotorsToSetpoint() {
        // m_turningPIDController.setSetpoint(Gyro.YAW_TOLERANCE);
        Logger.info("Setting Rotation of Swerve Drive Wheels");
        m_driveTalon.set(ControlMode.Velocity, 0.2 * driveGearRatio);
        m_steerTalon.set(ControlMode.Position, 2056 * turnGearRatio);
    }
    
}