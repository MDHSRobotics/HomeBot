package frc.robot.commands.pickup;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Pickup;

// This command stops the Pickup.
public class stopPickup extends CommandBase {

    private Pickup m_Pickup;

    public stopPickup(Pickup Pickup) {
        Logger.setup("Constructing Command: StopPickup...");

        // Add given subsystem requirements
        m_Pickup = Pickup;
        addRequirements(m_Pickup);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: StopPickup...");
    }

    @Override
    public void execute() {
        m_Pickup.stop();
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
            Logger.ending("Interrupting Command: stopPickup...");
        } else {
            Logger.ending("Ending Command: stopPickup...");
        }
        m_Pickup.stop();
    }

}
