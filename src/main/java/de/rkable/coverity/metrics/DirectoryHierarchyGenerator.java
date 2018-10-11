package de.rkable.coverity.metrics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Converts a bunch of file metrics into a hierarchy of directory metrics
 *
 */
public class DirectoryHierarchyGenerator {

    public Directory buildHierarchy(Collection<File> fileMetrics) {
        SortedMap<String, Directory> directories = combineFilesInSameDirectories(fileMetrics);
        return convertToHierarchy(directories);
    }

    /**
     * Puts all sub-directories into their parent-directories
     * @param directories InOut param
     */
    private Directory convertToHierarchy(SortedMap<String, Directory> directories) {
        Map<String, Directory> allDirectories = new HashMap<>();
        
        // ensure there is a root
        Directory root = directories.remove("/");
        if (root == null) {
            root = new Directory("/");
            allDirectories.put("/", root);
        }
        
        // fill allDirectories
        for (Entry<String, Directory> entry : directories.entrySet()) {
            String path = entry.getKey();
            Directory directory = entry.getValue();
            allDirectories.put(path, directory);
        }
        
        for (Entry<String, Directory> entry : directories.entrySet()) {
            String path = entry.getKey();
            Directory directory = entry.getValue();
            String parentPath = getParentPath(path);
            
            boolean parentFound = false;
            while(!parentFound) {
                Directory parent = allDirectories.get(parentPath);
                if (parent == null) {
                    parent = new Directory(parentPath);
                    allDirectories.put(parentPath, parent);
                    // find parent of parent
                    parentPath = getParentPath(parentPath);
                } else {
                    parentFound = true;
                }
                parent.addChild(directory);
                directory = parent;
            }
        }
        
        return root;
    }

    /**
     * Combines FileMetrics to DirectoryMetrics.
     * 
     * @param fileMetrics
     * @return the mapping from directory path -> DirectoryMetrics. The result is still flat, that means that
     * sub-directories are not contained in their parent directories
     */
    private SortedMap<String, Directory> combineFilesInSameDirectories(Collection<File> fileMetrics) {
        SortedMap<String, Directory> directories = new TreeMap<>();
        for (File fileMetric : fileMetrics) {
            String path = getParentPath(fileMetric.getPath());
            Directory directoryMetrics = directories.get(path);
            if (directoryMetrics == null) {
                directoryMetrics = new Directory(path);
                directories.put(path, directoryMetrics);
            }
            directoryMetrics.addFile(fileMetric);
        }
        return directories;
    }

    private String getParentPath(String file) {
        int index = file.lastIndexOf('/');
        if (index == 0) return "/";
        if (index == -1) return "";
        return file.substring(0, index);
    }

}
