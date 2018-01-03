package com.gemsrobotics.util;

import java.util.List;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public abstract class ExtendedIterativeRobot extends IterativeRobot {
	public abstract List<Command> getTeleopCommands();

	public abstract void teleopStart();
	
	public void teleopInit() {
		Scheduler.getInstance().removeAll();
		getTeleopCommands().forEach(Scheduler.getInstance()::add);
		teleopStart();
	}
}
