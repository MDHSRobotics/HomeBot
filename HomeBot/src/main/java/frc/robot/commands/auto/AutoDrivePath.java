
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.DiffDriver;


// This command automatically a predetermined pathweaver path.
public class AutoDrivePath extends CommandBase {

    private DiffDriver m_diffDriver;

    public AutoDrivePath(DiffDriver diffDriver) {
        Logger.setup("Constructing Command: AutoDrivePath...");

        // Add given subsystem requirements
        m_diffDriver = diffDriver;
        addRequirements(m_diffDriver);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: AutoDrivePath...");
    }

    @Override
    public void execute() {

    }

    // This command continues until
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Logger.info("AutoDrivePath interrupted");
    }

}
