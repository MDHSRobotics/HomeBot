
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.HoodShooter;

// This command stops the Shooter.
public class StopShoot extends CommandBase {

    private HoodShooter m_shooter;

    public StopShoot(HoodShooter shooter) {
        Logger.setup("Constructing Command: StopShoot...");

        // Add given subsystem requirements
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: StopShoot...");
    }

    @Override
    public void execute() {
        m_shooter.stop();
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
            Logger.ending("Interrupting Command: StopShoot...");
        } else {
            Logger.ending("Ending Command: StopShoot...");
        }
    }

}
