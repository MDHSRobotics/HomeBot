
package frc.robot.commands.gate;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Gate;

// This command spins the Gate
public class FeedGate extends CommandBase {

    private Gate m_gate;

    public FeedGate(Gate gate) {
        Logger.setup("Constructing Command: FeedGate...");

        // Add given subsystem requirements
        m_gate = gate;
        addRequirements(m_gate);

    }

    @Override
    public void initialize() {
        Logger.setup("Initializing Command: FeedGate...");
    }

    @Override
    public void execute() {
        m_gate.feedGate();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("--");
            Logger.ending("Interrupting Command: FeedGate...");
        } else {
            Logger.ending("Ending Command: FeedGate...");
        }
    }

}