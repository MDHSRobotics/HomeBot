
package frc.robot.commands.diffdriver;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.DiffDriver;

// This command stops the DiffDriver.
public class TestDiffDrive extends InstantCommand {

    private DiffDriver m_diffDriver;
    private int m_testNumber;

    public TestDiffDrive(DiffDriver diffDriver, int testNumber) {
        Logger.setup("Constructing Command: TestDiffDrive...");

        // Add given subsystem requirements
        m_diffDriver = diffDriver;
        addRequirements(m_diffDriver);

        m_testNumber = testNumber;
    }

    @Override
    public void initialize() {
        m_diffDriver.test(m_testNumber);
    }

}
