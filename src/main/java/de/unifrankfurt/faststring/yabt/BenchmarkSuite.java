package de.unifrankfurt.faststring.yabt;

import static com.google.common.base.Preconditions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;

import de.unifrankfurt.faststring.yabt.export.ExportStrategy;
import de.unifrankfurt.faststring.yabt.export.PrintStreamExporter;

public class BenchmarkSuite {
	
	public static final List<? extends ExportStrategy> DEFAULT_EXPORTER = Arrays.asList(new PrintStreamExporter());
	
	private ImmutableMap<String, RunnerConfig> benchmarkMap;
	
	private List<ExportStrategy> exporters = Lists.newLinkedList();

	private BenchmarkRunner runner = null;
	
	public BenchmarkSuite(Class<?> ... benchmarks) {
		this(Arrays.asList(benchmarks));
	}

	public BenchmarkSuite(Collection<Class<?>> benchmarks) {
		Builder<String, RunnerConfig> builder = ImmutableMap.builder();
		
		for (Class<?> benchmark : benchmarks) {
			RunnerConfig config = new RunnerConfig(benchmark);
			builder.put(config.name(), config);
		}
		
		benchmarkMap = builder.build();
	}
	
	public void addExporter(ExportStrategy exporter) {
		checkNotNull(exporter, "given exporter must not be null");
		
		if (runner == null) {
			exporters.add(exporter);
		} else {
			throw new IllegalArgumentException("adding exporters after creating a runner is not allowed");
		}
	}
	
	public void run(String name) {
		RunnerConfig config = benchmarkMap.get(name);
		checkNotNull(config, "no config found for name '" + name + "'");
		
		getRunner().runBenchmark(config);
	}
	
	public void run() {
		for (String name : benchmarkMap.keySet()) {
			run(name);
		}
	}
	
	private BenchmarkRunner getRunner() {
		if (runner == null) {
			createRunner();
		}	
		
		return runner;
	}

	private void createRunner() {
		runner = (exporters.size() > 0) ? new BenchmarkRunner(exporters) : new BenchmarkRunner();
	}
}
