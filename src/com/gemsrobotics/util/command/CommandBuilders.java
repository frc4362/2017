package com.gemsrobotics.util.command;


import java.util.Arrays;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;


public class CommandBuilders {
	private CommandBuilders() {}

	public static InstantCommand commandOf(final Runnable action) {
		return new InstantCommand() {
			protected void initialize() {
				action.run();
			}
		};
	}

	public static CommandGroup autonOf(final Command... actions) {
		return new CommandGroup() {
			{
				Arrays.asList(actions).forEach(this::addSequential);
			}
		};
	}
}
