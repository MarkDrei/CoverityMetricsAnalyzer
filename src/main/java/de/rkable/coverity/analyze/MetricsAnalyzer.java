package de.rkable.coverity.analyze;

import java.util.List;

import de.rkable.coverity.metrics.MethodMetrics;

/**
 * Can analyze metrics and produce a human readable report 
 *
 */
public interface MetricsAnalyzer {

    void startAnalysis(List<MethodMetrics> inputMetrics);

    Report getAnalysis();

}