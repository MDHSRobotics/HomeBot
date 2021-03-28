package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.DiffDriver;

import java.io.IOException;
import java.nio.file.Path;

// This command automatically a predetermined pathweaver path.
public class MoveForwardAuto extends CommandBase {

    private DiffDriver m_diffDriver;
    private boolean m_hasMoved = false;
    private double m_targetDistance = 0.0;


    public MoveForwardAuto(DiffDriver diffDriver, double targetDistance) {
        Logger.setup("Constructing Command: MoveForwardAuto...");

        // Add given subsystem requirements
        m_targetDistance = targetDistance;
        m_diffDriver = diffDriver;
        addRequirements(m_diffDriver);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: MoveForwardAuto...");

        int leftPosition = m_diffDriver.getPositionLeft();
        int rightPosition = m_diffDriver.getPositionRight();

        // if (leftPosition != 0) {
            Logger.debug("MoveForwardAutoInitialize: Left Wheel Position --> " + leftPosition);
        //}

        //if (rightPosition != 0) {
            Logger.debug("MoveForwardAutoInitialize: Right Wheel Position --> " + rightPosition);
        //}

        m_diffDriver.moveForwardAuto(m_targetDistance);

    }

    @Override
    public void execute() {
        m_diffDriver.feed();
        int leftPosition = m_diffDriver.getPositionLeft();
        int rightPosition = m_diffDriver.getPositionRight();
        if (leftPosition != 0) {
            Logger.debug("MoveForwardAuto: Left Wheel Position --> " + leftPosition);
        }

        if (rightPosition != 0) {
            Logger.debug("MoveForwardAuto: Right Wheel Position --> " + rightPosition);
        }
        
    }

    // This command continues until
    @Override
    public boolean isFinished() {
        // DifferentialDriveWheelSpeeds velocityObject = m_diffDriver.getWheelSpeeds();
        // double leftVelocity = velocityObject.leftMetersPerSecond;
        // boolean isMovingNow = leftVelocity > 0.000001;

        // if (m_hasMoved) {
        //     if (isMovingNow) {
        //         //robot has moved before and is continuing to move
        //         return false;
        //     } else {
        //         //robot has moved before and has stopped moving
        //         return true;
        //     }
        // } else {
        //     if (isMovingNow) {
        //         //first time robot has moved
        //         m_hasMoved = true;
        //     }
        // }
        // //robot has never moved
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Logger.info("MoveForwardAuto interrupted");
    }



}