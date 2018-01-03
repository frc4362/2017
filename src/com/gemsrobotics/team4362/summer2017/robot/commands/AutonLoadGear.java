package com.gemsrobotics.team4362.summer2017.robot.commands;


import static com.gemsrobotics.util.command.CommandBuilders.commandOf;

import com.gemsrobotics.team4362.summer2017.robot.subsystems.PassiveGearIntake.Position;
import com.gemsrobotics.team4362.summer2017.robot.Hardware;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class AutonLoadGear extends CommandGroup {
    public AutonLoadGear() {    	
    	addSequential(commandOf(() -> Hardware.getInstance().getPassiveIntake().set(Position.OPENED)));

	    addSequential(commandOf(Hardware.getInstance().getGuard()::open));

		addSequential(new Wait(300));

		addSequential(commandOf(Hardware.getInstance().getGuard()::close));

		addSequential(new Wait(250));

    	addSequential(commandOf(() -> Hardware.getInstance().getPassiveIntake().set(Position.CLOSED)));

		addSequential(new Wait(250));
    }
}
