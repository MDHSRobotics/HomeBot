package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.subsystems.Devices.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class SwerveDriver extends SubsystemBase {

    
    // Control whether you want the thumbstick axes to be flipped in the opposite direction.
    public static final boolean isYFlipped = false;
    public static final boolean isXFlipped = false;
    public static final boolean isOmegaFlipped = false;

    // Drive using a vertical, horizontal, and rotational velocity
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

        int tickBackRightAngle = (int) (backRightAngle * 4096);
        int tickBackLeftAngle = (int) (backLeftAngle * 4096);
        int tickFrontRightAngle = (int) (frontRightAngle * 4096);
        int tickFrontLeftAngle = (int) (frontLeftAngle * 4096);

        talonFxSwerveDriveWheelRearRight.set(backRightSpeed);
        talonFxSwerveDriveWheelRearLeft.set(backLeftSpeed);
        talonFxSwerveDriveWheelFrontRight.set(frontRightSpeed);
        talonFxSwerveDriveWheelFrontLeft.set(frontLeftSpeed);

        talonFxSwerveTurnWheelFrontLeft.set(ControlMode.Position, tickFrontLeftAngle);
        talonFxSwerveTurnWheelFrontRight.set(ControlMode.Position, tickFrontRightAngle);
        talonFxSwerveTurnWheelRearLeft.set(ControlMode.Position, tickBackLeftAngle);
        talonFxSwerveTurnWheelRearRight.set(ControlMode.Position, tickBackRightAngle);
    }

    // Stop all the swerve modules
    public void stop() {
        Devices.frontLeftSwerveModule.stopModule();
        Devices.frontRightSwerveModule.stopModule();
        Devices.rearLeftSwerveModule.stopModule();
        Devices.rearRightSwerveModule.stopModule();
    }

    public void testMotors() {
        Devices.frontLeftSwerveModule.testModule();
        Devices.frontRightSwerveModule.testModule();
        Devices.rearLeftSwerveModule.testModule();
        Devices.rearRightSwerveModule.testModule();
    }
}