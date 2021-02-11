
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

    static DevTalonSRX talonSrxShooterBottomWheel = new DevTalonSRX("talonSrxShooterBottomWheel", 0);
    static DevTalonSRX talonSrxShooterTopWheel = new DevTalonSRX("talonSrxShooterTopWheel", 0);

    // TalonFX
    static DevTalonFX talonFxDiffWheelFrontLeft = new DevTalonFX("talonFxDiffWheelFrontLeft", 1);
    static DevTalonFX talonFxDiffWheelFrontRight = new DevTalonFX("talonFxDiffWheelFrontRight", 2);
    static DevTalonFX talonFxDiffWheelRearLeft = new DevTalonFX("talonFxDiffWheelRearLeft", 3);
    static DevTalonFX talonFxDiffWheelRearRight = new DevTalonFX("talonFxDiffWheelRearRight", 4);
    

    /////////////////////
    // Drive Instances //
    /////////////////////

    public static DevDifferentialDrive diffDriveTalon = new DevDifferentialDrive("diffDriveTalon",
                                                                                talonSrxDiffWheelFrontLeft,
                                                                                talonSrxDiffWheelFrontRight);

}
