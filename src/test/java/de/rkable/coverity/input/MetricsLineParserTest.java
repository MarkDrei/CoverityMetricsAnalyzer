package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.input.MetricsLineParser;
import de.rkable.coverity.metrics.Metrics;

public class MetricsLineParserTest {

    @Test
    public void testThatNonFileLineIsRecognized() {
        MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c");
        assertFalse(parser.isLineWithFile());
        assertThrows(IllegalStateException.class, () -> parser.getFileName());
    }

    @Test
    public void testThatFileNameIsRecognized() {
        MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c</file>\n");
        assertTrue(parser.isLineWithFile());
        assertEquals(Paths.get("/path/to/file/File.c"), parser.getFileName());
    }

    @Test
    public void testThatIsLineWithFileNameMustNotBeCalledFirst() {
        MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c</file>\n");
        assertEquals(Paths.get("/path/to/file/File.c"), parser.getFileName());
    }

    @Test
    public void testThatNonMetricsLineIsRecognized() {
        MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c");
        assertFalse(parser.isLineWithMetrics());
        assertThrows(IllegalStateException.class, () -> parser.getMetrics());
    }

    @Test
    public void testThatLineWithMetricsIsRecognized() {
        MetricsLineParser parser = new MetricsLineParser(
                "<metrics>be:0;"
                + "fe:9;"
                + "bl:7;"
                + "lc:26;"
                + "on:14;"
                + "ot:9;"
                + "cc:4;"
                + "pce:4;"
                + "pcs:3;"
                + "hf:1207.17;"
                + "hr:0.0574841;"
                + "ml:1229"
                + "</metrics>"
                );
        assertTrue(parser.isLineWithMetrics());
        assertEquals(new Metrics(1207.17, 0.0574841), parser.getMetrics());
        // also works a second time
        assertEquals(new Metrics(1207.17, 0.0574841), parser.getMetrics());
    }
    
    @Test
    public void testThatZeroIsValidDouble() {
        MetricsLineParser parser = new MetricsLineParser(
                "<metrics>be:0;"
                        + "fe:9;"
                        + "bl:7;"
                        + "lc:26;"
                        + "on:14;"
                        + "ot:9;"
                        + "cc:4;"
                        + "pce:4;"
                        + "pcs:3;"
                        + "hf:0;"
                        + "hr:0;"
                        + "ml:1229"
                        + "</metrics>"
                );
        assertTrue(parser.isLineWithMetrics());
        assertEquals(new Metrics(0, 0), parser.getMetrics());
    }
    
    @Test
    public void testThatScientificNotationIsValidInPce() {
        MetricsLineParser parser = new MetricsLineParser(
                "<metrics>be:0;"
                        + "fe:9;"
                        + "bl:7;"
                        + "lc:26;"
                        + "on:14;"
                        + "ot:9;"
                        + "cc:4;"
                        + "pce:4e+28;"
                        + "pcs:3e+28;"
                        + "hf:5e+28;"
                        + "hr:6e+29;"
                        + "ml:1229"
                        + "</metrics>"
                );
        assertTrue(parser.isLineWithMetrics());
        assertEquals(5e28, parser.getMetrics().halsteadEffort, 1);
        assertEquals(6e29, parser.getMetrics().halsteadError, 1);
    }
    
    @Test
    public void testBug() {
        MetricsLineParser parser = new MetricsLineParser(
                "<metrics>"
                    + "be:0;"
                    + "fe:373;"
                    + "bl:280;"
                    + "lc:741;"
                    + "on:1066;"
                    + "ot:10;"
                    + "cc:95;"
                    + "pce:1.9807e+28;"
                    + "pcs:1.9807e+28;"
                    + "hf:402642;"
                    + "hr:15.3792;"
                    + "ml:810"
                    + "</metrics>"
                );
        assertTrue(parser.isLineWithMetrics());
    }

    @Test
    public void testThatNonNameLineIsRecognized() {
        MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c");
        assertFalse(parser.isLineWithMethodName());
        assertThrows(IllegalStateException.class, () -> parser.getMethodName());
    }

    @Test
    public void testThatMethodNameIsParsedCorrectly() {
        MetricsLineParser parser = new MetricsLineParser("<names><![CDATA[fn:MethodName;]]></names>");
        assertTrue(parser.isLineWithMethodName());
        assertEquals("MethodName", parser.getMethodName());
        // also works a second time
        assertEquals("MethodName", parser.getMethodName());
    }

}
