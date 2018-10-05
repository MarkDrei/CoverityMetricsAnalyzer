package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FileTest {
    
    @Test
    public void testFileNameExtraction() {
        File file = new File("dir/to/file.c");
        assertEquals("dir/to/file.c", file.getPath());
        assertEquals("file.c", file.getFileName());
    }
    
    @Test
    public void testCombinationOfSingleMethodMetric() {
        File file = new File("file");
        file.addMethod(new Method("file", "method", new Metrics(123, 20)));
        Metrics fileMetric = file.getCombinedMetric();
        assertEquals(new Metrics(123, 20), fileMetric);
    }
    
    @Test
    public void testCombinationOfTwoMethodMetric() {
        File file = new File("file");
        file.addMethod(new Method("file", "method", new Metrics(123, 20)));
        file.addMethod(new Method("file", "method2", new Metrics(123, 20)));
        Metrics fileMetric = file.getCombinedMetric();
        assertEquals(new Metrics(246, 40), fileMetric);
    }

}
