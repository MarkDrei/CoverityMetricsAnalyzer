package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.MethodMetrics.MethodMetricsBuilder;

public class FileMetricsListTest_singleFileSetup {
    
    private static Collection<FileMetrics> fileMetrics;
    
    @BeforeAll
    public static void setup() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        MethodMetrics metricsA = builder.fileName("fileA").methodName("methodA").build();
        
        builder = new MethodMetricsBuilder();
        MethodMetrics metricsB = builder.fileName("fileA").methodName("methodB").build();  
        
        List<MethodMetrics> metrics = Arrays.asList(metricsA, metricsB);
        
        FileMetricsList hierarchyBuilder = new FileMetricsList();
        fileMetrics = hierarchyBuilder.generateFileMetrics(metrics);
    }


    @Test
    public void combineTwoMethodsInOneFile() {
        assertEquals(1, fileMetrics.size());
        assertEquals("fileA", fileMetrics.iterator().next().getFile());
    }
    
    
    @Test
    public void ensureMethodMetricsAreAccessible() {
        FileMetrics fileMetric = fileMetrics.iterator().next();
        Collection<MethodMetrics> methodMetrics = fileMetric.getMethodMetrics();
        assertEquals(2, methodMetrics.size());
    }
}
