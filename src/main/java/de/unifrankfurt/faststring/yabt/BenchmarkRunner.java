package de.unifrankfurt.faststring.yabt;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.unifrankfurt.faststring.yabt.export.ExportStrategy;
import de.unifrankfurt.faststring.yabt.export.FileExporter;
import de.unifrankfurt.faststring.yabt.export.PrintStreamExporter;


public class BenchmarkRunner {

	public static final List<String> NEEDED_JVM_ARGS;

	public static final String JVM_ARG_COMPILATION_OUTPUT = "-XX:+PrintCompilation";

	static {
		NEEDED_JVM_ARGS = Arrays.asList(JVM_ARG_COMPILATION_OUTPUT);
	}

	public static void start() {
		start(new RunnerConfig());
	}

	public static void start(RunnerConfig config) {
		checkJVMSettings();
		createBenchmark(config.benchmarkClass(),
				config.benchmarkRuns(),
				config.warmUpIterations(),
				config.initRuns(),
				config.measureIterations(),
				config.exporter());
	}

	private static <T> void createBenchmark(Class<T> benchmarkClass, int benchmarkRuns, int warmUpsIterations, int initRuns, int measureIterations, Collection<? extends ExportStrategy> exporters) {

		Experiment<T> benchmark = new Experiment<>(benchmarkClass);

		Result result = benchmark.runBenchmarkClass(benchmarkRuns, warmUpsIterations, measureIterations, initRuns);


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
