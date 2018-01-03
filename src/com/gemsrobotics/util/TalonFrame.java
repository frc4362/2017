package com.gemsrobotics.util;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.IntStream;


public class TalonFrame extends HashMap<Integer, Double> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static TalonFrame of(final Object... args) {
		if (args.length % 2 != 0) {
			throw new RuntimeException("Invalid TalonFrame constructor!");
		}

		final TalonFrame ret = new TalonFrame();

		IntStream.range(0, args.length / 2).forEach(i -> {
			final int index = i * 2;
			ret.put((Integer) args[index], (Double) args[index + 1]);
		});

		return ret;
	}

	public Set<Integer> getScope() {
		return keySet();
	}
}
