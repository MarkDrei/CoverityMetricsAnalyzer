package de.rkable.coverity.analyze;

import java.util.List;

import de.rkable.coverity.MethodMetrics;

public class SimpleHalsteadAnalyzer implements MetricsAnalyzer {
    
    private final static String HALSTEAD_EFFORT = "Highest Halstead effort";
    
    private double worstEffort = 0;

    /* (non-Javadoc)
     * @see de.rkable.coverity.analyze.MetricsAnalyzer#startAnalysis(java.util.List)
     */
    @Override
    public void startAnalysis(List<MethodMetrics> inputMetrics) {
        for (MethodMetrics metric : inputMetrics) {
            worstEffort = Math.max(worstEffort, metric.getMetrics().halsteadEffort);
        }
    }

    /* (non-Javadoc)
     * @see de.rkable.coverity.analyze.MetricsAnalyzer#getAnalysis()
     */
    @Override
    public Report getAnalysis() {
        return new StringReport(HALSTEAD_EFFORT + ": " + worstEffort);
    }

}
