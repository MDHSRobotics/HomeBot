package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.brains.LimelightBrain;
import frc.robot.brains.ShooterBrain;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.utils.*;
import java.util.*;
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
       
      } 
    }

    //set flap angle
    public void setAngleFromDistance(double distance) {
     double angle = EncoderUtils.translateAngleToTicks(translateDistanceToDegreesViaTable(distance), GEAR_RATIO);
     talonSrxShooterHood.set(ControlMode.Position, angle);
    }



    public double translateDistanceToDegreesViaTable(double targetDistance) {

      // Initialize the lookup table; Key=Velocity in FPS; Value=Motor speed in Ticks/100ms
      SortedMap<Double, Double> luTable = new TreeMap<Double, Double>();

      // The data below is based on shooting experiments conducted on March 11, 2021:
      // (Distance, Ticks per 100ms)
      luTable.put(2.5, 500.); // untested
      luTable.put(7.5, 22000.);
      luTable.put(10., 20000.);
      luTable.put(12.5, 19500.);
      luTable.put(15., 19800.);
      luTable.put(17.5, 20400.);
      luTable.put(20., 21250.);
      luTable.put(22.5, 21300.);
      luTable.put(25., 21500.);

      boolean firstPass = true;
      double f1 = -99.;
      double t1 = -99.;
      double targetTPHMS = -99.;

      // Iterate over the lookup table, which is sorted based on the key (fps)
      for (Map.Entry<Double, Double> entry : luTable.entrySet()) {
          double f2 = entry.getKey();
          double t2 = entry.getValue();
          Logger.debug(f2 + " (ft) => " + t2 + " (Ticks/100ms");
          // Skip over the first value because we can't compute a slope until we have read at least 2 values
          if (firstPass){
            firstPass = false;
          }
          else {
              double slope = (t2-t1) / (f2-f1);
              targetTPHMS = t1 + (targetDistance-f1) * slope;

              if (targetDistance <= f2)
                  break;
          }
          f1 = f2;
          t1 = t2;
      }

      return targetTPHMS;
  }

      

}   