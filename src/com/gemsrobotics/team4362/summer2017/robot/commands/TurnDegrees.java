package com.gemsrobotics.team4362.summer2017.robot.commands;

import com.gemsrobotics.team4362.summer2017.robot.Hardware;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnDegrees extends Command {
	private final double m_distance;
	private double m_startingHeading, m_destination;

	private final double 
		DEGREES_THRESHOLD = 3.5,
		SPEED = 0.25;

    public TurnDegrees(final double degrees) {
    	m_distance = degrees;
    }

    protected double getHeading() {
    	return Hardware.getInstance().getMXP().getAngle();
    }

    protected void initialize() {
    	m_startingHeading = getHeading();
    	m_destination = m_startingHeading + m_distance;
    	
    	SmartDashboard.putNumber("turndegrees destination", m_destination);
    }

    protected double getError() {
    	return m_destination - getHeading();
    }

    protected void execute() {
    	final int direction = (int) Math.signum(getError());

    	SmartDashboard.putNumber("turnerror", getError());

    	Hardware.getInstance().getDrive().drive(
    			SPEED * direction, 
    			SPEED * -direction
    	);
    }

    protected boolean isFinished() {
        return Math.abs(getError()) < DEGREES_THRESHOLD;
    }

    protected void end() {
    	Hardware.getInstance().getDrive().drive(0, 0);
    }
}
