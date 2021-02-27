
package frc.robot.subsystems;

import frc.robot.consoles.Logger;

import frc.robot.subsystems.utils.TalonUtils;
import static frc.robot.subsystems.constants.TalonConstants.*;
import static frc.robot.subsystems.Devices.diffDriveTalonFX;
import static frc.robot.subsystems.Devices.talonFxDiffWheelFrontLeft;
import static frc.robot.subsystems.Devices.talonFxDiffWheelFrontRight;
import static frc.robot.subsystems.Devices.talonFxDiffWheelRearLeft;
import static frc.robot.subsystems.Devices.talonFxDiffWheelRearRight;
import static frc.robot.RobotManager.isReal;

// Differential driver subsystem.
public class DiffDriverTalonFX extends DiffDriver {

    // Motor constants
    private final double SECONDS_FROM_NEUTRAL_TO_FULL = 1.0;

    public DiffDriverTalonFX() {
        super(diffDriveTalonFX);
        Logger.setup("Constructing Subsystem: DiffDriverTalonFX...");

        if (isReal) {
            // Configure the TalonFX devices used for DiffDriver
            TalonUtils.configureBaseTalonMasterFollower(talonFxDiffWheelFrontLeft, talonFxDiffWheelRearLeft, true, true);
            TalonUtils.configureBaseTalonMasterFollower(talonFxDiffWheelFrontRight, talonFxDiffWheelRearRight, true, true);
            talonFxDiffWheelFrontLeft.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonFxDiffWheelFrontRight.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonFxDiffWheelRearLeft.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonFxDiffWheelRearRight.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
        }
    }
}

