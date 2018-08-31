package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.FileMetrics;
import de.rkable.coverity.metrics.FileMetricsList;
import de.rkable.coverity.metrics.MethodMetrics;
import de.rkable.coverity.metrics.MethodMetrics.MethodMetricsBuilder;

public class FileMetricsListTest_twoFiles {
    
    private static Collection<FileMetrics> fileMetrics;
    
    @BeforeAll
    static public void setup() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        MethodMetrics metricsA = builder.fileName("fileA").methodName("methodA").build();
        
        builder = new MethodMetricsBuilder();
        MethodMetrics metricsB = builder.fileName("fileB").methodName("methodB").build();  
        
        builder = new MethodMetricsBuilder();
        MethodMetrics metricsC = builder.fileName("fileB").methodName("methodC").build();
        
        List<MethodMetrics> metrics = Arrays.asList(metricsA, metricsB, metricsC);
        
        FileMetricsList hierarchyBuilder = new FileMetricsList();
        fileMetrics = hierarchyBuilder.generateFileMetrics(metrics);
    }

    
    @Test
    public void combineTwoMethodsInTwoFile() {
        assertEquals(2, fileMetrics.size());
        
        // check the files
        List<String> fileNames = new ArrayList<>();
        for (FileMetrics fileMetric : fileMetrics) {
            fileNames.add(fileMetric.getFile());
        }
        assertTrue(fileNames.contains("fileA"));
        assertTrue(fileNames.contains("fileB"));
    }
    
    @Test
    public void testThatFileAContainsOneMetric() {
        // find correct fileMetric
        FileMetrics lookup = null;
        for (FileMetrics fileMetric : fileMetrics) {
            if (fileMetric.getFile() == "fileA") {
                lookup = fileMetric;
                break;
            }
        }
        assertEquals(1, lookup.getMethodMetrics().size());
    }
    
    @Test
    public void testThatFileBContainsTwoMetrics() {
        // find correct fileMetric
        FileMetrics lookup = null;
        for (FileMetrics fileMetric : fileMetrics) {
            if (fileMetric.getFile() == "fileB") {
                lookup = fileMetric;
                break;
            }
        }
        assertEquals(2, lookup.getMethodMetrics().size());
    }

}
