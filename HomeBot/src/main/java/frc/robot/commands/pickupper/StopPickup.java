
package frc.robot.commands.pickupper;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Pickup;

// This command stops the Pickup.
public class StopPickup extends CommandBase {

    private Pickup m_pickup;

    public StopPickup(Pickup pickup) {
        Logger.setup("Constructing Command: StopPickup...");

        // Add given subsystem requirements
        m_pickup = pickup;
        addRequirements(m_pickup);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: StopPickup...");
    }

    @Override
    public void execute() {
        m_pickup.stop();
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
        m_pickup.stop();
    }

}
