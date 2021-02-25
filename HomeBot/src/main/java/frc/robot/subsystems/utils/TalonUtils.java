
package frc.robot.subsystems.utils;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import frc.robot.devices.*;

import static frc.robot.subsystems.constants.EncoderConstants.*;
import static frc.robot.subsystems.constants.TalonConstants.*;

// Utility methods for talon configuration (with or without encoders.)
public class TalonUtils {

    // Config the given TalonSRX
    public static void configureBaseTalon(DevTalonSRX talon, boolean motorInvert) {
        if (!talon.isConnected) return;
        talon.configFactoryDefault();

        talon.setInverted(motorInvert);

        SupplyCurrentLimitConfiguration currentLimitConfig = new SupplyCurrentLimitConfiguration(true, PEAK_CONTINUOUS_AMPERAGE, PEAK_AMPERAGE, PEAK_AMPERAGE_DURATION);
        talon.configSupplyCurrentLimit(currentLimitConfig, TIMEOUT_MS);

        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);

        talon.configNominalOutputForward(0);
        talon.configNominalOutputReverse(0);
        talon.configPeakOutputForward(1);
        talon.configPeakOutputReverse(-1);
    }

    // Config the given TalonFX
    public static void configureBaseTalon(DevTalonFX talon, boolean motorInvert) {
        if (!talon.isConnected) return;
        talon.configFactoryDefault();

        talon.setInverted(motorInvert);

        SupplyCurrentLimitConfiguration currentLimitConfig = new SupplyCurrentLimitConfiguration(true, PEAK_CONTINUOUS_AMPERAGE, PEAK_AMPERAGE, PEAK_AMPERAGE_DURATION);
        talon.configSupplyCurrentLimit(currentLimitConfig, TIMEOUT_MS);

        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);

        talon.configNominalOutputForward(0);
        talon.configNominalOutputReverse(0);
        talon.configPeakOutputForward(1);
        talon.configPeakOutputReverse(-1);
    }

    // Configure the given TalonSRX
    public static void configureTalonWithEncoder(DevTalonSRX talon, boolean sensorPhase, boolean motorInvert, PIDValues pid) {
        if (!talon.isConnected) return;

        configureBaseTalon(talon, motorInvert);

        talon.setSensorPhase(sensorPhase);

        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP_PRIMARY, TIMEOUT_MS);
        talon.configAllowableClosedloopError(PID_SLOT_0, 0, TIMEOUT_MS);

        talon.config_kF(PID_SLOT_0, pid.kF, TIMEOUT_MS);
        talon.config_kP(PID_SLOT_0, pid.kP, TIMEOUT_MS);
        talon.config_kI(PID_SLOT_0, pid.kI, TIMEOUT_MS);
        talon.config_kD(PID_SLOT_0, pid.kD, TIMEOUT_MS);

        //talon.configNeutralDeadband(NEUTRAL_DEADBAND, TIMEOUT_MS);

        talon.configMotionAcceleration(3000, TIMEOUT_MS);
        talon.configMotionCruiseVelocity(8000, TIMEOUT_MS);

        zeroOutEncoder(talon);
    }

    // Configure the given TalonFX
    public static void configureTalonWithEncoder(DevTalonFX talon, boolean sensorPhase, boolean motorInvert, PIDValues pid) {
        if (!talon.isConnected) return;

        configureBaseTalon(talon, motorInvert);

        talon.setSensorPhase(sensorPhase);

        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP_PRIMARY, TIMEOUT_MS);
        talon.configAllowableClosedloopError(PID_SLOT_0, 0, TIMEOUT_MS);

        talon.config_kF(PID_SLOT_0, pid.kF, TIMEOUT_MS);
        talon.config_kP(PID_SLOT_0, pid.kP, TIMEOUT_MS);
        talon.config_kI(PID_SLOT_0, pid.kI, TIMEOUT_MS);
        talon.config_kD(PID_SLOT_0, pid.kD, TIMEOUT_MS);

        //talon.configNeutralDeadband(NEUTRAL_DEADBAND, TIMEOUT_MS);

        talon.configMotionAcceleration(3000, TIMEOUT_MS);
        talon.configMotionCruiseVelocity(8000, TIMEOUT_MS);

        zeroOutEncoder(talon);
    }

    // Config the given TalonSRX master and follower
    public static void configureBaseTalonMasterFollower(DevTalonSRX talonM, DevTalonSRX talonF, boolean motorInvertM, boolean motorInvertF) {
        if (!talonM.isConnected || !talonF.isConnected) return;

        configureBaseTalon(talonF, motorInvertF);
        configureBaseTalon(talonM, motorInvertM);
        talonF.follow(talonM);
    }

    // Config the given TalonFX master and follower
    public static void configureBaseTalonMasterFollower(DevTalonFX talonM, DevTalonFX talonF, boolean motorInvertM, boolean motorInvertF) {
        if (!talonM.isConnected || !talonF.isConnected) return;

        configureBaseTalon(talonF, motorInvertF);
        configureBaseTalon(talonM, motorInvertM);
        talonF.follow(talonM);
    }

    // Initialize current encoder position as zero
    public static void zeroOutEncoder(DevTalonSRX talon) {
        if (!talon.isConnected) return;
        SensorCollection sensorCol = talon.getSensorCollection();
        sensorCol.setQuadraturePosition(0, TIMEOUT_MS);
    }

    // Initialize current encoder position as zero
    public static void zeroOutEncoder(DevTalonFX talon) {
        if (!talon.isConnected) return;
        TalonFXSensorCollection sensorCol = talon.getSensorCollection();
        sensorCol.setIntegratedSensorPosition(0, TIMEOUT_MS);
    }

}
