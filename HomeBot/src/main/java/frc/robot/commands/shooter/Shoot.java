
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Shooter;

// This command activates the shoot mechanism.
public class Shoot extends CommandBase {

    private Shooter m_shooter;

    public Shoot(Shooter shooter) {
        Logger.setup("Constructing Command: Shoot...");

        // Add given subsystem requirements
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: Shoot...");

        m_shooter.shootBasedOnTPHMS();
    }

    @Override
    public void execute() {

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
            Logger.ending("Interrupting Command: Shoot...");
        } else {
            Logger.ending("Ending Command: Shoot...");
        }
    }

}
