package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.Directory;
import de.rkable.coverity.metrics.File;
import de.rkable.coverity.metrics.Method;
import de.rkable.coverity.metrics.Method.MethodMetricsBuilder;
import de.rkable.coverity.metrics.Metrics;

public class SimpleHalsteadAnalyzerTest {

    @Test
    public void testThatReportContainsHalsteadEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(Arrays.asList(getInputMetric1()));
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.123", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHalsteadDifferentEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(Arrays.asList(getInputMetric2()));
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.234", analysis.toString());
    }
    
    @Test
    public void testThatSReportContainsHighestHalsteadEffort() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        assertContains("Halstead effort: 0.234", analysis.toString());
    }
    
    
    @Test
    public void testThatReportContainsTheMethodName() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        assertContains("MethodName2", analysis.toString());
    }
    
    @Test
    public void testThatReportContainsFileName() {
        MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();
        analyzer.startAnalysis(getTwoInputs());
        Report analysis = analyzer.getAnalysis();
        assertContains("/dir/file2", analysis.toString());
    }

    private void assertContains(String needle, String haystack) {
        assertTrue(haystack.contains(needle), "\"" + haystack +"\" is exptected to contain \"" + needle + "\"");
    }
    
    private Collection<Directory> getTwoInputs() {
        Arrays.asList(getInputMetric1(), getInputMetric2());
        return Arrays.asList(getInputMetric1(), getInputMetric2());
    }

    private Directory getInputMetric1() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.123, 0.02));
        metrics.methodName("MethodName1");
        metrics.fileName("/dir/file");
        Method method = metrics.build();
        
        File file = new File("file");
        file.addMethodMetric(method);
        Directory directory = new Directory("dir");
        directory.addFileMetrics(file);
        
        return directory;
    }
    
    private Directory getInputMetric2() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.234, 0.04));
        metrics.methodName("MethodName2");
        metrics.fileName("/dir/file2");
        Method method = metrics.build();
        
        File file = new File("file2");
        file.addMethodMetric(method);
        Directory directory = new Directory("dir");
        directory.addFileMetrics(file);
        
        return directory;
    }
}
