package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SimpleHalsteadAnalyzerTest {

    @Test
    public void testThatReportContainsHalsteadEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(Arrays.asList(TestInput.getInputMetric1()));
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.123", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHalsteadDifferentEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(Arrays.asList(TestInput.getInputMetric2()));
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.234", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHighestHalsteadEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(TestInput.getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.234", analysis.toString());
    }
    
    
    @Test
    public void testThatReportContainsTheMethodName() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(TestInput.getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        assertContains("MethodName2", analysis.toString());
    }
    
    @Test
    public void testThatReportContainsFileName() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(TestInput.getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        assertContains("/dir/file2", analysis.toString());
    }

    private void assertContains(String needle, String haystack) {
        assertTrue(haystack.contains(needle), "\"" + haystack +"\" is exptected to contain \"" + needle + "\"");
    }
}
