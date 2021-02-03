package frc.robot.commands.talonFXTester;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.TalonFXTest;

// This command spins the Roller and moves the Conveyor forward.
public class TestTalonFX extends CommandBase {

    private TalonFXTest m_talonFXTest;

    public TestTalonFX(TalonFXTest talonFXTest) {
        Logger.setup("Constructing Command: TestTalonFX...");

        // Add given subsystem requirements
        m_talonFXTest = talonFXTest;
        addRequirements(m_talonFXTest);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: TestTalonFX...");
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
            Logger.ending("Interrupting Command: TestTalonFX...");
        } else {
            Logger.ending("Ending Command: TestTalonFX...");
        }
    }

	public static void setDefaultCommand(TestTalonFX testTalonFX) {
	}

}