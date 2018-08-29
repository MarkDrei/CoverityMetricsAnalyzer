package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.MethodMetrics;
import de.rkable.coverity.Metrics;
import de.rkable.coverity.input.MetricsFileReader;

public class MetricsFileReaderTest {

    @Test
    public void testThatMetricsAreOnlyAvailableAfterParsing() throws Exception {
        MetricsFileReader reader = new MetricsFileReader("src/test/resources/MetricsSingle.xml");
        List<MethodMetrics> metrics = reader.getMetricEntities();
        assertNull(metrics);

        reader.parseFile();
        metrics = reader.getMetricEntities();
        assertNotNull(metrics);
    }

    @Test
    public void testParsingFailsWhenFileDoesNotExist() {
        MetricsFileReader reader = new MetricsFileReader("fileWhichDoesNotExist.xml");
        assertThrows(Exception.class, () -> reader.parseFile());
    }

    @Test
    public void testThatSingleMethodIsRead() throws Exception {
        MetricsFileReader reader = new MetricsFileReader("src/test/resources/MetricsSingle.xml");
        reader.parseFile();
        List<MethodMetrics> metricsList = reader.getMetricEntities();
        assertEquals(1, metricsList.size());
        Metrics metrics = metricsList.get(0).getMetrics();
        assertEquals(1207.17, metrics.halsteadEffort, 0.01);
    }
}
