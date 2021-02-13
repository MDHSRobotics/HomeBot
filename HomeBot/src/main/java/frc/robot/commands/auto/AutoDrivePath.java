package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.DiffDriver;

import java.io.IOException;
import java.nio.file.Path;

// This command automatically a predetermined pathweaver path.
public class AutoDrivePath extends CommandBase {

    private DiffDriver m_diffDriver;

    public AutoDrivePath(DiffDriver diffDriver) {
        Logger.setup("Constructing Command: AutoDrivePath...");

        // Add given subsystem requirements
        m_diffDriver = diffDriver;
        addRequirements(m_diffDriver);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: AutoDrivePath...");

        String trajectoryJSON = "paths/Practice.wpilib.json";

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);

            Logger.info("Trajectory created.");
        }

        catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
    }

    @Override
    public void execute() {
        
    }

    // This command continues until
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
                //TODO add log statement
    }



}