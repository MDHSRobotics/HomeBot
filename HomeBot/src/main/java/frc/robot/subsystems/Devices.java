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
    public static DevTalonSRX talonSrxShooterBottomWheel = new DevTalonSRX("talonSrxShooterBottomWheel", 4);
    public static DevTalonSRX talonSrxShooterTopWheel = new DevTalonSRX("talonSrxShooterTopWheel", 10);

    static DevTalonSRX talonSrxTurret = new DevTalonSRX("talonSrxTurret", 2); 
    static DevTalonSRX talonSrxShooterHood = new DevTalonSRX("talonSrxShooterHood", 0);
    
    // Delivery
    public static DevTalonSRX talonSrxDeliveryRight = new DevTalonSRX("talonSrxDeliveryRight", 16);
    public static DevTalonSRX talonSrxDeliveryLeft = new DevTalonSRX("talonSrxDeliveryLeft", 7);

    // DiffDriver
    public static DevTalonFX talonFxDiffWheelFrontLeft = new DevTalonFX("talonFxDiffWheelFrontLeft", 23);
    public static DevTalonFX talonFxDiffWheelFrontRight = new DevTalonFX("talonFxDiffWheelFrontRight", 20);
    public static DevTalonFX talonFxDiffWheelRearLeft = new DevTalonFX("talonFxDiffWheelRearLeft", 24);
    public static DevTalonFX talonFxDiffWheelRearRight = new DevTalonFX("talonFxDiffWheelRearRight", 21);

    //Sensors
    public static Servo servoGate = new Servo(1);

    /////////////////////
    // Drive Instances //
    /////////////////////

    public static DevDifferentialDrive diffDriveTalonFX = new DevDifferentialDrive("diffDriveTalonFx",
                                                                                talonFxDiffWheelFrontLeft,
                                                                                talonFxDiffWheelFrontRight);

}
