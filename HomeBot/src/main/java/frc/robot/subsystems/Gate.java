package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.consoles.Logger;

import static frc.robot.subsystems.Devices.servoGate;

public class Gate extends SubsystemBase {

    protected static final double kDefaultMaxServoPWM = 2.4;
    protected static final double kDefaultMinServoPWM = 0.6;

    public Gate() {
        servoGate.set(0.5);
        Logger.setup("Constructing Subsystem: Gate...");
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void toggleGate() {
        if (servoGate.get() == 0.5) {
            servoGate.set(1.0);
        } else if (servoGate.get() == 1.0){ 
            servoGate.set(0.5);

        }
        
    }

    public void openGate(){
        servoGate.set(1.0);
    }

    public void closeGate(){
        servoGate.set(0.5);
    }

    public static void feedGate() {
        
    }
}