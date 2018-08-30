package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.FileMetrics;
import de.rkable.coverity.MethodMetrics;
import de.rkable.coverity.MethodMetrics.MethodMetricsBuilder;

public class FileMetricsListTest {

    @Test
    public void combineTwoMethodsInOneFile() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        MethodMetrics metricsA = builder.fileName("fileA").methodName("methodA").build();
        
        builder = new MethodMetricsBuilder();
        MethodMetrics metricsB = builder.fileName("fileA").methodName("methodB").build();  
        
        List<MethodMetrics> metrics = Arrays.asList(metricsA, metricsB);
        
        FileMetricsList hierarchyBuilder = new FileMetricsList();
        Collection<FileMetrics> fileMetrics = hierarchyBuilder.generateFileMetrics(metrics);
        
        assertEquals(1, fileMetrics.size());
        assertEquals("fileA", fileMetrics.iterator().next().getFile());
    }
    
    @Test
    public void combineTwoMethodsInTwoFile() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        MethodMetrics metricsA = builder.fileName("fileA").methodName("methodA").build();
        
        builder = new MethodMetricsBuilder();
        MethodMetrics metricsB = builder.fileName("fileB").methodName("methodB").build();  
        
        builder = new MethodMetricsBuilder();
        MethodMetrics metricsC = builder.fileName("fileB").methodName("methodC").build();
        
        List<MethodMetrics> metrics = Arrays.asList(metricsA, metricsB, metricsC);
        
        FileMetricsList hierarchyBuilder = new FileMetricsList();
        Collection<FileMetrics> fileMetrics = hierarchyBuilder.generateFileMetrics(metrics);
        
        assertEquals(2, fileMetrics.size());
        
        // check the files
        List<String> fileNames = new ArrayList<>();
        for (FileMetrics fileMetric : fileMetrics) {
            fileNames.add(fileMetric.getFile());
        }
        assertTrue(fileNames.contains("fileA"));
        assertTrue(fileNames.contains("fileB"));
    }
}
