package frc.robot.commands.autoBasic;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.DiffDriver;

// This command auto drives the DiffDriver forward for a short time.
public class GalacticSearchRed extends CommandBase {

    private DiffDriver m_diffDriver;

    private Timer m_timer = new Timer();
    private double m_timeLastPrinted = 0.0;

    private static final double MAX_DRIVE_SECONDS = 0.5;

    public GalacticSearchRed(DiffDriver diffDriver) {
        Logger.setup("Constructing Command: AutoDriveForward...");

        // Add given subsystem requirements
        m_diffDriver = diffDriver;
        addRequirements(m_diffDriver);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: AutoDriveForward...");

        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute() {
        double currentTime = m_timer.get();
        double timeElapsedSincePrint = currentTime - m_timeLastPrinted;
        if (currentTime < 0.5) {
            m_diffDriver.moveForwardAuto();
            if (timeElapsedSincePrint > 1.0) {
                Logger.action("AutoDriveForward: -> Moved Forward for " + currentTime);
                m_timeLastPrinted = currentTime;
            }
        }

        else if (currentTime > 0.5 && currentTime < 2.0){
            m_diffDriver.stop();
        }
        
        else if(currentTime > 3.0 && currentTime < 4.0){
            m_diffDriver.driveAlign(48);
        }

        else if (currentTime > 4.0 && currentTime < 4.7){
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 6.0 && currentTime < 7.0){
            m_diffDriver.stop();
        }

        else if (currentTime > 7.0 && currentTime < 8.0){
            m_diffDriver.driveAlign(-85);
        }

        else if (currentTime > 9.0 && currentTime < 9.7){
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 9.7 && currentTime < 11.0){
            m_diffDriver.stop();
        }

        else if (currentTime > 12.0 && currentTime < 13.0){
            m_diffDriver.driveAlign(35);
        }

        else if (currentTime > 14.0 && currentTime < 15.5){
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 15.5 && currentTime < 17.0){
            m_diffDriver.stop();
        }

        else {
            m_diffDriver.stop();
            if (timeElapsedSincePrint > 1.0) {
                Logger.action("AutoDriveForward: -> Stopped for " + currentTime);
                m_timeLastPrinted = currentTime;
            }
        }

    }

    // This command continues until it MAX_DRIVE_SECONDS is reached
    @Override
    public boolean isFinished() {
        return false;
        // double currentTime = m_timer.get();

        // if (currentTime < MAX_DRIVE_SECONDS) {
        //     return false;
        // } else {
        //     m_diffDriver.stop();
        //     // Logger.action("AutoDriveForward: -> Stopped");
        //     return false;
        // }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("--");
            Logger.ending("Interrupting Command: AutoDriveForward...");
        } else {
            Logger.ending("Ending Command: AutoDriveForward...");
        }

        m_diffDriver.stop();
    }

}
