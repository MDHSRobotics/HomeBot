
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Delivery;

// This command activates the shoot mechanism.
public class DeliverAndShoot extends CommandBase {

    private Shooter m_shooter;
    private Delivery m_delivery;

    public DeliverAndShoot(Delivery delivery, Shooter shooter) {
        Logger.setup("Constructing Command: DeliverAndShoot...");

        // Add given subsystem requirements
        m_delivery = delivery;
        addRequirements(m_delivery);

        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: DeliverAndShoot...");

        m_delivery.spin();
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
            Logger.ending("Interrupting Command: DeliverAndShoot...");
        } else {
            Logger.ending("Ending Command: DeliverAndShoot...");
        }
    }

}
