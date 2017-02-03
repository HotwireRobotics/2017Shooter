package org.usfirst.frc.team2991.robot;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RPMControl {
	public Timer encoderTimer;
	public float currentSpeed = 0.2f;
	public float encoderZero;
	public CANTalon talon;
	public float targetRPM;
	public String name;
	
	// dead encoder checking
	public Timer killSwitch;
	public boolean debug;
	public int isZero = 0;
	
	public float P = 0.21f;
	public float I = 0.0f;
	public float D = 5.0f;
	public float F = 0.35f;
	
	public int codesPerRev = 250;
	
	public float speed = 0.2f;

	public RPMControl(String name, int talonNumber, boolean debug,
				float P, float I, float D, float F, float targetRPM) {
		
		talon = new CANTalon(talonNumber);
		
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
		this.targetRPM = targetRPM;
		
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.configEncoderCodesPerRev(360);
		talon.reverseSensor(true);
		talon.setProfile(0);
		talon.setP(P);
		talon.setI(I);
		talon.setD(D);
		talon.setF(F);
		
		talon.configNominalOutputVoltage(0.0f, 0.0f);
		talon.configPeakOutputVoltage(12.0f, 0f);
		
		//talon.setAllowableClosedLoopErr(200);
		talon.setPosition(0);
		
		talon.changeControlMode(CANTalon.TalonControlMode.Speed);
		
		//talon.setSetpoint(1000);
		
		

		this.debug = debug;
		killSwitch = new Timer();
		killSwitch.start();

		encoderTimer = new Timer();
		encoderTimer.start();
		encoderZero = talon.getEncPosition();

		this.name = name;
		

		
		SmartDashboard.putNumber(name, targetRPM);
		SmartDashboard.putNumber(name + "P", P);
		SmartDashboard.putNumber(name + "I", I);
		SmartDashboard.putNumber(name + "D", D);
		SmartDashboard.putNumber(name + "F", F);
		SmartDashboard.putNumber("Codes per rev", codesPerRev);
		
		SmartDashboard.putNumber("SPEED", speed);
		
		//talon.set(0.2f);
	}

	public void UpdateRPMControl() {
		targetRPM = (float) SmartDashboard.getNumber(name);
		P = (float) SmartDashboard.getNumber(name + "P");
		I = (float) SmartDashboard.getNumber(name + "I");
		D = (float) SmartDashboard.getNumber(name + "D");
		F = (float) SmartDashboard.getNumber(name + "F");
		
		talon.setP(P);
		talon.setI(I);
		talon.setD(D);
		talon.setF(F);
		
		talon.set(targetRPM);
		
		// gather and display the current rpm
		float encoderPerRotation = -4080;		
		if (encoderTimer.get() >= 0.1) {
			float encoderDelta = talon.getEncPosition() - encoderZero;
			System.out.println(talon.getAnalogInVelocity());
			float RPM = (encoderDelta * 60 * 10) / encoderPerRotation;
			
			if (RPM != 0) 
			{		
				SmartDashboard.putNumber(name + "RPM", RPM);
			}
			
			encoderTimer.reset();
			encoderTimer.start();
			encoderZero = talon.getEncPosition();
		}
	}
}
