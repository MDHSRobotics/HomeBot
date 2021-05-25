
package frc.robot.commands.gate;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Gate;

// This command spins the Gate
public class ToggleGate extends CommandBase {

    private Gate m_gate;

    public ToggleGate(Gate gate) {
        Logger.setup("Constructing Command: ToggleGate...");

        // Add given subsystem requirements
        m_gate = gate;
        addRequirements(m_gate);

    }

    @Override
    public void initialize() {
        Logger.setup("Initializing Command: ToggleGate...");
    }

    @Override
    public void execute() {
        m_gate.openGate();;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("--");
            Logger.ending("Interrupting Command: ToggleGate...");
        } else {
            Logger.ending("Ending Command: ToggleGate...");
        }
    }

}