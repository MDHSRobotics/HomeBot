
package frc.robot.commands.pickupper;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Pickup;

// This command spins the Pickup
public class StartCompressor extends CommandBase {

    private Pickup m_pickup;

    public StartCompressor(Pickup pickup) {
        Logger.setup("Constructing Command: StartCompressor...");

        // Add given subsystem requirements
        m_pickup = pickup;
        addRequirements(m_pickup);

    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: StartCompressor...");
    }

    @Override
    public void execute() {
        m_pickup.startCompressor();
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
            Logger.ending("Interrupting Command: StartCompressor...");
        } else {
            Logger.ending("Ending Command: StartCompressor...");
        }
    }

}