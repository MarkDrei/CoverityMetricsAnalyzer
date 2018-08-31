package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.File;
import de.rkable.coverity.metrics.FileListGenerator;
import de.rkable.coverity.metrics.Method;
import de.rkable.coverity.metrics.Method.MethodMetricsBuilder;

public class FileListGeneratorTest_twoFiles {
    
    private static Collection<File> fileMetrics;
    
    @BeforeAll
    static public void setup() {
        MethodMetricsBuilder builder = new MethodMetricsBuilder();
        Method metricsA = builder.fileName("fileA").methodName("methodA").build();
        
        builder = new MethodMetricsBuilder();
        Method metricsB = builder.fileName("fileB").methodName("methodB").build();  
        
        builder = new MethodMetricsBuilder();
        Method metricsC = builder.fileName("fileB").methodName("methodC").build();
        
        List<Method> metrics = Arrays.asList(metricsA, metricsB, metricsC);
        
        FileListGenerator hierarchyBuilder = new FileListGenerator();
        fileMetrics = hierarchyBuilder.generateFileMetrics(metrics);
    }

    
    @Test
    public void combineTwoMethodsInTwoFile() {
        assertEquals(2, fileMetrics.size());
        
        // check the files
        List<String> fileNames = new ArrayList<>();
        for (File fileMetric : fileMetrics) {
            fileNames.add(fileMetric.getFile());
        }
        assertTrue(fileNames.contains("fileA"));
        assertTrue(fileNames.contains("fileB"));
    }
    
    @Test
    public void testThatFileAContainsOneMetric() {
        // find correct fileMetric
        File lookup = null;
        for (File fileMetric : fileMetrics) {
            if (fileMetric.getFile() == "fileA") {
                lookup = fileMetric;
                break;
            }
        }
        assertEquals(1, lookup.getMethods().size());
    }
    
    @Test
    public void testThatFileBContainsTwoMetrics() {
        // find correct fileMetric
        File lookup = null;
        for (File fileMetric : fileMetrics) {
            if (fileMetric.getFile() == "fileB") {
                lookup = fileMetric;
                break;
            }
        }
        assertEquals(2, lookup.getMethods().size());
    }

}
