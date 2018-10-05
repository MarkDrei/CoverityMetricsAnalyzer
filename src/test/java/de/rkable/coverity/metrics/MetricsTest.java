package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MetricsTest {

    @Test
    public void combineMetrics() {
        Metrics metrics = new Metrics(1, 2);
        metrics = metrics.combine(new Metrics(3, 3));
        assertEquals(new Metrics(4, 5), metrics);
    }
}
