package de.rkable.coverity.analyze;

import java.util.List;

import de.rkable.coverity.MethodMetrics;

public class SimpleHalsteadAnalyzer {
    
    private final static String HALSTEAD_EFFORT = "Highest Halstead effort";
    
    private double worstEffort = 0;

    public SimpleHalsteadAnalyzer(List<MethodMetrics> inputMetrics) {
        for (MethodMetrics metric : inputMetrics) {
            worstEffort = Math.max(worstEffort, metric.getMetrics().halsteadEffort);
        }
    }

    public String getAnalysis() {
        return HALSTEAD_EFFORT + ": " + worstEffort;
    }

}
