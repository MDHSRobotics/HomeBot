package frc.robot.subsystems;

import frc.robot.devices.*;

import edu.wpi.first.wpilibj.Servo;

// This class contains singleton (static) instances of id mapped subsystem components.
// If a device is not connected at initialization, it should be set to null.
// IMPORTANT: Only ONE subsystem should control any given device.
// Device instances are package-private (neither private nor public) so they can only be used by subsystems.
public class Devices {

    //////////////////////
    // Device Instances //
    //////////////////////

    // Shooter
    static DevTalonSRX talonSrxShooterBottomWheel = new DevTalonSRX("talonSrxShooterBottomWheel", 4);
    static DevTalonSRX talonSrxShooterTopWheel = new DevTalonSRX("talonSrxShooterTopWheel", 10);

    // Pickup
    static DevTalonSRX talonSrxPickup = new DevTalonSRX("talonSrxPickup", 6);
    
    // Delivery
    static DevTalonSRX talonSrxDeliveryRight = new DevTalonSRX("talonSrxDeliveryRight", 16);
    static DevTalonSRX talonSrxDeliveryLeft = new DevTalonSRX("talonSrxDeliveryLeft", 7);

    // DiffDriver
    static DevTalonSRX talonSrxDiffWheelFrontLeft = new DevTalonSRX("talonSrxDiffWheelFrontLeft", 8);
    static DevTalonSRX talonSrxDiffWheelFrontRight = new DevTalonSRX("talonSrxDiffWheelFrontRight", 15);
    static DevTalonSRX talonSrxDiffWheelRearLeft = new DevTalonSRX("talonSrxDiffWheelRearLeft", 11);
    static DevTalonSRX talonSrxDiffWheelRearRight = new DevTalonSRX("talonSrxDiffWheelRearRight", 6);

    // DiffDriver
    static DevTalonSRX talonFxDiffWheelFrontLeft = new DevTalonSRX("talonSrxDiffWheelFrontLeft", 8);
    static DevTalonSRX talonFxDiffWheelFrontRight = new DevTalonSRX("talonSrxDiffWheelFrontRight", 15);
    static DevTalonSRX talonFxDiffWheelRearLeft = new DevTalonSRX("talonSrxDiffWheelRearLeft", 11);
    static DevTalonSRX talonFxDiffWheelRearRight = new DevTalonSRX("talonSrxDiffWheelRearRight", 6);

    //Sensors
    static Servo servoGate = new Servo(1);

    /////////////////////
    // Drive Instances //
    /////////////////////

    public static DevDifferentialDrive diffDriveTalonSRX = new DevDifferentialDrive("diffDriveTalonFx",
                                                                                talonSrxDiffWheelFrontLeft,
                                                                                talonSrxDiffWheelFrontRight);

     public static DevDifferentialDrive diffDriveTalonFX = new DevDifferentialDrive("diffDriveTalonFx",
                                                                              talonFxDiffWheelFrontLeft,
                                                                              talonFxDiffWheelFrontRight);

}
