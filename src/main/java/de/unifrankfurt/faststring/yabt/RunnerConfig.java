package de.unifrankfurt.faststring.yabt;

import static com.google.common.base.Preconditions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.unifrankfurt.faststring.yabt.annotation.BenchmarkConfig;
import de.unifrankfurt.faststring.yabt.export.ExportStrategy;
import de.unifrankfurt.faststring.yabt.export.PrintStreamExporter;

public class RunnerConfig {

	public static final int DEFAULT_BENCHMARK_RUNS = 5;
	public static final int DEFAULT_INIT_RUNS = 5;
	public static final int DEFAULT_WARM_UP_ITERATIONS = 500000;
	public static final int DEFAULT_MEASURE_ITERATIONS = 200000;
	public static final String DEFAULT_OUTPUT_PATH = "out";
	public static final List<? extends ExportStrategy> DEFAULT_EXPORTER = Arrays.asList(new PrintStreamExporter());

	private Class<?> benchmarkClass;
	
	private String name;
	
	private int benchmarkRuns = DEFAULT_BENCHMARK_RUNS;
	private int warmUpIterations = DEFAULT_WARM_UP_ITERATIONS;
	private int measureIterations = DEFAULT_MEASURE_ITERATIONS;
	private int initRuns = DEFAULT_INIT_RUNS;
	private Collection<? extends ExportStrategy> exporter = DEFAULT_EXPORTER;

	
	
	RunnerConfig(Class<?> clazz) {
		benchmarkClass = clazz;
		
		BenchmarkConfig config = clazz.getAnnotation(BenchmarkConfig.class);
		
		if (config != null) {
			copyConfig(config);
		}
	}

	private void copyConfig(BenchmarkConfig config) {
		name = config.name();
		initRuns = config.initRuns();
		benchmarkRuns = config.benchmarkRuns();
		warmUpIterations = config.warmUpIterations();
		measureIterations = config.measureInterations();
	}

	Class<?> benchmarkClass() {
		checkNotNull(benchmarkClass, "benchmarkClass must not be null");
		return benchmarkClass;
	}

	int benchmarkRuns() {
		return benchmarkRuns ;
	}

	int warmUpIterations() {
		return warmUpIterations ;
	}

	int measureIterations() {
		return measureIterations;
	}

	int initRuns() {
		return initRuns;
	}

	Collection<? extends ExportStrategy> exporter() {
		return exporter;
	}
	
	String name() {
		return name;
	}

}
