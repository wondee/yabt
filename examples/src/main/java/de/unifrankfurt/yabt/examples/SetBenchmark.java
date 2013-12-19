package de.unifrankfurt.yabt.examples;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import de.unifrankfurt.yabt.BenchmarkRunner;
import de.unifrankfurt.yabt.annotation.Benchmark;
import de.unifrankfurt.yabt.annotation.BenchmarkConfig;


@BenchmarkConfig(name = "set", warmUpIterations=50000)
public class SetBenchmark {
	
	
	private static final String ELEM_PREFIX = "str";
	private static final int ELEMS_SIZE = 100;
	
	final private static String[] ELEMS = new String[ELEMS_SIZE];
	
	static HashSet<String> hashSet;
	static TreeSet<String> treeSet;
	
	
	static {
		for (int i = 0; i < ELEMS_SIZE; i++) {
			ELEMS[i] = ELEM_PREFIX + i;
		}
		hashSet = new HashSet<>();
		initializeSet(hashSet);
		
		treeSet = new TreeSet<>();
		initializeSet(treeSet);
	}
	
	private static void initializeSet(Set<String> set) {
		for (int i = 0; i < 1000; i++) {
			set.add(ELEM_PREFIX + i);
		}
	}
	
	@Benchmark
	public TreeSet<String> containsTree() {
		for (int i = 0; i < 100; i++) {
			treeSet.contains(ELEMS[i]);
		}
		
		return treeSet;
	}
	
	@Benchmark
	public HashSet<String> containsHash() {
		for (int i = 0; i < 100; i++) {
			hashSet.contains(ELEMS[i]);
		}
		
		return hashSet;
	}
	
	public static void main(String[] args) {
		BenchmarkRunner.start(SetBenchmark.class);
	}
}
