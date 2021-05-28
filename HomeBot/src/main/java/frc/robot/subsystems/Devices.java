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

    // Pickup
    public static DevTalonSRX talonSrxPickup = new DevTalonSRX("talonSrxPickup", 6);
    
    // Delivery
    public static DevTalonSRX talonSrxDeliveryRight = new DevTalonSRX("talonSrxDeliveryRight", 16);
    public static DevTalonSRX talonSrxDeliveryLeft = new DevTalonSRX("talonSrxDeliveryLeft", 7);

    // Sensors
    public static Servo servoGate = new Servo(1);

    // SwerveModule
    static DevTalonFX talonFxSwerveDriveWheelFrontLeft = new DevTalonFX("talonFxSwerveDriveWheelFrontLeft", 28);
    static DevTalonFX talonFxSwerveDriveWheelFrontRight = new DevTalonFX("talonFxSwerveDriveWheelFrontLeft", 26);
    static DevTalonFX talonFxSwerveDriveWheelRearLeft = new DevTalonFX("talonFxSwerveDriveWheelFrontLeft", 31);
    static DevTalonFX talonFxSwerveDriveWheelRearRight = new DevTalonFX("talonFxSwerveDriveWheelFrontLeft", 25);
    static DevTalonFX talonFxSwerveTurnWheelFrontLeft = new DevTalonFX("talonFxSwerveDriveWheelFrontLeft", 30);
    static DevTalonFX talonFxSwerveTurnWheelFrontRight = new DevTalonFX("talonFxSwerveDriveWheelFrontLeft", 32);
    static DevTalonFX talonFxSwerveTurnWheelRearLeft = new DevTalonFX("talonFxSwerveDriveWheelFrontLeft", 27);
    static DevTalonFX talonFxSwerveTurnWheelRearRight = new DevTalonFX("talonFxSwerveDriveWheelFrontLeft", 29);

    /////////////////////
    // Drive Instances //
    /////////////////////
    
    //Swerve Modules
    public static final DevSwerveModule frontLeftSwerveModule = new DevSwerveModule(talonFxSwerveDriveWheelFrontLeft, talonFxSwerveTurnWheelFrontLeft, 0.24, 0.24, true, true);
    public static final DevSwerveModule frontRightSwerveModule = new DevSwerveModule(talonFxSwerveDriveWheelFrontRight, talonFxSwerveTurnWheelFrontRight, 0.24, -0.24, true, true);
    public static final DevSwerveModule rearLeftSwerveModule = new DevSwerveModule(talonFxSwerveDriveWheelRearLeft, talonFxSwerveTurnWheelRearLeft, -0.24, 0.24, true, true);
    public static final DevSwerveModule rearRightSwerveModule = new DevSwerveModule(talonFxSwerveDriveWheelRearRight, talonFxSwerveTurnWheelRearRight, -0.24, -0.24, true, true);

    
}
