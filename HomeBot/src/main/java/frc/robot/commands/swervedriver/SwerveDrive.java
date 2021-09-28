
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
        m_swerveDriver.drive(move.forwardBackwardSpeed , move.sideToSideSpeed , move.rotationSpeed );
        // m_swerveDriver.testMotors();
    }

    public final double L = .47;
    public final double W = .47;

    public void drive (double x1, double y1, double x2) {
        double r = Math.sqrt ((L * L) + (W * W));
        y1 *= -1;

        double a = x1 - x2 * (L / r);
        double b = x1 + x2 * (L / r);
        double c = y1 - x2 * (W / r);
        double d = y1 + x2 * (W / r);

        double backRightSpeed = Math.sqrt ((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));

        double backRightAngle = Math.atan2 (a, d) / Math.PI;
        double backLeftAngle = Math.atan2 (a, c) / Math.PI;
        double frontRightAngle = Math.atan2 (b, d) / Math.PI;
        double frontLeftAngle = Math.atan2 (b, c) / Math.PI;
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
