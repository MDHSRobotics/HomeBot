
package frc.robot.commands.diffdriver;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.oi.controllers.XboxPositionAccessible;
import frc.robot.oi.movements.ArcadeMovement;
import frc.robot.subsystems.DiffDriver;

// This command uses the joystick input to differential drive using the arcade method.
public class DriveDiffArcade extends CommandBase {

    public XboxPositionAccessible controller;
    private DiffDriver m_diffDriver;

    public DriveDiffArcade(DiffDriver diffDriver, XboxPositionAccessible controller) {
        Logger.setup("Constructing Command: DriveDiffArcade...");

        // Add given subsystem requirements
        this.controller = controller;
        m_diffDriver = diffDriver;
        addRequirements(m_diffDriver);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        ArcadeMovement move = ArcadeMovement.getMovement(controller, m_diffDriver.controlStickDirectionFlipped);

        m_diffDriver.driveArcade(move.straightSpeed, (move.rotationSpeed / 1)); // speed /1.75
    }
    
    // This command continues until interrupted.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }

}
