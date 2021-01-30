
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.consoles.Logger;

import static frc.robot.subsystems.Devices.talonFxTest;

// Roller Subsytem, for sucking in balls.
public class TalonFXTest extends SubsystemBase {

    public TalonFXTest() {
         Logger.setup("Constructing Subsystem: TalonFXTest...");
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    // Stop the roller
    public void stop() {
        talonFxTest.stopMotor();
    }

    // Spin the roller
    public void spin() {
        talonFxTest.set(0.5);
    }

}
