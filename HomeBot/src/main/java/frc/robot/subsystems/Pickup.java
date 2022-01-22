package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.consoles.Logger;

import static frc.robot.subsystems.Devices.talonSrxPickup;
import static frc.robot.subsystems.Devices.compressor;
import static frc.robot.subsystems.Devices.solenoid;

// Pickup Subsytem, for sucking in balls.
public class Pickup extends SubsystemBase {

    public Pickup() {
         Logger.setup("Constructing Subsystem: Pickup...");
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    // Stop the Pickup
    public void stop() {
        talonSrxPickup.stopMotor();
    }

    // Spin the Pickup
    public void spin() {
        talonSrxPickup.set(0.5);
    }

    public void openSolenoid(){
        solenoid.set(true);
    }

    public void closeSolenoid(){
        solenoid.set(false);
    }

    public void startCompressor(){
        compressor.start();
    }

    public void stopCompressor(){
        compressor.stop();
    }
}
