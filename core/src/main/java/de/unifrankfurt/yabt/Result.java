package de.unifrankfurt.yabt;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class Result {

	private Map<String, double[][]> resultMap;
	private int runs;
	private String name;

	Result(String name, int runs) {
		resultMap = Maps.newHashMap();
		this.runs = runs;
		this.name = name;
	}

	private void initializeMap(String name) {
		resultMap.put(name, new double[runs][]);

	}

	void set(int i, String name, double[] results) {
		if (!resultMap.containsKey(name)) {
			initializeMap(name);
		}
		resultMap.get(name)[i] = results;

	}

	public Collection<String> names() {
		return resultMap.keySet();
	}

	public int runs() {
		return runs;
	}

	public int measurments(String name, int run) {
		return resultMap.get(name)[run].length;
	}

	public double get(String name, int run, int m) {
		return resultMap.get(name)[run][m];
	}

	public double[] results(String name, int run) {
		return resultMap.get(name)[run];
	}

	public String getName() {
		return name;
	}


}
