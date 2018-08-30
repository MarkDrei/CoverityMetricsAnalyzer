package de.rkable.coverity.analyze;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rkable.coverity.FileMetrics;
import de.rkable.coverity.MethodMetrics;

/**
 * Maps all metrics to the files to which they belong
 *
 */
public class FileMetricsList {

    public Collection<FileMetrics> generateFileMetrics(List<MethodMetrics> metrics) {
        Map<String, FileMetrics> fileMap = new HashMap<>();
        for (MethodMetrics metric : metrics) {
            // get the right element
            String fileName = metric.getFileName();
            FileMetrics fileMetrics = fileMap.get(fileName);
            if (fileMetrics == null) {
                fileMetrics = new FileMetrics(fileName);
                fileMap.put(fileName, fileMetrics);
            }
        }
        return fileMap.values();
    }

}
