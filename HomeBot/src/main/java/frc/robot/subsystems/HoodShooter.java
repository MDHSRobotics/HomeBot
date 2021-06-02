package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.brains.LimelightBrain;
import frc.robot.brains.ShooterBrain;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.utils.*;
import static frc.robot.subsystems.Devices.talonSrxShooterHood;
import static frc.robot.RobotManager.isReal;
public class HoodShooter extends SubsystemBase {
    // Encoder constants
    private static final boolean SENSOR_PHASE_HOOD = true;
    private static final boolean MOTOR_INVERT_HOOD = false;

    private static final boolean SENSOR_PHASE_FLYWHEEL = true;
    private static final boolean MOTOR_INVERT_FLYWHEEL = false;

    // Position constants
    private static final double GEAR_RATIO = 4.0; // Gear ratio bewteen Input Shaft : Output Shaft of a gearbox
    
     public HoodShooter(){
       if(isReal){
        PIDValues pidHood = new PIDValues(0.0, 0.0, 0.0, 0.0);
        TalonUtils.configureTalonWithEncoder(talonSrxShooterHood, SENSOR_PHASE_HOOD, MOTOR_INVERT_HOOD, pidHood);
       
        PIDValues pidShooter = new PIDValues(0.00835, 0.0, 0.0, 0.0); // Calibrated for 20,000: TpHMS 0.00835
        TalonUtils.configureTalonWithEncoder(talonSrxShooterTopWheel, SENSOR_PHASE_FLYWHEEL, MOTOR_INVERT_FLYWHEEL, pidShooter);
       
      } 
    }
      

}     
