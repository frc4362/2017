package com.gemsrobotics.team4362.summer2017.robot.commands;

import com.gemsrobotics.team4362.summer2017.robot.subsystems.DriveBase;
import com.gemsrobotics.util.command.ToggleableCommand;
import com.gemsrobotics.util.joy.Gemstick;
import com.gemsrobotics.util.joy.Gemstick.StickLens;

public class Drive extends ToggleableCommand {
	protected final DriveBase m_driveBase;
	protected final Gemstick m_leftStick, m_rightStick;

    public Drive(final DriveBase db, final Gemstick leftStick, final Gemstick rightStick) {
    	super(StartMode.ENABLED);
   
    	m_driveBase = db;
    	m_leftStick = leftStick;
    	m_rightStick = rightStick;
    }

    protected void whenEnabled() {
    	final double
    		l = -m_leftStick.get(StickLens.Y),
    		r = -m_rightStick.get(StickLens.Y);

    	m_driveBase.drive(l, r);
    }

    protected boolean isFinished() {
        return false;
    }
}
