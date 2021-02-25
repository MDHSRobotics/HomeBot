
package frc.robot.subsystems;

import frc.robot.consoles.Logger;

import frc.robot.subsystems.utils.TalonUtils;
import static frc.robot.subsystems.constants.TalonConstants.*;
import static frc.robot.subsystems.Devices.diffDriveTalon;
import static frc.robot.subsystems.Devices.talonSrxDiffWheelFrontLeft;
import static frc.robot.subsystems.Devices.talonSrxDiffWheelFrontRight;
import static frc.robot.subsystems.Devices.talonSrxDiffWheelRearLeft;
import static frc.robot.subsystems.Devices.talonSrxDiffWheelRearRight;
import static frc.robot.RobotManager.isReal;

// Differential driver subsystem.
public class DiffDriverTalonSRX extends DiffDriver {

    // Motor constants
    private final double SECONDS_FROM_NEUTRAL_TO_FULL = 1.0;

    public DiffDriverTalonSRX() {
        super(diffDriveTalon);
        Logger.setup("Constructing Subsystem: DiffDriverTalonSRX...");

        if (isReal) {
            // Configure the TalonSRX devices used for DiffDriver
            TalonUtils.configureBaseTalonMasterFollower(talonSrxDiffWheelFrontLeft, talonSrxDiffWheelRearLeft, true, true);
            TalonUtils.configureBaseTalonMasterFollower(talonSrxDiffWheelFrontRight, talonSrxDiffWheelRearRight, true, true);
            talonSrxDiffWheelFrontLeft.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonSrxDiffWheelFrontRight.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonSrxDiffWheelRearLeft.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonSrxDiffWheelRearRight.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
        }
    }
}

