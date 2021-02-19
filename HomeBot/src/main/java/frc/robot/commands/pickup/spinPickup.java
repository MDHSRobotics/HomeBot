package frc.robot.commands.pickup;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Pickup;

// This command spins the Pickup
public class spinPickup extends CommandBase {

    
    private Pickup m_Pickup;

    public spinPickup(Pickup Pickup) {
        Logger.setup("Constructing Command: SpinPickup...");

        // Add given subsystem requirements
        m_Pickup = Pickup;
        addRequirements(m_Pickup);

    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: SpinPickupAndConveyor...");
    }

    @Override
    public void execute() {
        m_Pickup.spin();
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
            Logger.ending("Interrupting Command: SpinPickupAndConveyor...");
        } else {
            Logger.ending("Ending Command: SpinPickupAndConveyor...");
        }
    }

}