package de.rkable.coverity.analyze;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rkable.coverity.FileMetrics;
import de.rkable.coverity.MethodMetrics;

public class FileHierarchy {
    
    Map<String, FileMetrics> fileMap = new HashMap<>();

    public Collection<FileMetrics> buildHierarchy(List<MethodMetrics> metrics) {
        for (MethodMetrics metric : metrics) {
            // get the right element
            String fileName = metric.getFileName();
            FileMetrics fileMetrics = fileMap.get(fileName);
            if (fileMetrics == null) {
                fileMetrics = new FileMetrics();
                fileMap.put(fileName, fileMetrics);
            }
        }
        return fileMap.values();
    }

}
