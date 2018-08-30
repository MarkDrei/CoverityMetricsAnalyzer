package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.MethodMetrics;
import de.rkable.coverity.Metrics;

public class SimpleHalsteadAnalyzerTest {

    @Test
    public void testThatSimpleMetricReturnHalsteadEffort() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric1());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("Highest Halstead effort: 0.123", analysis.toString());
    }
    
    @Test
    public void testThatSimpleMetricReturnHalsteadDifferentEffort() {
        List<MethodMetrics> inputMetrics = new ArrayList<>();
        inputMetrics.add(getInputMetric2());
        
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(inputMetrics);
        Report analysis = analyzer.getAnalysis();
        assertContains("Highest Halstead effort: 0.234", analysis.toString());
    }

    private void assertContains(String needle, String haystack) {
        assertTrue(haystack.contains(needle), "\"" + haystack +"\" is exptected to contain \"" + needle + "\"");
    }

    private MethodMetrics getInputMetric1() {
        MethodMetrics metrics = new MethodMetrics();
        metrics.setMetrics(new Metrics(0.123));
        return metrics;
    }
    
    private MethodMetrics getInputMetric2() {
        MethodMetrics metrics = new MethodMetrics();
        metrics.setMetrics(new Metrics(0.234));
        return metrics;
    }
}
