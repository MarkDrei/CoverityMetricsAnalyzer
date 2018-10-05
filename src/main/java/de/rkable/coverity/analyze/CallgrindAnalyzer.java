package de.rkable.coverity.analyze;

import java.util.Collection;

import de.rkable.coverity.metrics.Directory;
import de.rkable.coverity.metrics.File;
import de.rkable.coverity.metrics.Method;
import de.rkable.coverity.metrics.Metrics;

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
        sb.append(System.lineSeparator());
        sb.append("positions: index");
        sb.append(System.lineSeparator());
        sb.append("events: HalsteadEffort HalsteadError");
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        
        appendAllDirectories(inputMetrics);
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
        // The index simulates the line number which is expected in the output 
        for (Method method : methods) {
            sb.append("fl=");
            sb.append(method.getFileName());
            sb.append(System.lineSeparator());
            
            sb.append("fn=");
            sb.append(method.getMethodName());
            sb.append(System.lineSeparator());
            
            Metrics metrics = method.getMetrics();
            sb.append(toFakeInt(metrics.halsteadEffort));
            sb.append(" ");
            sb.append(toFakeInt(metrics.halsteadError));
            sb.append(System.lineSeparator());
            
            sb.append(System.lineSeparator());
        }
    }
    
    /**
     * The format does not support integers. To keep at least some of the precision we multiply the value by 1000 and 
     * cut off the remaining digits behind the colon.
     * 
     * @param d
     * @return
     */
    private int toFakeInt(double d) {
        return (int) (1000 * d);
    }

}
