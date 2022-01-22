
package frc.robot.commands.pickupper;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Pickup;

// This command spins the Pickup
public class StopCompressor extends CommandBase {

    private Pickup m_pickup;

    public StopCompressor(Pickup pickup) {
        Logger.setup("Constructing Command: StopCompressor...");

        // Add given subsystem requirements
        m_pickup = pickup;
        addRequirements(m_pickup);

    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: StopCompressor...");
    }

    @Override
    public void execute() {
        m_pickup.stopCompressor();
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
            Logger.ending("Interrupting Command: StopCompressor...");
        } else {
            Logger.ending("Ending Command: StopCompressor...");
        }
    }

}