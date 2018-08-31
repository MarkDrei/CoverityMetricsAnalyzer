package de.rkable.coverity.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Converts a bunch of file metrics into a hierarchy of directory metrics
 *
 */
public class DirectoryHierarchyGenerator {

    public Collection<DirectoryMetrics> buildHierarchy(Collection<FileMetrics> fileMetrics) {
        SortedMap<String, DirectoryMetrics> directories = combineFilesInSameDirectories(fileMetrics);
        convertToHierarchy(directories);
        
        return directories.values();
    }

    /**
     * Puts all sub-directories into their parent-directories
     * @param directories InOut param
     */
    private void convertToHierarchy(SortedMap<String, DirectoryMetrics> directories) {
        /*
         * Idea: The map is sorted, such that
         * pathA
         * pathA/pathB
         * pathA/pathB/pathC
         * 
         * We walk over all elements, and if the parent of a path matches the predecessor, then we add it as a child
         * and remove it later from the flat hierarchy
         */
        Entry<String, DirectoryMetrics>  previousEntry = null;
        List<String> elementsToRemove = new ArrayList<>();
        for (Entry<String, DirectoryMetrics> entry : directories.entrySet()) {
            String previousPath = previousEntry == null? null : previousEntry.getKey();
            if (getParentPath(entry.getKey()).equals(previousPath)) {
                previousEntry.getValue().addChild(entry.getValue());
                elementsToRemove.add(entry.getKey());
            }
            previousEntry = entry;
        }
        
        for (String s : elementsToRemove) {
            directories.remove(s);
        }
    }

    /**
     * Combines FileMetrics to DirectoryMetrics.
     * 
     * @param fileMetrics
     * @return the mapping from directory path -> DirectoryMetrics. The result is still flat, that means that
     * sub-directories are not contained in their parent directories
     */
    private SortedMap<String, DirectoryMetrics> combineFilesInSameDirectories(Collection<FileMetrics> fileMetrics) {
        SortedMap<String, DirectoryMetrics> directories = new TreeMap<>();
        for (FileMetrics fileMetric : fileMetrics) {
            String path = getParentPath(fileMetric.getFile());
            DirectoryMetrics directoryMetrics = directories.get(path);
            if (directoryMetrics == null) {
                directoryMetrics = new DirectoryMetrics(path);
                directories.put(path, directoryMetrics);
            }
            directoryMetrics.addFileMetrics(fileMetric);
        }
        return directories;
    }

    private String getParentPath(String file) {
        int index = file.lastIndexOf('/');
        if (index == -1) return "";
        return file.substring(0, index);
    }

}
