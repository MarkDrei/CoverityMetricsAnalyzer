package de.rkable.coverity.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import de.rkable.coverity.MethodMetrics;

/**
 * Reader for a file which contains metrics.
 * Expects the format that is delivered by Coverity
 *
 */
public class MetricsFileReader {
	
	private List<MethodMetrics> methodMetrics;
	private String path;
	
	/**
	 * @param file path to the file which is parsed
	 */
	public MetricsFileReader(String file) {
		this.path = file;
	}

	public List<MethodMetrics> getMetricEntities() {
		return methodMetrics;
	}

	public void parseFile() throws IOException {
		File file = new File(path);
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);
		
		try (FileInputStream iStream = new FileInputStream(file)) {
			methodMetrics = new ArrayList<>();
			readMetrics(new BufferedReader(new InputStreamReader(iStream)));
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Failed to parse the file " + file.getAbsolutePath(), e);
		}
	}

	private void readMetrics(BufferedReader bufferedReader) throws IOException {
	    /* String openMetric =*/  bufferedReader.readLine();
	    String file =             bufferedReader.readLine();
	    String names =            bufferedReader.readLine();
	    String metric =           bufferedReader.readLine();
	    /* String coverage =*/    bufferedReader.readLine();
	    /* String impact =*/      bufferedReader.readLine();
	    /* String closeMetric =*/ bufferedReader.readLine();
	    
	    MetricsLineParser fileParser = new MetricsLineParser(file);
	    MetricsLineParser namesParser = new MetricsLineParser(names);
	    MetricsLineParser metricParser = new MetricsLineParser(metric);
	    
	    if (!fileParser.isLineWithFile() || !namesParser.isLineWithMethodName() || !metricParser.isLineWithMetrics()) {
	        throw new IOException("Expected content not found in the lines");
	    }
	    MethodMetrics methodMetric = new MethodMetrics();
        methodMetric.setMetrics(metricParser.getMetrics());
        methodMetrics.add(methodMetric);
	}

}
