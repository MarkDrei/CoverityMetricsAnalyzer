package de.rkable.coverity;

/**
 * An method which has code metrics associated to it.
 * 
 */
public class MethodMetrics {

    private Metrics metrics;
    private String methodName;
    private String fileName;
    
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

}
