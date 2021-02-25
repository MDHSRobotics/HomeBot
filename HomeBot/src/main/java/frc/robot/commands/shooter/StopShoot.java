
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Shooter;

// This command stops the shoot motors.
public class StopShoot extends CommandBase {

    private Shooter m_shooter;

    public StopShoot(Shooter shooter) {
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
