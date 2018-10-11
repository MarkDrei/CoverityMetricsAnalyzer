package de.rkable.coverity.metrics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DirectoryTest {
    
    @Test
    public void testThatDirectoryWithoutSlashIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Directory(""));
    }
    
    @Test
    public void testSimpleHierarchyPrintout() {
        Directory directory = new Directory("/");
        
        String expected = "directory: /\n";
        
        assertEquals(expected, directory.printHierarchy());
    }
    
    @Test
    public void testSimpleHierarchyWithNamePrintout() {
        Directory directory = new Directory("/root");
        
        String expected = "directory: /root\n";
        
        assertEquals(expected, directory.printHierarchy());
    }
    
    @Test
    public void testTwoLevelHierarchy() {
        Directory root = new Directory("/root");
        Directory child = new Directory("/root/child");
        root.addChild(child);
        
        String expected = 
                "directory: /root\n"
                + "    directory: /root/child\n";
        
        assertEquals(expected, root.printHierarchy());
    }

    @Test
    public void testHierarchyWithFiles() {
        Directory root = new Directory("/root");
        File file = new File("/root/fileA");
        root.addFile(file);
        Directory child = new Directory("/root/child");
        root.addChild(child);
        
        String expected = 
                "directory: /root\n"
                + "  file: /root/fileA\n"
                + "    directory: /root/child\n";
        
        assertEquals(expected, root.printHierarchy());
    }
}
