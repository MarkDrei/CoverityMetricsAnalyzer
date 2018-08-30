package de.rkable.coverity;

/**
 * An method which has code metrics associated to it.
 * 
 */
public class MethodMetrics {
    
    public static class MethodMetricsBuilder {

        private String methodName;
        private String fileName;
        private Metrics metrics;

        public MethodMetrics build() {
            MethodMetrics methodMetrics = new MethodMetrics(
                    fileName,
                    methodName,
                    metrics);
            return methodMetrics;
        }

        public MethodMetricsBuilder methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public void fileName(String fileName) {
            this.fileName = fileName;
        }

        public MethodMetricsBuilder metrics(Metrics metrics) {
            this.metrics = metrics;
            return this;
        }
    }


    private final Metrics metrics;
    private final String methodName;
    private final String fileName;
    
    public MethodMetrics(String fileName, String methodName, Metrics metrics) {
        this.fileName = fileName;
        this.methodName = methodName;
        this.metrics = metrics;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getFileName() {
        return fileName;
    }

    public Metrics getMetrics() {
        return metrics;
    }

}
