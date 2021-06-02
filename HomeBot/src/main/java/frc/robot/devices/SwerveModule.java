package frc.robot.devices;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.SwerveDriver;
import frc.robot.subsystems.utils.EncoderUtils;
import frc.robot.subsystems.utils.PIDValues;
import frc.robot.subsystems.utils.TalonUtils;
import static frc.robot.subsystems.constants.EncoderConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class SwerveModule {

    // Typical battery voltage
    public static final double BATTERY_VOLTAGE = 12.0;

    // Mechanical module characteristics 
    // Data can be found at: https://www.swervedrivespecialties.com/products/mk3-swerve-module
    private static final double WHEEl_DIAMETER = 6;
    private static final double DRIVE_GEAR_RATIO = 8.16; // might be 6.86
    private static final double STEER_GEAR_RATIO = 12.8;
    private static final double MAX_MODULE_VELOCITY = 13.6; // FPS

    // Accessible module objects
    private final DevTalonFX m_driveTalon;
    private final DevTalonFX m_steerTalon;
    private final Translation2d location;
    
    /**
     * Constructs a SwerveModule device with it's respective characteristics.
     * 
     * @param driveTalon talonFX object of the drive motor
     * @param steerTalon talonFX object of the steer motor
     * @param xpos horizontal position of the module (in meters) from the center of the robot
     * @param ypos vertical position of the module (in meters) from the center of the robot
     */
    public SwerveModule(DevTalonFX driveTalon, DevTalonFX steerTalon, double xpos, double ypos, boolean SENSOR_PHASE, boolean MOTOR_INVERT) {
        m_driveTalon = driveTalon;
        m_steerTalon = steerTalon;
        location = new Translation2d(xpos, ypos);

        PIDValues pidDrive = new PIDValues(0.0, 0.5, 0.0, 0.0);
        TalonUtils.configureTalonWithEncoder(m_driveTalon, SENSOR_PHASE, MOTOR_INVERT, pidDrive);
        PIDValues pidSteer = new PIDValues(0.0, 0.5, 0.0, 0.0);
        TalonUtils.configureTalonWithEncoder(m_steerTalon, false, false, pidSteer);
    }

    /**
     * Sets a SwerveModule device to a desired velocity and steer angle.
     * 
     * @param state A SwerveModuleState object that contains the desired module velocity and angle.
     */
    public void setDesiredState(SwerveModuleState state) {
        double wrappedSensorPosition = (m_steerTalon.getSelectedSensorPosition() % ENCODER_TPR); // wraps the current encoder position
        double currentSteerAngle = (wrappedSensorPosition / ENCODER_TPR) * 2 * Math.PI; // converts the current encoder position into an angle in radians
        SwerveModuleState optimizedState = state.optimize(state, new Rotation2d(currentSteerAngle));
        double stateVelocity = optimizedState.speedMetersPerSecond;
        Rotation2d stateAngle = optimizedState.angle;

        int driveVelocity = EncoderUtils.translateMPSToTicksPerDecisecond(stateVelocity, WHEEl_DIAMETER, DRIVE_GEAR_RATIO);
        double stateAngleDegrees = stateAngle.getRadians();
        int steerPosition = EncoderUtils.translateDegreesToTicks(stateAngleDegrees, STEER_GEAR_RATIO);
        double steerInput = (steerPosition - currentSteerAngle) * STEER_GEAR_RATIO;

        m_driveTalon.set(ControlMode.Velocity, driveVelocity);
        m_steerTalon.set(ControlMode.Position, steerInput);
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
    
}