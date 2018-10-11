package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;
import static de.rkable.coverity.analyze.StringAssertions.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.Directory;

public class CallgrindAnalyzerTest {

    static private String analysis;

    @BeforeAll
    public static void prepareAnalysis() {
        Collection<Directory> inputs = TestInput.getTwoInputs();

        MetricsAnalyzer analyzer = new CallgrindAnalyzer();
        analyzer.startAnalysis(inputs);
        analysis = analyzer.getAnalysis().toString();
        assertNotNull(analysis);
    }

    private void assertAnalysisContains(String needle) {
        assertContains(needle, analysis);
    }

    @Test
    public void theHeaderContainsTheFormat() {
        assertAnalysisContains("# callgrind format");
    }

    @Test
    public void testTheEventsLineContainsTheCorrectEvents() {
        assertAnalysisContains("events: HalsteadEffort HalsteadError");
    }

    @Test
    public void testThatAllFilesArePresent() {
        assertAnalysisContains("fl=/dir/file1");
        assertAnalysisContains("fl=/dir/file2");
    }

    @Test
    public void testThatAllMethodsArePresent() {
        assertAnalysisContains("fn=method MethodName1");
        assertAnalysisContains("fn=method MethodName2");
    }
    
    @Test
    public void testThatAllMetricsArePresent() {
        assertAnalysisContains("123 20");
        assertAnalysisContains("234 40");
    }
    
    @Test
    public void testNoIntegerOverflow() {
        Directory input = TestInput.getInputMetricWithIntIverflow();

        MetricsAnalyzer analyzer = new CallgrindAnalyzer();
        analyzer.startAnalysis(Arrays.asList(input));
        String result = analyzer.getAnalysis().toString();
        
        // these numbers are bigger than int32 max
        assertTrue(result.contains("2500000000234"));
        assertTrue(result.contains("2500000000040"));
    }

    @Test
    public void testFullOutput() {
        String expected = 
                "# callgrind format\n" 
                + "positions: index\n"
                + "events: HalsteadEffort HalsteadError\n"
                + "\n"
                + "fl=/dir\n"
                + "fn=directory /dir\n"
                + "0 0\n"
                + "cfi=/dir/file1\n"
                + "cfn=file file1\n"
                + "calls=1\n"
                + "123 20\n"
                + "cfi=/dir/file2\n"
                + "cfn=file file2\n"
                + "calls=1\n"
                + "234 40\n"
                + "\n"
                + "fl=/dir/file1\n"
                + "fn=file file1\n"
                + "0 0\n"
                + "cfn=method MethodName1\n"
                + "calls=1\n"
                + "123 20\n"
                + "\n"
                + "fl=/dir/file1\n"
                + "fn=method MethodName1\n"
                + "123 20\n"
                + "\n"
                + "fl=/dir/file2\n"
                + "fn=file file2\n"
                + "0 0\n"
                + "cfn=method MethodName2\n"
                + "calls=1\n"
                + "234 40\n"
                + "\n"
                + "fl=/dir/file2\n"
                + "fn=method MethodName2\n"
                + "234 40\n"
                + "\n"
                ;

        assertEquals(expected, analysis);
    }
    
    @Test
    public void testHierarchicalOutput() {
        Collection<Directory> inputs = TestInput.getThreeInputs();

        MetricsAnalyzer analyzer = new CallgrindAnalyzer();
        analyzer.startAnalysis(inputs);
        String localAnalysis = analyzer.getAnalysis().toString();
        assertNotNull(localAnalysis);
        
        String expected = "# callgrind format\n" + 
                "positions: index\n" + 
                "events: HalsteadEffort HalsteadError\n" + 
                "\n" + 
                "fl=/dir/\n" + 
                "fn=directory /dir/\n" + 
                "0 0\n" +
                "cfi=/dir/sub\n" +
                "cfn=directory /dir/sub\n" +
                "calls=1\n" + 
                "423 50\n" + 
                "cfi=/dir/file2\n" + 
                "cfn=file file2\n" + 
                "calls=1\n" + 
                "234 40\n" + 
                "\n" + 
                "fl=/dir/file2\n" + 
                "fn=file file2\n" + 
                "0 0\n" + 
                "cfn=method MethodName2\n" + 
                "calls=1\n" + 
                "234 40\n" + 
                "\n" + 
                "fl=/dir/file2\n" + 
                "fn=method MethodName2\n" + 
                "234 40\n" + 
                "\n" + 
                "fl=/dir/sub\n" + 
                "fn=directory /dir/sub\n" + 
                "0 0\n" + 
                "cfi=/dir/sub/file1\n" + 
                "cfn=file file1\n" + 
                "calls=1\n" + 
                "423 50\n" + 
                "\n" + 
                "fl=/dir/sub/file1\n" + 
                "fn=file file1\n" + 
                "0 0\n" + 
                "cfn=method MethodName1\n" + 
                "calls=1\n" + 
                "123 20\n" + 
                "cfn=method MethodName3\n" + 
                "calls=1\n" + 
                "300 30\n" + 
                "\n" + 
                "fl=/dir/sub/file1\n" + 
                "fn=method MethodName1\n" + 
                "123 20\n" + 
                "\n" + 
                "fl=/dir/sub/file1\n" + 
                "fn=method MethodName3\n" + 
                "300 30\n" + 
                "\n"
                ;
        
        assertEquals(expected, localAnalysis);
    }
}











