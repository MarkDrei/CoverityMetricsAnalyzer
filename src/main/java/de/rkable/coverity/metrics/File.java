package de.rkable.coverity.metrics;

import java.util.ArrayList;
import java.util.Collection;

public class File {

    private final String path;
    private final Collection<Method> methodMetrics = new ArrayList<>();

    public File(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getFileName() {
        return path.substring(path.lastIndexOf('/') + 1);
    }
    
    
    public void addMethod(Method metrics) {
        methodMetrics.add(metrics);
    }

    public Collection<Method> getMethods() {
        return methodMetrics;
    }

    public Metrics getCombinedMetric() {
        Metrics result = Metrics.createEmptyMetrics();
        for (Method method : getMethods()) {
            result = result.combine(method.getMetrics());
        }
        return result;
    }

}
