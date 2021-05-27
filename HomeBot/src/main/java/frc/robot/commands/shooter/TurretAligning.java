package frc.robot.commands.shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Turret;
import frc.robot.consoles.Logger;

public class TurretAligning extends CommandBase {
    private Turret m_turret; 
    
    public TurretAligning(Turret turret){
        Logger.setup("Constructing Command: TurretAligning...");
        //Add given subsystem requirements 
        m_turret = turret; 
        addRequirements(m_turret);
    }
    
    @Override
    public void initialize() {
        Logger.action("Initializing Command: AligningTurret...");

    }   
    
    @Override
    public void execute() {

        m_turret.rotateTurretPercent();
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
            Logger.ending("Interrupting Command: TurretAligning...");
        } else {
            Logger.ending("Ending Command: TurretAligning...");
        }
    }

}