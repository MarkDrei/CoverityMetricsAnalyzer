package de.rkable.coverity.analyze;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SimpleHalsteadAnalyzerTest {

    @Test
    public void testThatReportContainsHalsteadEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(Arrays.asList(TestInput.getInputMetricFile1Method1()));
        Report analysis = analyzer.getAnalysis();
        StringAssertions.assertContains("Halstead effort: 0.123", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHalsteadDifferentEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(Arrays.asList(TestInput.getInputMetricFile2Method2()));
        Report analysis = analyzer.getAnalysis();
        StringAssertions.assertContains("Halstead effort: 0.234", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHighestHalsteadEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(TestInput.getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        StringAssertions.assertContains("Halstead effort: 0.234", analysis.toString());
    }
    
    
    @Test
    public void testThatReportContainsTheMethodName() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(TestInput.getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        StringAssertions.assertContains("MethodName2", analysis.toString());
    }
    
    @Test
    public void testThatReportContainsFileName() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(TestInput.getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        StringAssertions.assertContains("/dir/file2", analysis.toString());
    }
}
