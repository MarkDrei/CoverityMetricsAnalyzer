package de.rkable.coverity.analyze;

import java.util.Collection;

import de.rkable.coverity.metrics.Directory;
import de.rkable.coverity.metrics.File;
import de.rkable.coverity.metrics.Method;

/**
 * Analyzer which generates output that can be read by the kcachegrind tool
 */
public class CallgrindAnalyzer implements MetricsAnalyzer {
    
    private StringBuilder sb = new StringBuilder();

    @Override
    public Report getAnalysis() {
        return new Report() {
            @Override
            public String toString() {
                return sb.toString();
            }
        };
    }

    @Override
    public void startAnalysis(Collection<Directory> inputMetrics) {
        sb.append("# callgrind format");
        sb.append("events: HalsteadEffort HalsteadError");
        
        appendAllDirectories(inputMetrics);
        
        sb.append("fl=/dir/file1");
        sb.append("fl=/dir/file2");
        
    }

    private void appendAllDirectories(Collection<Directory> directories) {
        for (Directory directory : directories) {
            appendAllDirectories(directory.getChildren());
            appendAllFiles(directory.getFiles());
        }
    }

    private void appendAllFiles(Collection<File> files) {
        for (File file : files) {
            appendAllMethods(file.getMethods());
        }
    }

    private void appendAllMethods(Collection<Method> methods) {
        
        for (Method method : methods) {
            sb.append(System.lineSeparator());
            sb.append("fl=");
            sb.append(method.getFileName());
            
            sb.append(System.lineSeparator());
            sb.append("fn=");
            sb.append(method.getMethodName());
            
        }
        
        
    }

}
