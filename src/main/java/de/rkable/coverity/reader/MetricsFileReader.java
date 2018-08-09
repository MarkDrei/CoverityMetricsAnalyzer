package de.rkable.coverity.reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import de.rkable.coverity.Metrics;

public class MetricsFileReader {
	
	private List<Metrics> metrics;
	private String path;
	
	/**
	 * @param file path to the file which is parsed
	 */
	public MetricsFileReader(String file) {
		this.path = file;
	}

	public List<Metrics> getMetrics() {
		return metrics;
	}

	public void parseFile() throws Exception {
		File file = new File(path);
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);
		
		try (FileInputStream iStream = new FileInputStream(file)) {
			metrics = new ArrayList<>();
			readMetrics(new BufferedInputStream(iStream));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to parse the file " + file.getAbsolutePath(), e);
		}
	}

	private void readMetrics(BufferedInputStream bufferedInputStream) {
		metrics.add(new Metrics(3));
	}

}
