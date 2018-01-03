package com.gemsrobotics.util;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;


public class TalonRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	protected final List<TalonFrame> m_record;
	protected final Set<Integer> m_scope;

	protected static boolean isValidForm(final Set<Integer> scope, final List<TalonFrame> frames) {
		return frames.stream()
				     .skip(1)
				     .allMatch(frame -> scope.equals(frame.getScope()));
	}

	public TalonRecord(final List<TalonFrame> frames) {
		m_scope = frames.get(0).getScope();

		if (isValidForm(m_scope, frames)) {
			m_record = frames;
		} else {
			throw new RuntimeException("Invalid form of collection of frames passed to TalonRecord!");
		}
	}

	public Set<Integer> getScope() {
		return m_scope;
	}

	public final Command toCommand() {
		return new Command() {
			private int m_tick;
			
			private final Map<Integer, CANTalon> m_registry = new HashMap<Integer, CANTalon>() {
				private static final long serialVersionUID = 1L;
				{
					m_scope.forEach(id -> {
						put(id, new CANTalon(id));
					});
				}
			};

			public void initialize() {
				m_tick = 0;
			}

			public void execute() {
				m_record.get(m_tick).forEach((id, outputValue) -> {
					m_registry.get(id).set(outputValue);
				});

				m_tick++;
			}

			public boolean isFinished() {
				return m_tick >= m_record.size();
			}
		};
	}
}
