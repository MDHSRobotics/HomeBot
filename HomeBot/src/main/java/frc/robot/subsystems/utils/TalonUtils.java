
package frc.robot.subsystems.utils;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;

import frc.robot.devices.DevTalonSRX;

import static frc.robot.subsystems.constants.EncoderConstants.*;
import static frc.robot.subsystems.constants.TalonConstants.*;

// Utility methods for talon configuration (with or without encoders.)
public class TalonUtils {

    // Config the given talon
    public static void configureBaseTalon(DevTalonSRX talon, boolean sensorPhase, boolean motorInvert) {
        if (!talon.isConnected) return;
        talon.configFactoryDefault();

        talon.setSensorPhase(sensorPhase);
        talon.setInverted(motorInvert);

        talon.configPeakCurrentDuration(PEAK_AMPERAGE_DURATION, TIMEOUT_MS);
        talon.configPeakCurrentLimit(PEAK_AMPERAGE, TIMEOUT_MS);
        talon.configContinuousCurrentLimit(CONTINUOUS_AMPERAGE_LIMIT, TIMEOUT_MS);
        talon.enableCurrentLimit(true);

        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);

        talon.configNominalOutputForward(0);
        talon.configNominalOutputReverse(0);
        talon.configPeakOutputForward(1);
        talon.configPeakOutputReverse(-1);
    }

    // Configure the given talon
    public static void configureTalonWithEncoder(DevTalonSRX talon, boolean sensorPhase, boolean motorInvert, PIDValues pid) {
        if (!talon.isConnected) return;

        configureBaseTalon(talon, sensorPhase, motorInvert);

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

    // Config the given talon master
    public static void configureTalonMasterFollower(DevTalonSRX talonM,
                                                    DevTalonSRX talonF,
                                                    boolean sensorPhaseM,
                                                    boolean motorInvertM,
                                                    boolean sensorPhaseF,
                                                    boolean motorInvertF,
                                                    PIDValues pid) {
        if (!talonM.isConnected || !talonF.isConnected) return;

        configureBaseTalon(talonF, sensorPhaseF, motorInvertF);
        configureBaseTalon(talonM, sensorPhaseM, motorInvertM);

        talonF.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP_PRIMARY, TIMEOUT_MS);
        talonF.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, TIMEOUT_MS);
        talonF.follow(talonM, FollowerType.AuxOutput1);

        int talonFid = talonF.getDeviceID();
        talonM.configRemoteFeedbackFilter(talonFid, RemoteSensorSource.TalonSRX_SelectedSensor, 1, TIMEOUT_MS);

        // Configure both top and bottom wheel encoders to use for summation feedback (feedback to reach target velocity)
        talonM.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor1, TIMEOUT_MS); // Quadrature Encoder of BottomWheel Talon
        talonM.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, TIMEOUT_MS); // Quadrature Encoder of TopWheel Talon

        // Configure both top and bottom wheel encoders to use for difference feedback (feedback to minimize velocity header)
        talonM.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor1, TIMEOUT_MS); // Quadrature Encoder of BottomWheel Talon
        talonM.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.QuadEncoder, TIMEOUT_MS); // Quadrature Encoder of TopWheelTalon

        talonM.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, PID_LOOP_PRIMARY, TIMEOUT_MS); // Assign summation feedback to the primary PID loop
        talonM.configSelectedFeedbackCoefficient(0.5, PID_LOOP_PRIMARY, TIMEOUT_MS);

        talonM.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference, PID_LOOP_AUXILIARY, TIMEOUT_MS); // Assign difference feedback to the auxiliary PID loop
        talonM.configSelectedFeedbackCoefficient(1, PID_LOOP_AUXILIARY, TIMEOUT_MS);

        // Satus frames to ensure no stale values
        talonM.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, TIMEOUT_MS);
        talonM.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, TIMEOUT_MS);
        talonM.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, TIMEOUT_MS);

        // PID configuration for target velocity
        talonM.config_kF(PID_SLOT_0, pid.kF, TIMEOUT_MS);
        talonM.config_kP(PID_SLOT_0, pid.kP, TIMEOUT_MS);
        talonM.config_kI(PID_SLOT_0, pid.kI, TIMEOUT_MS);
        talonM.config_kD(PID_SLOT_0, pid.kD, TIMEOUT_MS);
        talonM.config_IntegralZone(PID_SLOT_0, 300, TIMEOUT_MS);
        talonM.configClosedLoopPeakOutput(PID_SLOT_0, 1, TIMEOUT_MS);
        talonM.configAllowableClosedloopError(PID_SLOT_0, 0, TIMEOUT_MS);

        // PID configuration for velocity header
        talonM.config_kF(PID_SLOT_1, pid.kF, TIMEOUT_MS);
        talonM.config_kP(PID_SLOT_1, pid.kP, TIMEOUT_MS);
        talonM.config_kI(PID_SLOT_1, pid.kI, TIMEOUT_MS);
        talonM.config_kD(PID_SLOT_1, pid.kD, TIMEOUT_MS);
        talonM.config_IntegralZone(PID_SLOT_1, 300, TIMEOUT_MS);
        talonM.configClosedLoopPeakOutput(PID_SLOT_1, 1, TIMEOUT_MS);
        talonM.configAllowableClosedloopError(PID_SLOT_1, 0, TIMEOUT_MS);

        talonM.configClosedLoopPeriod(PID_SLOT_0, CLOSED_LOOP_TIME_MS, TIMEOUT_MS);
        talonM.configClosedLoopPeriod(PID_SLOT_1, CLOSED_LOOP_TIME_MS, TIMEOUT_MS);

        talonM.configAuxPIDPolarity(false, TIMEOUT_MS);

        talonM.selectProfileSlot(PID_SLOT_0, PID_LOOP_PRIMARY);
        talonM.selectProfileSlot(PID_SLOT_1, PID_LOOP_AUXILIARY);

        zeroOutEncoder(talonF);
        zeroOutEncoder(talonM);
    }

    // Initialize current encoder position as zero
    public static void zeroOutEncoder(DevTalonSRX talon) {
        if (!talon.isConnected) return;
        SensorCollection sensorCol = talon.getSensorCollection();
        sensorCol.setQuadraturePosition(0, TIMEOUT_MS);
    }

}
