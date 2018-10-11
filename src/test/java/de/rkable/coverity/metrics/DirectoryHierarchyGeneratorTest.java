package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DirectoryHierarchyGeneratorTest {

    @Test
    public void combineTwoFilesInSameDirectory() {
        File metricsA = new File("/fileA");
        File metricsB = new File("/fileB");
        
        List<File> fileList = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Directory directory = hierarchy.buildHierarchy(fileList);
        
        String expected = 
                "directory: /\n" + 
                "  file: /fileA\n" + 
                "  file: /fileB\n";
        
        assertEquals(expected, directory.printHierarchy());
    }
    
    @Test
    public void combineTwoFilesInSameSubDirectory() {
        File metricsA = new File("/path/fileA");
        File metricsB = new File("/path/fileB");
        
        List<File> fileList = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Directory directory = hierarchy.buildHierarchy(fileList);
        
        String expected = 
                "directory: /\n" +
                "    directory: /path\n" +
                "      file: /path/fileA\n" + 
                "      file: /path/fileB\n";
        
        assertEquals(expected, directory.printHierarchy());
    }
    
    @Test
    public void combineTwoFilesDifferentDirectories() {
        File metricsA = new File("/pathA/fileA");
        File metricsB = new File("/pathB/fileB");
        
        List<File> fileList = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Directory directory = hierarchy.buildHierarchy(fileList);
        
        String expected = 
                "directory: /\n" + 
                "    directory: /pathA\n" + 
                "      file: /pathA/fileA\n" + 
                "    directory: /pathB\n" + 
                "      file: /pathB/fileB\n";
        
        assertEquals(expected, directory.printHierarchy());
    }
    
    @Test
    public void combineTwoFilesDifferentDirectoryLevels() {
        File metricsA = new File("/pathA/pathAB/fileA");
        File metricsB = new File("/pathB/fileA");
        
        List<File> fileList = Arrays.asList(metricsA, metricsB);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Directory directory = hierarchy.buildHierarchy(fileList);
        
        String expected = 
                "directory: /\n" + 
                "    directory: /pathA/pathAB\n" + 
                "      file: /pathA/pathAB/fileA\n" + 
                "    directory: /pathB\n" + 
                "      file: /pathB/fileA\n";
        
        assertEquals(expected, directory.printHierarchy());
    }
    
    @Test
    public void combineTwoFilesWithCommonSubdirectory() {
        File metricsA = new File("/pathA/pathAB/pathABC/fileA");
        File metricsB = new File("/pathA/pathAB/fileA");
        File metricsC = new File("/pathA/fileA");
        
        List<File> fileList = Arrays.asList(metricsA, metricsB, metricsC);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Directory directory = hierarchy.buildHierarchy(fileList);
        
        String expected = 
                "directory: /\n" + 
                "    directory: /pathA\n" + 
                "      file: /pathA/fileA\n" + 
                "        directory: /pathA/pathAB\n" + 
                "          file: /pathA/pathAB/fileA\n" + 
                "            directory: /pathA/pathAB/pathABC\n" + 
                "              file: /pathA/pathAB/pathABC/fileA\n";
        
        assertEquals(expected, directory.printHierarchy());
    }
    
    @Test
    public void combineManySubdirectories() {
        File metricsA = new File("/pathA/pathAB/fileB");
        File metricsS = new File("/pathA/pathAC/fileS");
        File metricsB = new File("/pathA/pathAB/fileE");
        File metricsE = new File("/pathA/pathAB/fileF");
        File metricsD = new File("/pathA/pathAC/fileD");
        File metricsC = new File("/pathA/fileC");
        
        List<File> fileList = Arrays.asList(metricsA, metricsC, metricsD, metricsS, metricsB, metricsE);
        
        DirectoryHierarchyGenerator hierarchy = new DirectoryHierarchyGenerator();
        Directory directory = hierarchy.buildHierarchy(fileList);
        
        String expected = 
                "directory: /\n" + 
                "    directory: /pathA\n" + 
                "      file: /pathA/fileC\n" + 
                "        directory: /pathA/pathAB\n" + 
                "          file: /pathA/pathAB/fileB\n" + 
                "          file: /pathA/pathAB/fileE\n" + 
                "          file: /pathA/pathAB/fileF\n" + 
                "        directory: /pathA/pathAC\n" + 
                "          file: /pathA/pathAC/fileD\n" + 
                "          file: /pathA/pathAC/fileS\n";
        
        assertEquals(expected, directory.printHierarchy());
    }
    
 
}
