package frc.robot.consoles.tabs;
import frc.robot.sensors.LimeLight;
import edu.wpi.first.wpilibj.shuffleboard.*;
import frc.robot.consoles.ShuffleLogger;
import frc.robot.brains.ShooterBrain;
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
            m_xOffSetWidget = m_tab.add("X OffSet",
                   LimeLight.getXOffset());
            // Y Offset
            m_yOffSetWidget = m_tab.add("Y OffSet",
            LimeLight.getYOffset());
           
            // Distance
            m_distanceWidget = m_tab.add("Distance to target", 
            LimeLight.getDistance());
        }
    
        // Create all other Widgets
        public void initialize() {
        }
    
        // Configure all Widgets
        public void configure() {
            m_xOffSetWidget.withPosition(1,0);
            m_yOffSetWidget.withPosition(2,0);
            m_distanceWidget.withPosition(3,0);
        }
    
        // This will be called in the robotPeriodic
        public void update() {
    
        }
    
}
