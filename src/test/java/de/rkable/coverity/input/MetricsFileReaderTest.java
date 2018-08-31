package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.input.MetricsFileReader;
import de.rkable.coverity.metrics.MethodMetrics;
import de.rkable.coverity.metrics.Metrics;

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
    
    @Test
    public void testThatThreeMethodsAreRead() throws Exception {
        MetricsFileReader reader = new MetricsFileReader("src/test/resources/MetricsThreeLines.xml");
        reader.parseFile();
        List<MethodMetrics> metricsList = reader.getMetricEntities();
        assertEquals(3, metricsList.size());
        Metrics metrics = metricsList.get(0).getMetrics();
        assertEquals(1107.17, metrics.halsteadEffort, 0.01);
        metrics = metricsList.get(1).getMetrics();
        assertEquals(1207.17, metrics.halsteadEffort, 0.01);
        metrics = metricsList.get(2).getMetrics();
        assertEquals(1307.17, metrics.halsteadEffort, 0.01);
    }
}
