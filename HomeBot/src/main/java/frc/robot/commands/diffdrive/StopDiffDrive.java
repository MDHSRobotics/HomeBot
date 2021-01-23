
package frc.robot.commands.diffdriver;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.DiffDriver;

// This command stops the DiffDriver.
public class StopDiffDrive extends CommandBase {

    private DiffDriver m_diffDriver;

    public StopDiffDrive(DiffDriver diffDriver) {
        Logger.setup("Constructing Command: StopDiffDrive...");

        // Add given subsystem requirements
        m_diffDriver = diffDriver;
        addRequirements(m_diffDriver);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: StopDiffDrive...");
    }

    @Override
    public void execute() {
        m_diffDriver.stop();
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
            Logger.ending("Interrupting Command: StopDiffDrive...");
        } else {
            Logger.ending("Ending Command: StopDiffDrive...");
        }
        m_diffDriver.stop();
    }

}
