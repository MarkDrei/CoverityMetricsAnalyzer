package de.rkable.coverity.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.rkable.coverity.metrics.Method;

/**
 * Reader for a file which contains metrics.
 * Expects the format that is delivered by Coverity
 *
 */
public class MetricsFileReader {
	
	private List<Method> methodMetrics;
	private String path;
	
	/**
	 * @param file path to the file which is parsed
	 */
	public MetricsFileReader(String file) {
		this.path = file;
	}

	public List<Method> getMetricEntities() {
		return methodMetrics;
	}

	public void parseFile() throws IOException {
		File file = new File(path);
		
		try (FileInputStream iStream = new FileInputStream(file)) {
			methodMetrics = new ArrayList<>();
			readMetrics(new BufferedReader(new InputStreamReader(iStream)));
		} catch (IOException e) {
			throw new IOException("Failed to parse the file " + file.getAbsolutePath(), e);
		}
	}

	private void readMetrics(BufferedReader bufferedReader) throws IOException {
	    for (;;) {
	        String[] input = new String[] {
	                bufferedReader.readLine(), 
	                bufferedReader.readLine(), 
	                bufferedReader.readLine(), 
	                bufferedReader.readLine(), 
	                bufferedReader.readLine(), 
	                bufferedReader.readLine(), 
	                bufferedReader.readLine()};
 
	        if (input[6] == null) {
	            return;
	        }
	        
	        methodMetrics.add(new FnmetricParser(input).parse());
	    }
	}

}
