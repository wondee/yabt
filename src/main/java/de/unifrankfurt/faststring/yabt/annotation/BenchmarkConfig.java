package de.unifrankfurt.faststring.yabt.annotation;

import static de.unifrankfurt.faststring.yabt.RunnerConfig.DEFAULT_BENCHMARK_RUNS;
import static de.unifrankfurt.faststring.yabt.RunnerConfig.DEFAULT_INIT_RUNS;
import static de.unifrankfurt.faststring.yabt.RunnerConfig.DEFAULT_MEASURE_ITERATIONS;
import static de.unifrankfurt.faststring.yabt.RunnerConfig.DEFAULT_WARM_UP_ITERATIONS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BenchmarkConfig {
	
	String name();
	
	int benchmarkRuns() default DEFAULT_BENCHMARK_RUNS;
	int initRuns() default DEFAULT_INIT_RUNS;
	int warmUpIterations() default DEFAULT_WARM_UP_ITERATIONS;
	int measureInterations() default DEFAULT_MEASURE_ITERATIONS;
	
}
