package frc.robot.commands.talonFXTester;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.TalonFXTest;

// This command spins the Roller and moves the Conveyor forward.
public class TalonFXTester extends CommandBase {

    private TalonFXTest m_talonFXTest;

    public TalonFXTester(TalonFXTest talonFXTest) {
        Logger.setup("Constructing Command: TalonFXTester...");

        // Add given subsystem requirements
        m_talonFXTest = talonFXTest;
        addRequirements(m_talonFXTest);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: TalonFXTester...");
    }

    @Override
    public void execute() {
        m_talonFXTest.spin();
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
            Logger.ending("Interrupting Command: TalonFXTester...");
        } else {
            Logger.ending("Ending Command: TalonFXTester...");
        }
    }

}