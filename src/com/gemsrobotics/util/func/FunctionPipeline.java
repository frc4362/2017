package com.gemsrobotics.util.func;


import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


public class FunctionPipeline<T> {
	protected Function<T, T> m_func;

	public FunctionPipeline(final List<Function<T, T>> functions) {
		m_func = functions.stream()
				          .reduce((f, g) -> f.andThen(g))
				          .get();
	}

	@SafeVarargs
	public FunctionPipeline(final Function<T, T>... functions) {
		this(Arrays.asList(functions));
	}

	public FunctionPipeline() {
		this(Function.identity());
	}

	public FunctionPipeline<T> map(final Function<T, T> func) {
		m_func = m_func.andThen(func);
		return this;
	}

	public T apply(T input) {
		return m_func.apply(input);
	}
}
