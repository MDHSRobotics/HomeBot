package frc.robot.commands.Delivery;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Delivery;

// This command spins the Delivery
public class SpinDelivery extends CommandBase {

    
    private Delivery m_Delivery;

    public SpinDelivery(Delivery Delivery) {
        Logger.setup("Constructing Command: SpinDelivery...");

        // Add given subsystem requirements
        m_Delivery = Delivery;
        addRequirements(m_Delivery);

    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: SpinDelivery...");
    }

    @Override
    public void execute() {
        m_Delivery.spin();
    }

    // This command continues until interrupted
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("--");
            Logger.ending("Interrupting Command: SpinDelivery...");
        } else {
            Logger.ending("Ending Command: SpinDelivery...");
        }
    }

}