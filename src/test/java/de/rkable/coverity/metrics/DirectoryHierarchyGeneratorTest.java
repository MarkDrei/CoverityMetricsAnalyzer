package de.rkable.coverity.metrics;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.metrics.DirectoryHierarchyGenerator;
import de.rkable.coverity.metrics.DirectoryMetrics;
import de.rkable.coverity.metrics.FileMetrics;

public class DirectoryHierarchyGeneratorTest {

    @Test
    public void combineTwoFilesInSameDirectory() {
        FileMetrics metricsA = new FileMetrics("fileA");
        FileMetrics metricsB = new FileMetrics("fileA");
        
        List<FileMetrics> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<DirectoryMetrics> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(1, directories.size());
        assertEquals("", directories.iterator().next().getDirectory());
    }
    
    @Test
    public void combineTwoFilesInSameSubDirectory() {
        FileMetrics metricsA = new FileMetrics("path/fileA");
        FileMetrics metricsB = new FileMetrics("path/fileA");
        
        List<FileMetrics> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<DirectoryMetrics> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(1, directories.size());
        assertEquals("path", directories.iterator().next().getDirectory());
    }
    
    @Test
    public void combineTwoFilesDifferentDirectories() {
        FileMetrics metricsA = new FileMetrics("pathA/fileA");
        FileMetrics metricsB = new FileMetrics("pathB/fileA");
        
        List<FileMetrics> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<DirectoryMetrics> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(2, directories.size());
        
        // collect the paths
        List<String> dirs = new ArrayList<>();
        for(DirectoryMetrics d : directories) {
            dirs.add(d.getDirectory());
        }
        
        assertTrue(dirs.contains("pathA"));
        assertTrue(dirs.contains("pathB"));
    }
    
    @Test
    public void combineTwoFilesDifferentDirectoryLevels() {
        FileMetrics metricsA = new FileMetrics("pathA/pathAB/fileA");
        FileMetrics metricsB = new FileMetrics("pathB/fileA");
        
        List<FileMetrics> filesMetrics = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<DirectoryMetrics> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(2, directories.size());
        
        // collect the paths
        List<String> dirs = new ArrayList<>();
        for(DirectoryMetrics d : directories) {
            dirs.add(d.getDirectory());
        }
        
        assertTrue(dirs.contains("pathA/pathAB"));
        assertTrue(dirs.contains("pathB"));
    }
    
    @Test
    public void combineTwoFilesWithCommonSubdirectory() {
        FileMetrics metricsA = new FileMetrics("pathA/pathAB/pathABC/fileA");
        FileMetrics metricsB = new FileMetrics("pathA/pathAB/fileA");
        FileMetrics metricsC = new FileMetrics("pathA/fileA");
        
        List<FileMetrics> filesMetrics = Arrays.asList(metricsA, metricsB, metricsC);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Collection<DirectoryMetrics> directories = hierarchy.buildHierarchy(filesMetrics);
        assertEquals(1, directories.size());
        assertEquals("pathA", directories.iterator().next().getDirectory());
        List<DirectoryMetrics> children = directories.iterator().next().getChildren();
        assertEquals(1, children.size());
        assertEquals("pathA/pathAB", children.get(0).getDirectory());
        children = children.get(0).getChildren();
        assertEquals(1, children.size());
        assertEquals("pathA/pathAB/pathABC", children.get(0).getDirectory());
        assertEquals(0, children.get(0).getChildren().size());
    }
}
