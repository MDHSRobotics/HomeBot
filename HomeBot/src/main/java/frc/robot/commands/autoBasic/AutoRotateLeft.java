package frc.robot.commands.autoBasic;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BotSensors;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.DiffDriver;

// This command auto drives the DiffDriver forward for a short time
public class AutoRotateLeft extends CommandBase {

    private DiffDriver m_diffDriver;

    private double INITIAL_ANGLE = 0.0;
    private double RIGHT_ANGLE = 90.0;

    private static boolean isTurned = false;

    private int timesTurned = 1;

    public AutoRotateLeft(DiffDriver diffDriver) {
        Logger.setup("Constructing Command: AutoRotateLeft...");

        // Add given subsystem requirements
        m_diffDriver = diffDriver;
        addRequirements(m_diffDriver);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: AutoRotateLeft...");
    }

    @Override
    public void execute() {

        double currentAngle = BotSensors.gyro.getAngle();
        double angleElapsed = currentAngle - INITIAL_ANGLE;

        BotSensors.gyro.getAngle();

        if (angleElapsed < RIGHT_ANGLE * timesTurned) {
            m_diffDriver.driveTank(- 0.5, 0.5);
        } else {
            m_diffDriver.stop();
            isTurned = true;

        }
    }

    @Override
    public boolean isFinished() {
        if (!isTurned) {
            return false;
        } else {
            Logger.action("AutoRotateLeft: -> Stopped");
            return true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("--");
            Logger.ending("Interrupting Command: AutoRotateLeft...");
        } else {
            Logger.ending("Ending Command: AutoRotateLeft...");
        }
    }

}
