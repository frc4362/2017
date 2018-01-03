package com.gemsrobotics.team4362.summer2017.robot.subsystems;


import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;


public class PassiveGearIntake extends Subsystem {
	private final Servo m_left, m_right;

	public enum Position {
		CLOSED(17, 73),
		OPENED(60, 30);

		private final double m_leftValue, m_rightValue;

		Position(final double l, final double r) {
			m_leftValue = l / 180.0d;
			m_rightValue = r / 180.0d;
		}

		public double getLeft() {
			return m_leftValue;
		}
		
		public double getRight() {
			return m_rightValue;
		}
	}

	public PassiveGearIntake(final int leftPort, final int rightPort) {
		m_left = new Servo(leftPort);
		m_right = new Servo(rightPort);
	}

	public void set(final Position pos) {
		m_left.set(pos.getLeft());
		m_right.set(pos.getRight());
	}

    public void initDefaultCommand() {}
}
