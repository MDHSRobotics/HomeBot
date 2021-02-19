package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.consoles.Logger;

import static frc.robot.subsystems.Devices.talonSrxPickupRight;
import static frc.robot.subsystems.Devices.talonSrxPickupLeft;

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
        talonSrxPickupRight.stopMotor();
        talonSrxPickupLeft.stopMotor();
    }

    // Spin the Pickup
    public void spin() {
        talonSrxPickupRight.set(-0.5);
        talonSrxPickupLeft.set(0.5);
    }

}
