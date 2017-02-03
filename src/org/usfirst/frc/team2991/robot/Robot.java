
package org.usfirst.frc.team2991.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CANTalon;


public class Robot extends IterativeRobot {
	
	public RPMControl feederRPMControl;
	public RPMControl shooterRPMControl;
    
    public CANTalon intake;

    public void robotInit() {
    	feederRPMControl= new RPMControl("RPM Feeder", 5, true, 0.195f, 0.0f, 9.0f, 0.35f, 3000);
    	shooterRPMControl = new RPMControl("RPM Shooter", 2, true, 0.2f, 0.0f, 9.0f, 0.35f, 4000); 
    	//intake = new CANTalon(2);
    }


    public void autonomousInit() {
    }

  
    public void autonomousPeriodic() {
  
    }

    
    public void teleopPeriodic() {
    	//intake.set(0.7f);
    	
    	feederRPMControl.UpdateRPMControl();
    	shooterRPMControl.UpdateRPMControl();
    	
    	
    }
   
    public void testPeriodic() {
    
    }
    
}
