package frc.robot.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.*;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.SwerveDriver;
import frc.robot.subsystems.utils.EncoderUtils;
import frc.robot.subsystems.utils.PIDValues;
import frc.robot.subsystems.utils.TalonUtils;

public class DevSwerveModule {

    // Typical battery voltage
    public static final double BATTERY_VOLTAGE = 12.0;

    // Accessible module objects
    private final DevTalonFX m_driveTalon;
    private final DevTalonFX m_steerTalon;
    
    // PID initialization
    private static final double kModuleMaxAngularVelocity = 0.5;//SwerveDriver.kMaxAngularSpeed;  // radians per second squared
    private static final double kModuleMaxAngularAcceleration = 0.5;//2 * Math.PI;                // radians per second squared
    
    /**
     * Constructs a SwerveModule device.
     * 
     * @param driveTalon talonFX object of the drive motor
     * @param steerTalon talonFX object of the steer motor
     */
    public DevSwerveModule(DevTalonFX driveTalon, DevTalonFX steerTalon, boolean SENSOR_PHASE, boolean MOTOR_INVERT) {
        m_driveTalon = driveTalon;
        m_steerTalon = steerTalon;

        PIDValues pid = new PIDValues(0.0, 0.05, 0.0, 0.0);
        TalonUtils.configureTalonWithEncoder(m_driveTalon, SENSOR_PHASE, MOTOR_INVERT, pid);
        TalonUtils.configureTalonWithEncoder(m_steerTalon, SENSOR_PHASE, MOTOR_INVERT, pid);
    }

    public void stopModule(){
        m_driveTalon.stopMotor();
        m_steerTalon.stopMotor();
    }

    public void testModule() {
        m_driveTalon.set(0.3);
        m_steerTalon.set(ControlMode.Velocity, 2048);
    }
    
}