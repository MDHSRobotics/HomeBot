package frc.robot.subsystems;
import java.util.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.brains.LimelightBrain;
import frc.robot.consoles.Logger;
import frc.robot.devices.DevTalonSRX;
import frc.robot.subsystems.utils.EncoderUtils;
import frc.robot.subsystems.utils.PIDValues;
import frc.robot.subsystems.utils.TalonUtils;
import static frc.robot.subsystems.constants.EncoderConstants.*;
import static frc.robot.subsystems.Devices.talonSrxTurret;
import static frc.robot.RobotManager.isReal;

public class Turret extends SubsystemBase{
    private final double constant = 0.19; // Constant to get velocity from tx
    //private boolean sensorPhase = false; -> used if choose control mode
    private boolean motorInvert = true; 
    public Turret(){
        
        Logger.setup("Constructing Subsystem: Turret...");
        if(isReal){
        //  PID configuration -> if needed to use for velocity control 
        //PIDValues pidturret = new PIDValues(0.1, 0.7 , 0, 30.0);
        //TalonUtils.configureTalonWithEncoder(talonSrxTurret, sensorPhase, motorInvert, pidturret);
        TalonUtils.configureBaseTalon(talonSrxTurret, motorInvert);
        }
    }
    // Using encoder + limelight 
    public void rotateTurretVelocity(){
        double speed = LimelightBrain.getTxEntry()* constant;  
        talonSrxTurret.set(ControlMode.Velocity, speed);
    }
    // Using limelight only 
    public void rotateTurretPercent(){
        double speed = LimelightBrain.getTxEntry()* constant;    
        talonSrxTurret.set(ControlMode.PercentOutput, speed);
    }


}