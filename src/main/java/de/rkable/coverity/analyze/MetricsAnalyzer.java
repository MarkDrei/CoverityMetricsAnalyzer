package de.rkable.coverity.analyze;

import java.util.Collection;

import de.rkable.coverity.metrics.DirectoryMetrics;

/**
 * Can analyze metrics and produce a human readable report 
 *
 */
public interface MetricsAnalyzer {

    void startAnalysis(Collection<DirectoryMetrics> inputMetrics);

    Report getAnalysis();

}