package de.rkable.coverity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MeasuredEntityTest {

    @Test
    public void testMeasuredEntity() {
        MeasuredEntity measuredEntity = new MeasuredEntity();
        Metrics metrics = measuredEntity.getMetrics();
        assertNotNull(metrics);
    }
}
