package de.rkable.coverity;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.MethodMetrics.MethodMetricsBuilder;

public class MethodMetricsBuilderTest {

    @Test
    public void testSimpleConstruction() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        MethodMetrics metrics = builder.build();
        assertNotNull(metrics);
    }
    
    @Test
    public void testConstructionWithStrings() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        builder.methodName("methodName").fileName("fileName");
        MethodMetrics metrics = builder.build();
        assertEquals("methodName", metrics.getMethodName());
        assertEquals("fileName", metrics.getFileName());
    }
    
    @Test
    public void testConstructionWithMetrics() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        Metrics metricValues = new Metrics(0.123, 0.03);
        builder.metrics(metricValues).methodName("methodName");
        MethodMetrics metrics = builder.build();
        assertEquals("methodName", metrics.getMethodName());
        assertEquals(metricValues, metrics.getMetrics());
    }
}
