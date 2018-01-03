package com.gemsrobotics.team4362.summer2017.robot.commands;


import java.util.List;
import java.util.stream.IntStream;

import com.ctre.CANTalon;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.DriveBase;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class LogDriveTalons extends Command {
	private static final String[] talonNames = {
		"front left",
		"back left",
		"front right",
		"back right"
	};

	protected final List<CANTalon> m_talons;

	public LogDriveTalons(final DriveBase driveTrain) {
		m_talons = driveTrain.getTalons();
	}

    protected void execute() {
    	IntStream.range(0, 4).forEach(i -> {
    		SmartDashboard.putNumber(
    				talonNames[i] + " encoder speed 2",
    				m_talons.get(i).getSpeed()
			);

    		SmartDashboard.putNumber(
    				talonNames[i] + " encoder position 2",
    				m_talons.get(i).getPosition()
    		);
    	});
    }

    protected boolean isFinished() {
        return false;
    }
}
