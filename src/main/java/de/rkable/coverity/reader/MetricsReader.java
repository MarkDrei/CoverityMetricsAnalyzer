package de.rkable.coverity.reader;

import java.util.ArrayList;
import java.util.List;

import de.rkable.coverity.Metrics;

public class MetricsReader {
	
	private List<Metrics> metrics;
	
	/**
	 * @param file path to the file which is parsed
	 */
	public MetricsReader(String file) {
	}

	public List<Metrics> getMetrics() {
		return metrics;
	}

	public void parseFile() {
		metrics = new ArrayList<>();
	}

}
