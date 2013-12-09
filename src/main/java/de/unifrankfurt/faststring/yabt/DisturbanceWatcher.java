package de.unifrankfurt.faststring.yabt;

import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;

class DisturbanceWatcher {

	private WeakReference<Dummy> weakRef;
	
	private long beforeCompilationTime;
	
	private CompilationMXBean compilationMXBean;
	
	DisturbanceWatcher() {
		compilationMXBean = ManagementFactory.getCompilationMXBean();
		
		initWeakRef();
		initCompilationTime();
	}

	private void initCompilationTime() {
		beforeCompilationTime = getCurrentCompilationTime();
	}
	
	private synchronized void initWeakRef() {
		weakRef = new WeakReference<>(new Dummy());
	}
	
	synchronized boolean wasDisturbing() {
		return (wasGcActive() || wasCompilation());
	}
	
	boolean wasCompilation() {
		return (getCurrentCompilationTime() - beforeCompilationTime > 0);
	}

	private long getCurrentCompilationTime() {
		return compilationMXBean.getTotalCompilationTime();
	}

	synchronized boolean wasGcActive() {
		return (weakRef.get() == null);
	}

	synchronized void reset() {
		initWeakRef();
		initCompilationTime();
	}

	class Dummy { }
}
