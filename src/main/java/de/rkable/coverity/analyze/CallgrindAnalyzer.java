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
        appendNewline();
        sb.append("positions: index");
        appendNewline();
        sb.append("events: HalsteadEffort HalsteadError");
        appendNewline();
        appendNewline();
        
        appendAllDirectories(inputMetrics);
    }

    private void appendAllDirectories(Collection<Directory> directories) {
        for (Directory directory : directories) {
            appendDirectoryReference(directory, false);
            appendMetrics(null);
            
            appendCallsToSubDirectories(directory);
            appendCallsToFiles(directory);
            appendAllFiles(directory.getFiles());
            appendAllDirectories(directory.getChildren());
        }
    }

    private void appendCallsToSubDirectories(Directory directory) {
        for (Directory child : directory.getChildren()) {
            appendDirectoryReference(child, true);
            appendCallStatement();
            appendCumulatedMetrics(child);
        }
    }

    private void appendCallsToFiles(Directory directory) {
        for (File file : directory.getFiles()) {
            appendFileReference(file, true);
            appendCallStatement();
            appendCumulatedMetrics(file);
        }
        appendNewline();
    }
    
    private void appendCumulatedMetrics(Directory directory) {
        appendMetrics(directory.getCombindedMetric());
    }
    
    private void appendCumulatedMetrics(File file) {
        appendMetrics(file.getCombinedMetric());
    }

    private void appendAllFiles(Collection<File> files) {
        for (File file : files) {
            appendFileCallHierarchy(file);
            appendAllMethods(file.getMethods());
        }
    }

    private void appendFileCallHierarchy(File file) {
        appendFileReference(file, false);
        appendMetrics(null);
        
        for (Method method : file.getMethods()) {
            appendMethodReference(method, true);
            appendCallStatement();
            appendMetrics(method.getMetrics());
        }
        appendNewline();
    }
    
    private void appendAllMethods(Collection<Method> methods) {
        for (Method method : methods) {
            appendFilePathReference(method.getFileName());
            
            appendMethodReference(method, false);
            
            appendMetrics(method.getMetrics());
            
            appendNewline();
        }
    }

    private void appendDirectoryReference(Directory directory, boolean isCallReference) {
        if(isCallReference) {
            sb.append("cfi=");
        } else {
            sb.append("fl=");
        }
        sb.append(directory.getPath());
        appendNewline();
        
        if(isCallReference) {
            sb.append("c");
        }
        sb.append("fn=directory ");
        sb.append(directory.getPath());
        appendNewline();
    }

    private void appendFileReference(File file, boolean isCallReference) {
        if (isCallReference) {
            sb.append("cfi");
        } else {
            sb.append("fl");
        }
        sb.append("=");
        sb.append(file.getPath());
        appendNewline();
        
        if (isCallReference) {
            sb.append('c');
        } 
        sb.append("fn=file ");
        sb.append(file.getFileName());
        appendNewline();
    }
    
    private void appendCallStatement() {
        sb.append("calls=1");
        appendNewline();
    }

    private void appendFilePathReference(String path) {
        sb.append("fl=");
        sb.append(path);
        appendNewline();
    }
    
    private void appendMetrics(Metrics metrics) {
        if (metrics != null) {
            sb.append(toFakeInt(metrics.halsteadEffort));
            sb.append(" ");
            sb.append(toFakeInt(metrics.halsteadError));
        } else {
            sb.append("0 0");
        }
        appendNewline();
    }
    
    /**
     * 
     * @param isCallReference <code>true</code> to append a reference for a call, <code>false</code>
     * to append a direct reference
     */
    private void appendMethodReference(Method method, boolean isCallReference) {
        if (isCallReference)
        {
            sb.append('c');
        }
        sb.append("fn=method ");
        sb.append(method.getMethodName());
        appendNewline();
    }
    
    private void appendNewline() {
        sb.append(System.lineSeparator());
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
