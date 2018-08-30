package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.MethodMetrics;
import de.rkable.coverity.Metrics;

public class SimpleHalsteadAnalyzerTest {

    @Test
    public void testThatReportContainsHalsteadEffort() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric1());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("Highest Halstead effort: 0.123", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHalsteadDifferentEffort() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric2());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("Highest Halstead effort: 0.234", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHighestHalsteadEffort() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric1());
        inputMetrics.add(getInputMetric2());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("Highest Halstead effort: 0.234", analysis.toString());
    }
    
    
    @Test
    public void testThatReportContainsTheMethodName() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric1());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("MethodName1", analysis.toString());
    }

    private void assertContains(String needle, String haystack) {
        assertTrue(haystack.contains(needle), "\"" + haystack +"\" is exptected to contain \"" + needle + "\"");
    }

    private MethodMetrics getInputMetric1() {
        MethodMetrics metrics = new MethodMetrics();
        metrics.setMetrics(new Metrics(0.123));
        metrics.setMethodName("MethodName1");
        return metrics;
    }
    
    private MethodMetrics getInputMetric2() {
        MethodMetrics metrics = new MethodMetrics();
        metrics.setMetrics(new Metrics(0.234));
        metrics.setMethodName("MethodName2");
        return metrics;
    }
}
