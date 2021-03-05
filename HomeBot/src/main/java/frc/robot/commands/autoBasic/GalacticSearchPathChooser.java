package frc.robot.commands.autoBasic;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.sensors.Pixy;
import frc.robot.subsystems.DiffDriver;

// This command auto drives the DiffDriver forward for a short time
public class GalacticSearchPathChooser extends CommandBase {

    private DiffDriver m_diffDriver;

    private Timer m_timer = new Timer();
    private Timer m_timer2 = new Timer();
    private double m_timeLastPrinted = 0.0;

    private boolean isFinished = false;

    public GalacticSearchPathChooser(DiffDriver diffDriver) {
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

        m_timer2.reset();
        m_timer2.start();
    }

    @Override
    public void execute() {

        double currentTime = m_timer.get();
        double currentTime2 = m_timer2.get();
        double timeElapsedSincePrint = currentTime - m_timeLastPrinted;

        int currentVal = 0;

        while(currentTime2 < 5.0){
            currentVal = Pixy.detectFieldMode();
        }

        boolean isRed = (currentVal != -1);

    if (isRed){    
        if (currentTime < 0.5) {
            m_diffDriver.moveForwardAuto();
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
            this.isFinished = true;
        }

        else {
            m_diffDriver.stop();
            if (timeElapsedSincePrint > 1.0) {
                Logger.action("AutoDriveForward: -> Stopped for " + currentTime);
                m_timeLastPrinted = currentTime;
            }
        }
    }
    else{
        // Begin Blue sequence

        Logger.action("AutoDriveForward: -> Beginning Sequence!");

        if (currentTime > 0 && currentTime < 0.5) {          // drive to B3
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 0.5 && currentTime < 2.0){    // stop
            m_diffDriver.stop();
        }
        
        else if(currentTime > 3.0 && currentTime < 6.0){     // turn to D5
            m_diffDriver.driveAlign(45);
        }

        else if (currentTime > 6.0 && currentTime < 6.7){    // drive to D5
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 6.7 && currentTime < 8.0){    // stop
            m_diffDriver.stop();
        }

        else if(currentTime > 9.0 && currentTime < 12.0){    // turn to D6
            m_diffDriver.driveAlign(-45);
        }

        else if (currentTime > 13.0 && currentTime < 13.26){ // move to D6
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 13.26 && currentTime < 15.0){ // stop
            m_diffDriver.stop();
        }

        else if (currentTime > 16.0 && currentTime < 19.0){  // turn to B8
            m_diffDriver.driveAlign(-45);
        }

        else if (currentTime > 20.0 && currentTime < 20.7){  // move to B8
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 20.7 && currentTime < 22.0){  // stop
            m_diffDriver.stop();
        }

        else if (currentTime > 23.0 && currentTime < 26.0){  // turn to D10
            m_diffDriver.driveAlign(90);
        }

        else if (currentTime > 27.0 && currentTime < 27.7){  // move to D10
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 27.7 && currentTime < 29.0){  // stop
            m_diffDriver.stop();
        }

        else if (currentTime > 30.0 && currentTime < 33.0){  // turn to D11
            m_diffDriver.driveAlign(-45);
        }

        else if (currentTime > 34.0 && currentTime < 34.26){ // move to D11
            m_diffDriver.moveForwardAuto();
        }

        else if (currentTime > 34.26 && currentTime < 36.0){ // stop
            m_diffDriver.stop();
            this.isFinished = true;
        }

        else {
            m_diffDriver.stop();
                Logger.action("AutoDriveForward: -> Stopped for " + currentTime);
                m_timeLastPrinted = currentTime;
        }
    }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("--");
            Logger.ending("Interrupting Command: GalacticSearchPathChooser...");
        } else {
            Logger.ending("Ending Command: GalacticSearchPathChooser...");
        }
    }

}
