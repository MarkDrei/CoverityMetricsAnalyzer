package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.DirectoryHierarchyGenerator;
import de.rkable.coverity.metrics.Directory;
import de.rkable.coverity.metrics.File;

public class DirectoryHierarchyGeneratorTest {

    @Test
    public void combineTwoFilesInSameDirectory() {
        File metricsA = new File("fileA");
        File metricsB = new File("fileB");
        
        List<File> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<Directory> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(1, directories.size());
        assertEquals("", directories.iterator().next().getDirectory());
    }
    
    @Test
    public void combineTwoFilesInSameSubDirectory() {
        File metricsA = new File("path/fileA");
        File metricsB = new File("path/fileB");
        
        List<File> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<Directory> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(1, directories.size());
        assertEquals("path", directories.iterator().next().getDirectory());
    }
    
    @Test
    public void combineTwoFilesDifferentDirectories() {
        File metricsA = new File("pathA/fileA");
        File metricsB = new File("pathB/fileB");
        
        List<File> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<Directory> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(2, directories.size());
        
        // collect the paths
        List<String> dirs = new ArrayList<>();
        for(Directory d : directories) {
            dirs.add(d.getDirectory());
        }
        
        assertTrue(dirs.contains("pathA"));
        assertTrue(dirs.contains("pathB"));
    }
    
    @Test
    public void combineTwoFilesDifferentDirectoryLevels() {
        File metricsA = new File("pathA/pathAB/fileA");
        File metricsB = new File("pathB/fileA");
        
        List<File> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<Directory> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(2, directories.size());
        
        // collect the paths
        List<String> dirs = new ArrayList<>();
        for(Directory d : directories) {
            dirs.add(d.getDirectory());
        }
        
        assertTrue(dirs.contains("pathA/pathAB"));
        assertTrue(dirs.contains("pathB"));
    }
    
    @Test
    public void combineTwoFilesWithCommonSubdirectory() {
        File metricsA = new File("pathA/pathAB/pathABC/fileA");
        File metricsB = new File("pathA/pathAB/fileA");
        File metricsC = new File("pathA/fileA");
        
        List<File> filesMetrics = Arrays.asList(metricsA, metricsB, metricsC);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<Directory> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(1, directories.size());
        assertEquals("pathA", directories.iterator().next().getDirectory());
        List<Directory> children = directories.iterator().next().getChildren();
        assertEquals(1, children.size());
        assertEquals("pathA/pathAB", children.get(0).getDirectory());
        children = children.get(0).getChildren();
        assertEquals(1, children.size());
        assertEquals("pathA/pathAB/pathABC", children.get(0).getDirectory());
        assertEquals(0, children.get(0).getChildren().size());
    }
    
    @Test
    public void ensureFileMetricsAreAvailable() {
        File metricsA = new File("fileA");
        File metricsB = new File("fileB");
        
        List<File> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<Directory> directories = hierarchy.buildHierarchy(filesMetrics);
        Directory directoryMetrics = directories.iterator().next();
        Collection<File> fileMetrics = directoryMetrics.getFileMetrics();
        
        assertEquals(2, fileMetrics.size());
        List<String> collectedFileNames = new ArrayList<>();
        for (File metric : fileMetrics) {
            collectedFileNames.add(metric.getFile());
        }
        assertEquals(2, collectedFileNames.size());
        assertTrue(collectedFileNames.contains("fileA"));
        assertTrue(collectedFileNames.contains("fileB"));
    }
}
