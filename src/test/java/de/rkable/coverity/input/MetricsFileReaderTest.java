package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.Metrics;
import de.rkable.coverity.reader.MetricsFileReader;

public class MetricsFileReaderTest {

    @Test
    public void testThatMetricsAreOnlyAvailableAfterParsing() throws Exception {
        MetricsFileReader reader = new MetricsFileReader("src/test/resources/MetricsSingle.xml");
        List<Metrics> metrics = reader.getMetrics();
        assertNull(metrics);

        reader.parseFile();
        metrics = reader.getMetrics();
        assertNotNull(metrics);
    }

    @Test
    public void testParsingFailsWhenFileDoesNotExist() {
        MetricsFileReader reader = new MetricsFileReader("fileWhichDoesNotExist.xml");
        assertThrows(Exception.class, () -> reader.parseFile());
    }

    @Test
    public void testThatSingleMetricIsRead() throws Exception {
        MetricsFileReader reader = new MetricsFileReader("src/test/resources/MetricsSingle.xml");
        reader.parseFile();
        List<Metrics> metrics = reader.getMetrics();
        assertEquals(1, metrics.size());
    }
}
