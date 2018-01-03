package com.gemsrobotics.team4362.summer2017.robot.subsystems;


import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;


public class FeedTrigger extends Subsystem {
	private final Servo m_left, m_right;

	public enum Position {
		DOWN(0.23, 0.63),
		RIGHT_DOWN(0.41, 0.63),
		LEFT_DOWN(0.23, 0.45),
		UP(0.41, 0.45);

		private final double m_leftPosition, m_rightPosition;

		Position(final double l, final double r) {
			m_leftPosition  = l;
			m_rightPosition = r;
		}

		public double getLeft() {
			return m_leftPosition;
		}
		
		public double getRight() {
			return m_rightPosition;
		}
	}

	public FeedTrigger(final int... ports) {
		m_left = new Servo(ports[0]);
		m_right = new Servo(ports[1]);
	}

	public void set(final Position pos) {
		m_left.set(pos.getLeft());
		m_right.set(pos.getRight());
	}

    public void initDefaultCommand() {}
}
