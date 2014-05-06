package de.unifrankfurt.yabt;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.unifrankfurt.yabt.export.ExportStrategy;


public class BenchmarkRunner {

	public static final List<String> NEEDED_JVM_ARGS;

	public static final String JVM_ARG_COMPILATION_OUTPUT = "-XX:+PrintCompilation";

	static {
		NEEDED_JVM_ARGS = Arrays.asList(JVM_ARG_COMPILATION_OUTPUT);
	}

	private Collection<? extends ExportStrategy> exporters;

	public BenchmarkRunner(Collection<? extends ExportStrategy> exporters) {
		this.exporters = exporters;
	}
	
	public BenchmarkRunner() {
		this(BenchmarkSuite.DEFAULT_EXPORTER);
	}

	/**
	 * Starts the benchmark on this class with the default params
	 * @param clazz the benchmark class
	 */
	public static void start(Class<?> clazz) {
		start(new RunnerConfig(clazz));
	}

	public static void start(RunnerConfig config) {
		new BenchmarkRunner().runBenchmark(config);
	}

	public void runBenchmark(RunnerConfig config) {
		runBenchmark(config.benchmarkClass(),
				config.name(),
				config.benchmarkRuns(),
				config.warmUpIterations(),
				config.initRuns(),
				config.measureIterations());
		
	}
	
	private <T> void runBenchmark(Class<T> benchmarkClass, String name, int benchmarkRuns, int warmUpsIterations, int initRuns, int measureIterations) {
		checkJVMSettings();

		Experiment<T> benchmark = new Experiment<>(benchmarkClass);

		Result result = benchmark.runBenchmarkClass(name, benchmarkRuns, warmUpsIterations, measureIterations, initRuns);


		for (ExportStrategy exporter : exporters) {
			exporter.export(result);
		}

	}

	private static void checkJVMSettings() {
		List<String> arguments = ManagementFactory.getRuntimeMXBean().getInputArguments();

		for (String arg : NEEDED_JVM_ARGS) {
			if (!arguments.contains(arg)) {
				System.err.println("the running JVM has to be launched with the following argument: " + arg);
			}
		}
	}
}
