package de.rkable.coverity.input;

import java.io.IOException;

import de.rkable.coverity.MethodMetrics;
import de.rkable.coverity.MethodMetrics.MethodMetricsBuilder;

public class FnmetricParser {

    private String file;
    private String names;
    private String metric;

    public FnmetricParser(String... input) {
        if (input.length != 7) {
            throw new IllegalArgumentException("Input of 7 strings expected");
        }
        /* openMetric =    input[0]; */
        file =             input[1];
        names =            input[2];
        metric =           input[3];
        /* coverage =      input[4]; */   
        /* impact =        input[5]; */  
        /* closeMetric =   input[6]; */
    }

    public MethodMetrics parse() throws IOException {

        MetricsLineParser fileParser = new MetricsLineParser(file);
        MetricsLineParser namesParser = new MetricsLineParser(names);
        MetricsLineParser metricParser = new MetricsLineParser(metric);
        
        if (!fileParser.isLineWithFile()) {
            throw new IOException("Content of the file field is wrong: " + file);
        }
        
        
        if (!namesParser.isLineWithMethodName()) {
            throw new IOException("Content of the names field is wrong: " + names);
        }
        
        
        if (!metricParser.isLineWithMetrics()) {
            throw new IOException("Content of the metrics field is wrong: " + metric);
        }
        
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        builder.metrics(metricParser.getMetrics());
        builder.methodName(namesParser.getMethodName());
        builder.fileName(fileParser.getFileName().toString());
        return builder.build();
    }

}
