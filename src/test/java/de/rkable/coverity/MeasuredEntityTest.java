package de.rkable.coverity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MeasuredEntityTest {

    @Test
    public void testMeasuredEntity() {
        MethodMetrics measuredEntity = new MethodMetrics();
        measuredEntity.setMetrics(new Metrics(0.123, 0.02));
        Metrics metrics = measuredEntity.getMetrics();
        assertNotNull(metrics);
    }
}
