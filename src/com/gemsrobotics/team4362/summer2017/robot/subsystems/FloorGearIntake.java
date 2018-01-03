package com.gemsrobotics.team4362.summer2017.robot.subsystems;


import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;


public class FloorGearIntake extends Subsystem {
	private final double 
		kP = 0.03125,
		kI = 0.0,
		kD = 0.0,
		kF = 0.003;

	protected final CANTalon m_talon;

	public FloorGearIntake(final int port) {
		m_talon = new CANTalon(port);
		m_talon.configNominalOutputVoltage(+6.0, -6.0);
		m_talon.setCloseLoopRampRate(0.001);
		m_talon.setAllowableClosedLoopErr(90);
		
		m_talon.setInverted(false);
		
		m_talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		m_talon.changeControlMode(TalonControlMode.Position);
		
		m_talon.setP(kP);
		m_talon.setI(kI);
		m_talon.setD(kD);
		m_talon.setF(kF);
		
		m_talon.enableControl();
	}

	public CANTalon getTalon() {
		return m_talon;
	}

	public void initialize() {
		//m_talon.setSetpoint(0.47);
	}

	public void deploy() {
		m_talon.changeControlMode(TalonControlMode.PercentVbus);
		m_talon.set(0);
	}
	
	public void retract() {
		m_talon.changeControlMode(TalonControlMode.Position);
		m_talon.set(0.495);
	}

    public void initDefaultCommand() {}
}

