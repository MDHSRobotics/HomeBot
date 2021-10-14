
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.HoodShooter;
//This command set the Flap's Angle
public class SetFlapAngle extends CommandBase {
    
    private HoodShooter m_shooter;
    
    public SetFlapAngle(HoodShooter shooter) {
        Logger.setup("Constructing Command: StopShoot...");

        // Add given subsystem requirements
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: SetFlapAngle...");
    }

    @Override
    public void execute() {
        m_shooter.setFlapAngle();
    }

    // This command continues until interrupted
    @Override
    public boolean isFinished() {
        return false;
    }
}
