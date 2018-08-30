package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.FileMetrics;
import de.rkable.coverity.MethodMetrics;
import de.rkable.coverity.MethodMetrics.MethodMetricsBuilder;

public class FileHierarchyTest {

    @Test
    public void combineTwoMethodsInOneFile() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        MethodMetrics metricsA = builder.fileName("fileA").methodName("methodA").build();
        
        builder = new MethodMetricsBuilder();
        MethodMetrics metricsB = builder.fileName("fileA").methodName("methodB").build();  
        
        List<MethodMetrics> metrics = Arrays.asList(metricsA, metricsB);
        
        FileHierarchy hierarchyBuilder = new FileHierarchy();
        Collection<FileMetrics> fileMetrics = hierarchyBuilder.buildHierarchy(metrics);
        
        assertEquals(1, fileMetrics.size());
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
        
        FileHierarchy hierarchyBuilder = new FileHierarchy();
        Collection<FileMetrics> fileMetrics = hierarchyBuilder.buildHierarchy(metrics);
        
        assertEquals(2, fileMetrics.size());
    }
}
