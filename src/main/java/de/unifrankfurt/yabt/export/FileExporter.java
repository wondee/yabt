package de.unifrankfurt.yabt.export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import de.unifrankfurt.yabt.Result;

public class FileExporter implements ExportStrategy {

	public static final String DEFAULT_OUTPUT_PATH = "out";
	
	private String path;

	private DecimalFormat df = new DecimalFormat("#");

	public FileExporter() {
		this(DEFAULT_OUTPUT_PATH);
	}
	
	public FileExporter(String path) {
		this.path = path;
	}

	@Override
	public void export(Result result) {

		try {

			for (String name : result.names()) {
				BufferedWriter writer = Files.newBufferedWriter(
						createFileName(result.getName(), name), Charset.defaultCharset());
				for (int run = 0; run < result.runs(); run++) {
					for (int m = 0; m < result.measurments(name, run); m++) {
						writer.write(df.format(result.get(name, run, m)));
						writer.write(";");
					}

					writer.write(String.format("%n"));
				}

			}
		} catch (IOException e) {
			throw new IllegalStateException("could not write to file", e);
		}

	}

	private Path createFileName(String prefix, String name) {
		try {

			Path dir = Paths.get(path);
			if (!Files.exists(dir)) {
				Files.createDirectory(dir);
			}

			Path file = Paths.get(path, prefix + "_" + name + ".csv");
			if (!Files.exists(file)) {
				Files.createFile(file);
			}
			return file;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}
