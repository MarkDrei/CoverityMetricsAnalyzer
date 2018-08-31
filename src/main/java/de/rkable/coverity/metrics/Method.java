package de.rkable.coverity.metrics;

/**
 * An method which has code metrics associated to it.
 * 
 */
public class Method {
    
    public static class MethodMetricsBuilder {

        private String methodName;
        private String fileName;
        private Metrics metrics;

        public Method build() {
            Method methodMetrics = new Method(
                    fileName,
                    methodName,
                    metrics);
            return methodMetrics;
        }

        public MethodMetricsBuilder methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public MethodMetricsBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public MethodMetricsBuilder metrics(Metrics metrics) {
            this.metrics = metrics;
            return this;
        }
    }


    private final Metrics metrics;
    private final String methodName;
    private final String fileName;
    
    public Method(String fileName, String methodName, Metrics metrics) {
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
