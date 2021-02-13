
package frc.robot.subsystems;

import frc.robot.consoles.Logger;
import frc.robot.devices.DevTalonFX;

import static frc.robot.subsystems.Devices.diffDriveTalon;
import static frc.robot.subsystems.Devices.talonFxDiffWheelFrontLeft;
import static frc.robot.subsystems.Devices.talonFxDiffWheelFrontRight;
import static frc.robot.subsystems.Devices.talonFxDiffWheelRearLeft;
import static frc.robot.subsystems.Devices.talonFxDiffWheelRearRight;
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
            configureTalon(talonFxDiffWheelFrontLeft);
            configureTalon(talonFxDiffWheelFrontRight);
            configureTalon(talonFxDiffWheelRearLeft);
            configureTalon(talonFxDiffWheelRearRight);
        }
        talonFxDiffWheelRearLeft.follow(talonFxDiffWheelFrontLeft);
        talonFxDiffWheelRearRight.follow(talonFxDiffWheelFrontRight);
    }

    // Configure the given talon
    private void configureTalon(DevTalonFX talon) {
        if (!talon.isConnected) return;

        // TODO: Investigate why these motor controllers have to be inverted.
        talon.setInverted(true);
        talon.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
    }

}

