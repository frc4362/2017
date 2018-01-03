package com.gemsrobotics.team4362.summer2017.robot.subsystems;


import edu.wpi.first.wpilibj.Servo;


public class Guard {
	private final Servo m_servo;
	
	public Guard(final int port) {
		m_servo = new Servo(port);
	}
	
	public void open() {
		m_servo.set(1);
	}
	
	public void close() {
		m_servo.set(0);
	}
}
