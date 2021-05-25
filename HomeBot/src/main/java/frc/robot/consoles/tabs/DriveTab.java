
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.brains.DiffDriverBrain;
import frc.robot.consoles.ShuffleLogger;
import frc.robot.subsystems.Devices;
import frc.robot.BotSubsystems;

// The Shuffleboard Drive tab.
public class DriveTab {

    // Tab & Layouts
    private ShuffleboardTab m_tab;

    // Widgets
    private ComplexWidget m_diffDriveWidget;
    private SimpleWidget m_alignZSensitivityWidget;
    private SimpleWidget m_alignZSpeedMinimumWidget;
    private SimpleWidget m_alignZToleranceWidget;

    // Create Brain Widgets
    public DriveTab() {
        ShuffleLogger.logCritical("Constructing DriveTab...");

        m_tab = Shuffleboard.getTab("Drive");
    }

    // Create Brain Widgets
    public void preInitialize() {
        m_alignZSensitivityWidget = m_tab.add("Z Sensitivity", DiffDriverBrain.alignZSensitivityDefault);
        DiffDriverBrain.alignZSensitivityEntry = m_alignZSensitivityWidget.getEntry();

        m_alignZSpeedMinimumWidget = m_tab.add("Z Speed Minimum", DiffDriverBrain.alignZSpeedMinimumDefault);
        DiffDriverBrain.alignZSpeedMinimumEntry = m_alignZSpeedMinimumWidget.getEntry();

        m_alignZToleranceWidget = m_tab.add("Z Tolerance", DiffDriverBrain.alignZToleranceDefault);
        DiffDriverBrain.alignZToleranceEntry = m_alignZToleranceWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
        m_diffDriveWidget = m_tab.add("Differential Drive", BotSubsystems.swerveDriver);
    }

    // Configure all Widgets
    public void configure() {
        m_diffDriveWidget.withPosition(3, 1);
        m_diffDriveWidget.withSize(4, 3);

        m_alignZSensitivityWidget.withPosition(2, 0);
        m_alignZSpeedMinimumWidget.withPosition(0, 1);
        m_alignZToleranceWidget.withPosition(1, 1);
    }

    // This will be called in the robotPeriodic
    public void update() {
    }

}
