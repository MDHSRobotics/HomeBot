
package frc.robot.commands.swervedriver;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.oi.controllers.XboxPositionAccessible;
import frc.robot.oi.movements.SwerveMovement;
import frc.robot.subsystems.SwerveDriver;

// This command uses the xbox inputs as parameters for the swerve drive() method
public class SwerveDrive extends CommandBase {

    public XboxPositionAccessible controller;
    private SwerveDriver m_swerveDriver;

    public SwerveDrive(SwerveDriver swerveDriver, XboxPositionAccessible controller) {
        Logger.setup("Constructing Command: SwerveDrive...");

        // Add given subsystem requirements
        this.controller = controller;
        m_swerveDriver = swerveDriver;
        addRequirements(m_swerveDriver);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        SwerveMovement move = SwerveMovement.getMovement(controller);
        m_swerveDriver.drive(move.forwardBackwardSpeed, move.sideToSideSpeed, move.rotationSpeed);
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
