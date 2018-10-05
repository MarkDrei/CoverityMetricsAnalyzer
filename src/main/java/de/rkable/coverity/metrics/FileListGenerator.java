package de.rkable.coverity.metrics;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps all metrics to the files to which they belong
 *
 */
public class FileListGenerator {

    public Collection<File> generateFileMetrics(List<Method> metrics) {
        Map<String, File> fileMap = new HashMap<>();
        for (Method metric : metrics) {
            // get the right element
            String fileName = metric.getFileName();
            File fileMetrics = fileMap.get(fileName);
            if (fileMetrics == null) {
                fileMetrics = new File(fileName);
                fileMap.put(fileName, fileMetrics);
            }
            fileMetrics.addMethod(metric);
        }
        return fileMap.values();
    }

}
