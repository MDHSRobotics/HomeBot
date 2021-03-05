package frc.robot.commands.autoBasic;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.DiffDriver;

// This command auto drives the DiffDriver forward for a short time.
public class GalacticSearchBlue extends CommandBase {

    private DiffDriver m_diffDriver;

    private Timer m_timer = new Timer();
    private double m_timeLastPrinted = 0.0;

    public GalacticSearchBlue(DiffDriver diffDriver) {
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

        Logger.action("AutoDriveForward: -> Beginning Sequence!");
    }

    @Override
    public void execute() {
        double currentTime = m_timer.get();
        double timeElapsedSincePrint = currentTime - m_timeLastPrinted;

        // Begin sequence

        if (currentTime > 0 && currentTime < 0.6) {          // drive to B3
            m_diffDriver.moveForwardAuto();
            Logger.action("AutoDriveForward: -> Drive to B3");
        }

        else if (currentTime > 0.6 && currentTime < 2.0){    // stop
            m_diffDriver.stop();
        }
        
        else if(currentTime > 3.0 && currentTime < 6.0){     // turn to D5
            m_diffDriver.driveAlign(35);
            Logger.action("AutoDriveForward: -> Turn to D5");
        }

        else if (currentTime > 6.0 && currentTime < 6.8){    // drive to D5
            m_diffDriver.moveForwardAuto();
            Logger.action("AutoDriveForward: -> Drive to D5");
        }

        else if (currentTime > 6.8 && currentTime < 8.0){    // stop
            m_diffDriver.stop();
        }

        else if(currentTime > 9.0 && currentTime < 12.0){    // turn to D6
            m_diffDriver.driveAlign(0);
            Logger.action("AutoDriveForward: -> Turn to D6");
        }

        else if (currentTime > 13.0 && currentTime < 13.26){ // move to D6
            m_diffDriver.moveForwardAuto();
            Logger.action("AutoDriveForward: -> Drive to D6");
        }

        else if (currentTime > 13.26 && currentTime < 15.0){ // stop
            m_diffDriver.stop();
        }

        else if (currentTime > 16.0 && currentTime < 19.0){  // turn to B8
            m_diffDriver.driveAlign(-45);
            Logger.action("AutoDriveForward: -> Turn to B8");
        }

        else if (currentTime > 20.0 && currentTime < 20.8){  // move to B8
            m_diffDriver.moveForwardAuto();
            Logger.action("AutoDriveForward: -> Drive to B8");
        }

        else if (currentTime > 20.8 && currentTime < 22.0){  // stop
            m_diffDriver.stop();
        }

        else if (currentTime > 23.0 && currentTime < 26.0){  // turn to D10
            m_diffDriver.driveAlign(90);
            Logger.action("AutoDriveForward: -> Turn to D10");
        }

        else if (currentTime > 27.0 && currentTime < 27.7){  // move to D10
            m_diffDriver.moveForwardAuto();
            Logger.action("AutoDriveForward: -> Drive to D10");
        }

        else if (currentTime > 27.7 && currentTime < 29.0){  // stop
            m_diffDriver.stop();
        }

        else if (currentTime > 30.0 && currentTime < 33.0){  // turn to D11
            m_diffDriver.driveAlign(-45);
            Logger.action("AutoDriveForward: -> Turn to D11");
        }

        else if (currentTime > 34.0 && currentTime < 34.26){ // move to D11
            m_diffDriver.moveForwardAuto();
            Logger.action("AutoDriveForward: -> Drive to D11");
        }

        else if (currentTime > 34.26 && currentTime < 36.0){ // stop
            m_diffDriver.stop();
        }

        else {
            m_diffDriver.stop();
                Logger.action("AutoDriveForward: -> Stopped for " + currentTime);
                m_timeLastPrinted = currentTime;
        }

    }

    // This command continues until it MAX_DRIVE_SECONDS is reached
    @Override
    public boolean isFinished() {
        return false;
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
