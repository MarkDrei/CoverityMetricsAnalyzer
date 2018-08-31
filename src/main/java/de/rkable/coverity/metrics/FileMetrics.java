package de.rkable.coverity.metrics;

import java.util.ArrayList;
import java.util.Collection;

public class FileMetrics {

    private final String fileName;
    private final Collection<MethodMetrics> methodMetrics = new ArrayList<>();

    public FileMetrics(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFile() {
        return fileName;
    }
    
    public void addMethodMetric(MethodMetrics metrics) {
        methodMetrics.add(metrics);
    }

    public Collection<MethodMetrics> getMethodMetrics() {
        return methodMetrics;
    }

}
