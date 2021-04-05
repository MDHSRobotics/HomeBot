
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;
import frc.robot.consoles.ShuffleLogger;
import frc.robot.brains.LimelightBrain;

public class LimeLightTab {

    // Tab & Layouts
    private ShuffleboardTab m_tab;

    // Widgets
    private SimpleWidget m_xOffSetWidget;
    private SimpleWidget m_yOffSetWidget;
    private SimpleWidget m_distanceWidget;

    // Constructor
    public LimeLightTab() {
        ShuffleLogger.logTrivial("Constructing LimeLightTab...");

        m_tab = Shuffleboard.getTab("limelight");
    }

    // Create LimeLight Widgets
    public void preInitialize() {

        // X OffSet
        m_xOffSetWidget = m_tab.add("X OffSet", LimelightBrain.txEntryDefault);
        LimelightBrain.txEntry = m_xOffSetWidget.getEntry();

        // Y Offset
        m_yOffSetWidget = m_tab.add("Y OffSet", LimelightBrain.tyEntryDefault);
        LimelightBrain.tyEntry = m_yOffSetWidget.getEntry();

        // Distance
        m_distanceWidget = m_tab.add("Distance to target", LimelightBrain.distanceEntryDefault);
        LimelightBrain.distanceEntry = m_distanceWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        m_xOffSetWidget.withPosition(1, 0);
        m_yOffSetWidget.withPosition(2, 0);
        m_distanceWidget.withPosition(3, 0);
    }

    // This will be called in the robotPeriodic
    public void update() {
        LimelightBrain.updateTxEntry();
        LimelightBrain.updateTyEntry();
        LimelightBrain.updateDistanceEntry();
    }

}
