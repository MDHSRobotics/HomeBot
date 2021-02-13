
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.consoles.Logger;

import static frc.robot.subsystems.Devices.talonSrxDeliveryRight;
import static frc.robot.subsystems.Devices.talonSrxDeliveryLeft;

// Delivery Subsytem, for sucking in balls.
public class Delivery extends SubsystemBase {

    public Delivery() {
         Logger.setup("Constructing Subsystem: Delivery...");
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    // Stop the Delivery
    public void stop() {
        talonSrxDeliveryRight.stopMotor();
        talonSrxDeliveryLeft.stopMotor();
    }

    // Spin the Delivery
    public void spin() {
        talonSrxDeliveryRight.set(0.5);
        talonSrxDeliveryLeft.set(0.5);
    }

}
