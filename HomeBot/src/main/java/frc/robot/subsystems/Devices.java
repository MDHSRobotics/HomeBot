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
    static DevTalonSRX talonSrxDiffWheelFrontLeft = new DevTalonSRX("talonFxDiffWheelFrontLeft", 15);
    static DevTalonSRX talonSrxDiffWheelFrontRight = new DevTalonSRX("talonFxDiffWheelFrontRight", 11);
    static DevTalonSRX talonSrxDiffWheelRearLeft = new DevTalonSRX("talonFxDiffWheelRearLeft", 6);
    static DevTalonSRX talonSrxDiffWheelRearRight = new DevTalonSRX("talonFxDiffWheelRearRight", 8);

    static DevTalonSRX talonSrxShooterTopWheel = new DevTalonSRX("talonSrxShooterTopWheel", 0);
    static DevTalonSRX talonSrxShooterBottomWheel = new DevTalonSRX("talonSrxShooterBottomWheel", 0);
    static DevTalonSRX talonSrxTurret = new DevTalonSRX("talonSrxTurret", 2); 
    static DevTalonSRX talonSrxShooterHood = new DevTalonSRX("talonSrxShooterHood", 0);
    // TalonFX
    static DevTalonFX talonFxDiffWheelFrontLeft = new DevTalonFX("talonFxDiffWheelFrontLeft", 3);
    static DevTalonFX talonFxDiffWheelFrontRight = new DevTalonFX("talonFxDiffWheelFrontRight", 1);
    static DevTalonFX talonFxDiffWheelRearLeft = new DevTalonFX("talonFxDiffWheelRearLeft", 4);
    static DevTalonFX talonFxDiffWheelRearRight = new DevTalonFX("talonFxDiffWheelRearRight", 2);
    
    static DevTalonSRX talonSrxDeliveryRight = new DevTalonSRX("talonSrxDeliveryRight", 3);
    static DevTalonSRX talonSrxDeliveryLeft = new DevTalonSRX("talonSrxDeliveryLeft", 16);

    /////////////////////
    // Drive Instances //
    /////////////////////

    public static DevDifferentialDrive diffDriveTalon = new DevDifferentialDrive("diffDriveTalon",
                                                                                talonFxDiffWheelFrontLeft,
                                                                                talonFxDiffWheelFrontRight);

}
