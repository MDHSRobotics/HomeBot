package frc.robot.subsystems;
import java.util.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.consoles.Logger;
import frc.robot.devices.DevTalonSRX;
import frc.robot.subsystems.utils.EncoderUtils;
import frc.robot.subsystems.utils.PIDValues;
import frc.robot.subsystems.utils.TalonUtils;

import static frc.robot.subsystems.constants.EncoderConstants.*;
import static frc.robot.subsystems.Devices.talonSrxTurret;
import static frc.robot.RobotManager.isReal;

public class Turret extends SubsystemBase{
    private boolean sensorPhase = false; 
    private boolean motorInvert = true; 
    public Turret(){
        Logger.setup("Constructing Subsystem: Turret...");
        
        PIDValues pidturret = new PIDValues(0.1, 0.7 , 0, 30.0);
        TalonUtils.configureTalonWithEncoder(talonSrxTurret, sensorPhase, motorInvert, pidturret);
        
    }

    public void rotateTurretVelocity(int speed){
          talonSrxTurret.set(ControlMode.Velocity, speed);
    }
    
    public void rotateTurretPosition(int speedByRev){
          talonSrxTurret.set(ControlMode.Position,speedByRev);
    }


}