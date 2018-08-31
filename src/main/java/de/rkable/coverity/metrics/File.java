package de.rkable.coverity.metrics;

import java.util.ArrayList;
import java.util.Collection;

public class File {

    private final String fileName;
    private final Collection<Method> methodMetrics = new ArrayList<>();

    public File(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFile() {
        return fileName;
    }
    
    public void addMethodMetric(Method metrics) {
        methodMetrics.add(metrics);
    }

    public Collection<Method> getMethods() {
        return methodMetrics;
    }

}
