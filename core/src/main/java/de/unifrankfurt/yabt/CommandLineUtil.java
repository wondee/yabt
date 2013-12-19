package de.unifrankfurt.yabt;

import de.unifrankfurt.yabt.export.FileExporter;
import de.unifrankfurt.yabt.export.PrintStreamExporter;


public class CommandLineUtil {
	
	public static void runSuite(BenchmarkSuite suite, String[] args) {
		boolean runned = false;
		
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			
			if (arg.startsWith("-")) {
				String exporter = arg.substring(1);
				
				switch (exporter) {
				case "f":
					FileExporter fe;
					if (i + 1 < args.length  && !args[i + 1].startsWith("-")) {
						fe = new FileExporter(args[i++]);
					} else {
						fe = new FileExporter();
					}
					
					suite.addExporter(fe);
					
					break;
				case "p":
					suite.addExporter(new PrintStreamExporter());
					
					break;
				default:
					throw new IllegalArgumentException("no exporter found for param '" + exporter + "'");
					
				}
								
			} else {
				suite.run(arg);
				runned = true;
				
			}
		}
		
		if (!runned) {
			suite.run();
		}
	}
}
