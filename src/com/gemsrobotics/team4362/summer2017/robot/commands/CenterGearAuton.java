package com.gemsrobotics.team4362.summer2017.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import static com.gemsrobotics.util.command.CommandBuilders.commandOf;

import com.gemsrobotics.team4362.summer2017.robot.Hardware;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.PassiveGearIntake;

public class CenterGearAuton extends CommandGroup {
    public CenterGearAuton() {
    	addSequential(new AutonLoadGear());

    	addSequential(new DriveDistance(Hardware.getInstance().getDrive(), -66, -0.35));

    	addSequential(new Wait(700));

    	addSequential(commandOf(() -> Hardware.getInstance().getPassiveIntake().set(PassiveGearIntake.Position.OPENED)));

    	addSequential(new Wait(250));

    	addSequential(new DriveDistance(Hardware.getInstance().getDrive(), -7, -0.20));
    }
}
