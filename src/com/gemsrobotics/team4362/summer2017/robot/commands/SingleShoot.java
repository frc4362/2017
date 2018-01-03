package com.gemsrobotics.team4362.summer2017.robot.commands;


import static com.gemsrobotics.util.command.CommandBuilders.commandOf;

import com.gemsrobotics.team4362.summer2017.robot.Hardware;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.FeedTrigger;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.TwoWheelShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class SingleShoot extends CommandGroup {
    public SingleShoot() {
    	addSequential(commandOf(() -> Hardware.getInstance().getShooter().set(TwoWheelShooter.ShooterPreset.SHORT)));

    	addSequential(new Wait(1200));

    	addSequential(commandOf(() -> Hardware.getInstance().getTrigger().set(FeedTrigger.Position.RIGHT_DOWN)));

    	addSequential(new Wait(2000));
    	addSequential(new DriveDistance(Hardware.getInstance().getDrive(), 2, 0.3));
    	addSequential(new Wait(2500));

    	addSequential(commandOf(() -> Hardware.getInstance().getTrigger().set(FeedTrigger.Position.UP)));
    }
}
