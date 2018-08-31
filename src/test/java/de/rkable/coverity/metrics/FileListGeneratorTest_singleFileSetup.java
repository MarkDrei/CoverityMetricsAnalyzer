package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.Method.MethodMetricsBuilder;

public class FileListGeneratorTest_singleFileSetup {
    
    private static Collection<File> fileMetrics;
    
    @BeforeAll
    public static void setup() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        Method metricsA = builder.fileName("fileA").methodName("methodA").build();
        
        builder = new MethodMetricsBuilder();
        Method metricsB = builder.fileName("fileA").methodName("methodB").build();  
        
        List<Method> metrics = Arrays.asList(metricsA, metricsB);
        
        FileListGenerator hierarchyBuilder = new FileListGenerator();
        fileMetrics = hierarchyBuilder.generateFileMetrics(metrics);
    }


    @Test
    public void combineTwoMethodsInOneFile() {
        assertEquals(1, fileMetrics.size());
        assertEquals("fileA", fileMetrics.iterator().next().getFile());
    }
    
    
    @Test
    public void ensureMethodMetricsAreAccessible() {
        File fileMetric = fileMetrics.iterator().next();
        Collection<Method> methodMetrics = fileMetric.getMethods();
        assertEquals(2, methodMetrics.size());
    }
}
