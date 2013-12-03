package de.unifrankfurt.faststring.yabt;

import static com.google.common.base.Preconditions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.unifrankfurt.faststring.yabt.export.ExportStrategy;
import de.unifrankfurt.faststring.yabt.export.PrintStreamExporter;

public class RunnerConfig {

	public static final int DEFAULT_BENCHMARK_RUNS = 5;
	public static final int DEFAULT_INIT_RUNS = 5;
	public static final int DEFAULT_WARM_UP_ITERATIONS = 500000;
	public static final int DEFAULT_MEASURE_ITERATIONS = 20000;
	public static final String DEFAULT_OUTPUT_PATH = "out";
	public static final List<? extends ExportStrategy> DEFAULT_EXPORTER = Arrays.asList(new PrintStreamExporter());

	private Class<?> benchmarkClass;
	private int benchmarkRuns = DEFAULT_BENCHMARK_RUNS;
	private int warmUpIterations = DEFAULT_WARM_UP_ITERATIONS;
	private int measureIterations = DEFAULT_MEASURE_ITERATIONS;
	private int initRuns = DEFAULT_INIT_RUNS;
	private Collection<? extends ExportStrategy> exporter = DEFAULT_EXPORTER;

	Class<?> benchmarkClass() {
		checkNotNull(benchmarkClass, "benchmarkClass must not be null");
		return this.benchmarkClass;
	}

	int benchmarkRuns() {
		return this.benchmarkRuns ;
	}

	int warmUpIterations() {
		return this.warmUpIterations ;
	}

	int measureIterations() {
		return this.measureIterations;
	}

	int initRuns() {
		return this.initRuns;
	}

	Collection<? extends ExportStrategy> exporter() {
		return this.exporter;
	}

}
