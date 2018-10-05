package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;
import static de.rkable.coverity.analyze.StringAssertions.*;

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
        assertAnalysisContains("fn=MethodName1");
        assertAnalysisContains("fn=MethodName2");
    }
    
    @Test
    public void testThatAllMetricsArePresent() {
        assertAnalysisContains("123 20");
        assertAnalysisContains("234 40");
    }

    @Test
    public void testFullOutput() {
        String expected = 
                "# callgrind format\n" 
                + "positions: index\n"
                + "events: HalsteadEffort HalsteadError\n"
                + "\n"
                + "fl=/dir/file1\n"
                + "fn=MethodName1\n"
                + "123 20\n"
                + "\n"
                + "fl=/dir/file2\n"
                + "fn=MethodName2\n"
                + "234 40\n"
                + "\n"
                ;

        assertEquals(expected, analysis);
    }
}











