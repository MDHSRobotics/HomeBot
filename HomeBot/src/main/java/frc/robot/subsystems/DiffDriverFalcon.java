
package frc.robot.subsystems;

import frc.robot.consoles.Logger;
import frc.robot.devices.DevTalonSRX;

import static frc.robot.subsystems.Devices.diffDriveTalon;
import static frc.robot.subsystems.Devices.talonSrxDiffWheelFrontLeft;
import static frc.robot.subsystems.Devices.talonSrxDiffWheelFrontRight;
import static frc.robot.subsystems.Devices.talonSrxDiffWheelRearLeft;
import static frc.robot.subsystems.Devices.talonSrxDiffWheelRearRight;
import static frc.robot.RobotManager.isReal;

// Differential driver subsystem.
public class DiffDriverFalcon extends DiffDriver {

    // Motor constants
    private final double SECONDS_FROM_NEUTRAL_TO_FULL = 0;
    private final int TIMEOUT_MS = 10;

    public DiffDriverFalcon() {
        super(diffDriveTalon);
        Logger.setup("Constructing Subsystem: DiffDriverTalon...");

        if (isReal) {
            // Configure the subsystem devices
            configureTalon(talonSrxDiffWheelFrontLeft);
            configureTalon(talonSrxDiffWheelFrontRight);
            configureTalon(talonSrxDiffWheelRearLeft);
            configureTalon(talonSrxDiffWheelRearRight);
        }
        talonSrxDiffWheelRearLeft.follow(talonSrxDiffWheelFrontLeft);
        talonSrxDiffWheelRearRight.follow(talonSrxDiffWheelFrontRight);
    }

    // Configure the given talon
    private void configureTalon(DevTalonSRX talon) {
        if (!talon.isConnected) return;

        // TODO: Investigate why these motor controllers have to be inverted.
        talon.setInverted(true);
        talon.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
    }

}

