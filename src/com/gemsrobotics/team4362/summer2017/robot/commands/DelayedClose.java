package com.gemsrobotics.team4362.summer2017.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.CommandGroup;

import static com.gemsrobotics.util.command.CommandBuilders.commandOf;

import com.gemsrobotics.team4362.summer2017.robot.subsystems.Guard;

public class DelayedClose extends CommandGroup {
	public DelayedClose(final XboxController controller, final Guard guard, final int wait) {
		addSequential(new Wait(wait));
		addSequential(commandOf(() -> {
			if (!controller.getBumper(Hand.kRight)) {
				guard.close();
			}
		}));
	}
}
