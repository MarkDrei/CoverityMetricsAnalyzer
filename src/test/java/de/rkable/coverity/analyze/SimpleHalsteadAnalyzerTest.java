package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.MethodMetrics;
import de.rkable.coverity.metrics.Metrics;
import de.rkable.coverity.metrics.MethodMetrics.MethodMetricsBuilder;

public class SimpleHalsteadAnalyzerTest {

    @Test
    public void testThatReportContainsHalsteadEffort() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric1());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.123", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHalsteadDifferentEffort() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric2());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.234", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHighestHalsteadEffort() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric1());
        inputMetrics.add(getInputMetric2());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.234", analysis.toString());
    }
    
    
    @Test
    public void testThatReportContainsTheMethodName() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric1());
        inputMetrics.add(getInputMetric2());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("MethodName2", analysis.toString());
    }
    
    @Test
    public void testThatReportContainsFileName() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric1());
        inputMetrics.add(getInputMetric2());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("/path/to/file2", analysis.toString());
    }

    private void assertContains(String needle, String haystack) {
        assertTrue(haystack.contains(needle), "\"" + haystack +"\" is exptected to contain \"" + needle + "\"");
    }

    private MethodMetrics getInputMetric1() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.123, 0.02));
        metrics.methodName("MethodName1");
        metrics.fileName("/path/to/file1");
        return metrics.build();
    }
    
    private MethodMetrics getInputMetric2() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.234, 0.04));
        metrics.methodName("MethodName2");
        metrics.fileName("/path/to/file2");
        return metrics.build();
    }
}
