package com.gemsrobotics.team4362.summer2017.robot.commands;


import edu.wpi.first.wpilibj.command.Command;


public class Wait extends Command {
	private final long m_length;
	private long m_startTime, m_endTime;

    public Wait(final long length) {
    	m_length = length;
    }

    protected void initialize() {
    	m_startTime = System.currentTimeMillis();
    	m_endTime = m_startTime + m_length;
    }

    protected boolean isFinished() {
        return m_endTime < System.currentTimeMillis();
    }
}
