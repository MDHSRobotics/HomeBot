
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Shooter;

// This command resets the shoot mechanism.
public class ResetShoot extends InstantCommand {

    private Shooter m_shooter;

    public ResetShoot(Shooter shooter) {
        Logger.setup("Constructing InstantCommand: ResetShoot...");

        m_shooter = shooter;
    }

    @Override
    public void initialize() {
        Logger.action("Initializing InstantCommand: ResetShoot...");

        m_shooter.reset();

        Logger.action("ResetShoot -> Shooter min/max velocity values RESET!");
    }

}
