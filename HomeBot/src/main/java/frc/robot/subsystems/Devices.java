package frc.robot.subsystems;

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

    // DiffDriver

    // Shooter
    static DevTalonSRX talonSrxShooterBottomWheel = new DevTalonSRX("talonSrxShooterBottomWheel", 4);
    static DevTalonSRX talonSrxShooterTopWheel = new DevTalonSRX("talonSrxShooterTopWheel", 10);

    // Pickup
    static DevTalonSRX talonSrxPickupRight = new DevTalonSRX("talonSrxPickupRight", 6);
    static DevTalonSRX talonSrxPickupLeft = new DevTalonSRX("talonSrxPickupLeft", 0);
    
    // Delivery
    static DevTalonSRX talonSrxDeliveryRight = new DevTalonSRX("talonSrxDeliveryRight", 16);
    static DevTalonSRX talonSrxDeliveryLeft = new DevTalonSRX("talonSrxDeliveryLeft", 7);

    // TalonFX

    // DiffDriver
    static DevTalonFX talonFxDiffWheelFrontLeft = new DevTalonFX("talonFxDiffWheelFrontLeft", 23);
    static DevTalonFX talonFxDiffWheelFrontRight = new DevTalonFX("talonFxDiffWheelFrontRight", 20);
    static DevTalonFX talonFxDiffWheelRearLeft = new DevTalonFX("talonFxDiffWheelRearLeft", 24);
    static DevTalonFX talonFxDiffWheelRearRight = new DevTalonFX("talonFxDiffWheelRearRight", 21);

    /////////////////////
    // Drive Instances //
    /////////////////////

    public static DevDifferentialDrive diffDriveTalonFX = new DevDifferentialDrive("diffDriveTalonFx",
                                                                                talonFxDiffWheelFrontLeft,
                                                                                talonFxDiffWheelFrontRight);

}
