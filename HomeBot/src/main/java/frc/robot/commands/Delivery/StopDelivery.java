package frc.robot.commands.Delivery;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Delivery;

// This command stops the delivery.
public class StopDelivery extends CommandBase {

    private Delivery m_delivery;

    public StopDelivery(Delivery Delivery) {
        Logger.setup("Constructing Command: StopDelivery...");

        // Add given subsystem requirements
        m_delivery = Delivery;
        addRequirements(m_delivery);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: StopDelivery...");
    }

    @Override
    public void execute() {
        m_delivery.stop();
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
            Logger.ending("Interrupting Command: Stopdelivery...");
        } else {
            Logger.ending("Ending Command: Stopdelivery...");
        }
        m_delivery.stop();
    }

}
