package de.rkable.coverity.analyze;

import java.util.List;

import de.rkable.coverity.MethodMetrics;

public class SimpleHalsteadAnalyzer implements MetricsAnalyzer {
    
    private final static String HALSTEAD_EFFORT = "Halstead effort";
    private final static String HALSTEAD_ERROR = "Halstead error";
    
    private MethodMetrics worstMethod;

    /* (non-Javadoc)
     * @see de.rkable.coverity.analyze.MetricsAnalyzer#startAnalysis(java.util.List)
     */
    @Override
    public void startAnalysis(List<MethodMetrics> inputMetrics) {
        for (MethodMetrics metric : inputMetrics) {
            if (worstMethod == null) {
                worstMethod = metric;
                continue;
            }
            if (worstMethod.getMetrics().halsteadEffort < metric.getMetrics().halsteadEffort) {
                worstMethod = metric;
            }
        }
    }

    /* (non-Javadoc)
     * @see de.rkable.coverity.analyze.MetricsAnalyzer#getAnalysis()
     */
    @Override
    public Report getAnalysis() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("File path: ");
        sb.append(worstMethod.getFileName());
        sb.append(System.lineSeparator());
        
        sb.append("Method name: ");
        sb.append(worstMethod.getMethodName());
        sb.append(System.lineSeparator());
        
        sb.append(HALSTEAD_EFFORT);
        sb.append(": ");
        sb.append(worstMethod.getMetrics().halsteadEffort);
        sb.append(System.lineSeparator());
        
        sb.append(HALSTEAD_ERROR);
        sb.append(": ");
        sb.append(worstMethod.getMetrics().halsteadError);
        sb.append(System.lineSeparator());
        
        StringReport report = new StringReport(sb.toString());
        
        
        return report;
    }

}
