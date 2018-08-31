package de.rkable.coverity.analyze;

import java.util.Collection;

import de.rkable.coverity.metrics.Directory;

/**
 * Can analyze metrics and produce a human readable report 
 *
 */
public interface MetricsAnalyzer {

    void startAnalysis(Collection<Directory> inputMetrics);

    Report getAnalysis();

}