package frc.robot.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.subsystems.utils.EncoderUtils;
import frc.robot.subsystems.utils.PIDValues;
import frc.robot.subsystems.utils.TalonUtils;

public class DevSwerveModule {

    // Typical battery voltage
    public static final double BATTERY_VOLTAGE = 12.0;
    public static final double driveGearRatio = 8.16;
    public static final double turnGearRatio = 12.8;
    public static final double ENCODER_TPR = 2048;
    public static final double WHEEl_DIAMETER = 6;
    public static final double DRIVE_GEAR_RATIO = 8.16;
    public static final double STEER_GEAR_RATIO = 13.6;

    // Accessible module objects
    private final DevTalonFX m_driveTalon;
    private final DevTalonFX m_steerTalon;
    private final Translation2d location;
    
    // PID initialization
    private final PIDController m_drivePIDController = new PIDController(0.87, 0, 0);          // initialize PID profile
    public final PIDController m_turningPIDController = new PIDController(0.87, 0, 0);
    
    // Configure feed forward gains (experimentally found)
    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(0, 0.45);
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(0, 0.45);

    /**
     * Constructs a SwerveModule device.
     * 
     * @param driveTalon the talonFX object of this module's drive motor
     * @param steerTalon the talonFX object of this module's steer motor
     * @param xpos horizontal position of this module (in meters) from the center of the robot
     * @param ypos vertical position of this module (in meters) from the center of the robot
     */
    public DevSwerveModule(DevTalonFX driveTalon, DevTalonFX steerTalon, double xpos, double ypos) {
        m_driveTalon = driveTalon;
        m_steerTalon = steerTalon;
        location = new Translation2d(xpos, ypos);
        PIDValues pidDrive = new PIDValues(0.0, 0.5, 0.0, 0.0);
        PIDValues pidSteer = new PIDValues(0.0, 0.5, 0.0, 0.0);

        TalonUtils.configureTalonWithEncoder(m_driveTalon, true, true, pidDrive);
        TalonUtils.configureTalonWithEncoder(m_steerTalon, true, true, pidSteer);

        // Limit the PID Controller's input range between -pi and pi and set the input to be continuous
        m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);

    }

    /**
     * Sets the desired state for the module.
     * 
     * @param state Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState state) {
        // int driveVelocity = (int) state.speedMetersPerSecond;
        // int turnPosition = (int) ((state.angle.getRadians() * 4096) / (2 * Math.PI));
        // double turnPositionRadians = EncoderUtils.translateTicksToRadians(turnPosition);

        // double driveOutput = m_drivePIDController.calculate(EncoderUtils.translateTicksToDistance(driveVelocity * 3.048, 4 * Math.PI), state.speedMetersPerSecond); // Calculate the drive output from the current velocity and a velocity setpoint
        // //convert from feet per 100 ms to meters per second
        // double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond); // Calculate the drive feed forward from a velocity setpoint

        // double turnOutput = m_turningPIDController.calculate(turnPositionRadians, state.angle.getRadians()); // Calculate the turning motor output from the current position and a position setpoint
        // double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint()); // Calculate the turn feed forward from a velocity setpoint

        // // Creates a percentage value for set by adding the voltages required for the respective motors and dividing by the current maximum battery voltage
        // double driveTalonVoltage = (driveOutput + driveFeedforward) / BATTERY_VOLTAGE;
        // double steerTalonVoltage = (turnOutput + turnFeedforward) / BATTERY_VOLTAGE;
        // m_driveTalon.set(driveTalonVoltage);
        // m_steerTalon.set(steerTalonVoltage);


        double wrappedSensorPosition = (m_steerTalon.getSelectedSensorPosition() % ENCODER_TPR); // wraps the current encoder position
        double currentSteerAngle = (wrappedSensorPosition / ENCODER_TPR) * 2 * Math.PI; // converts the current encoder position into an angle in radians
        SwerveModuleState optimizedState = SwerveModuleState.optimize(state, new Rotation2d(currentSteerAngle));
        double stateVelocity = optimizedState.speedMetersPerSecond;
        Rotation2d stateAngle = optimizedState.angle;

        int driveVelocity = EncoderUtils.translateMPSToTicksPerDecisecond(stateVelocity, WHEEl_DIAMETER, DRIVE_GEAR_RATIO);
        int steerPosition = EncoderUtils.translateRadiansToTicks(stateAngle.getRadians());

        m_driveTalon.set(ControlMode.Velocity, driveVelocity);
        m_steerTalon.set(ControlMode.Position, steerPosition);
    }

    /**
     * Gets the current state of the module.
     *
     * @return The current state of the module
     */
    public SwerveModuleState getCurrentState() {
        int velocity = (int) m_driveTalon.getSelectedSensorVelocity();
        int position = (int) m_steerTalon.getSelectedSensorPosition();
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

    /**
     * Should spin both drive and steer talons a full 360 degrees
     */
    public void testModule() {
        m_driveTalon.set(ControlMode.Position, 2048 * driveGearRatio);
        m_steerTalon.set(ControlMode.Position, 2048 * turnGearRatio);
    }

    public void zeroOutModulePositions(){
        TalonFXSensorCollection sensorColDrive = m_driveTalon.getSensorCollection();
        sensorColDrive.setIntegratedSensorPosition(0, 20);
        TalonFXSensorCollection sensorColSteer = m_steerTalon.getSensorCollection();
        sensorColSteer.setIntegratedSensorPosition(0, 20);
        
    }
}