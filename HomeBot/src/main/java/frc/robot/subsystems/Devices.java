package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.devices.*;

// This class contains singleton (static) instances of id mapped subsystem components.
// If a device is not connected at initialization, it should be set to null.
// IMPORTANT: Only ONE subsystem should control any given device.
// Device instances are package-private (neither private nor public) so they can only be used by subsystems.
public class Devices {

    //////////////////////
    // Device Instances //
    //////////////////////

    // TalonSRX
    static DevTalonSRX talonSrxDiffWheelFrontLeft = new DevTalonSRX("talonFxDiffWheelFrontLeft", 0);
    static DevTalonSRX talonSrxDiffWheelFrontRight = new DevTalonSRX("talonFxDiffWheelFrontRight", 0);
    static DevTalonSRX talonSrxDiffWheelRearLeft = new DevTalonSRX("talonFxDiffWheelRearLeft", 0);
    static DevTalonSRX talonSrxDiffWheelRearRight = new DevTalonSRX("talonFxDiffWheelRearRight", 0);

    static DevTalonSRX talonSrxShooterBottomWheel = new DevTalonSRX("talonSrxShooterBottomWheel", 4);
    static DevTalonSRX talonSrxShooterTopWheel = new DevTalonSRX("talonSrxShooterTopWheel", 10);

    // TalonFX
    static DevTalonFX talonFxDiffWheelFrontLeft = new DevTalonFX("talonFxDiffWheelFrontLeft", 0);
    static DevTalonFX talonFxDiffWheelFrontRight = new DevTalonFX("talonFxDiffWheelFrontRight", 0);
    static DevTalonFX talonFxDiffWheelRearLeft = new DevTalonFX("talonFxDiffWheelRearLeft", 0);
    static DevTalonFX talonFxDiffWheelRearRight = new DevTalonFX("talonFxDiffWheelRearRight", 0);
    
    static DevTalonSRX talonSrxDeliveryRight = new DevTalonSRX("talonSrxDeliveryRight", 0);
    static DevTalonSRX talonSrxDeliveryLeft = new DevTalonSRX("talonSrxDeliveryLeft", 0);

    /////////////////////
    // Drive Instances //
    /////////////////////

    public static DevDifferentialDrive diffDriveTalon = new DevDifferentialDrive("diffDriveTalon",
                                                                                talonFxDiffWheelFrontLeft,
                                                                                talonFxDiffWheelFrontRight);

}
