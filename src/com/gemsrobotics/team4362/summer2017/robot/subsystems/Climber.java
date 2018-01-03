package com.gemsrobotics.team4362.summer2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public final class Climber {
	private final int m_port;
	private final CANTalon m_talon, m_talonSlave;

	public Climber(final int idMain, final int idSlave) {
		m_port = idMain;
		
		m_talon = new CANTalon(idMain);
		
		m_talonSlave = new CANTalon(idSlave);
		m_talonSlave.changeControlMode(TalonControlMode.Follower);
	}

	public void set(final double speed) {
		// needs to be double-negated so that the input and output are in the correct direction
		m_talon.set(-speed);
		m_talonSlave.set(m_port);
	}
}
