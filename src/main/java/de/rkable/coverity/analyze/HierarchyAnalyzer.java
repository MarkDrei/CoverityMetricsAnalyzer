package de.rkable.coverity.analyze;

import java.util.Collection;

import de.rkable.coverity.metrics.Directory;
import de.rkable.coverity.metrics.File;
import de.rkable.coverity.metrics.Method;

/**
 * Creates a report which contains the whole source code hierarchy
 *
 */
public class HierarchyAnalyzer implements MetricsAnalyzer {


    StringBuilder sb = new StringBuilder();
    
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
        sb = new StringBuilder();
        
        int indent = 0;
        appendDirectories(inputMetrics, indent);
    }

    private void appendDirectories(Collection<Directory> directories, int indent) {
        for (Directory directory : directories) {
            appendDirectory(directory);
            
            appendDirectories(directory.getChildren(), indent + 1);
        }
        
    }

    /**
     * Append a single directory to the buffer
     * @param directory
     */
    private void appendDirectory(Directory directory) {
        //appendIndent(indent);
        sb.append("Directory: ");
        sb.append(directory.getDirectory());
        newline();
        
        appendIndent(1);
        sb.append("Highest Halstead Effort: ");
        sb.append(getHighestHalsteadEffort(directory));
        newline();
        
        appendIndent(1);
        sb.append("Highest Halstead Error: ");
        sb.append(getHighestHalsteadError(directory));
        newline();
        
        
        newline();
    }
    
    private double getHighestHalsteadError(Directory directory) {
        double max = 0;
        for (File file : directory.getFiles()) {
            for (Method method : file.getMethods()) {
                max = Math.max(max, method.getMetrics().halsteadError);
            }
        }
        for (Directory child : directory.getChildren()) {
            max = Math.max(max, getHighestHalsteadEffort(child));
        }
        
        return max;
    }

    private double getHighestHalsteadEffort(Directory directory) {
        double max = 0;
        for (File file : directory.getFiles()) {
            for (Method method : file.getMethods()) {
                max = Math.max(max, method.getMetrics().halsteadEffort);
            }
        }
        for (Directory child : directory.getChildren()) {
            max = Math.max(max, getHighestHalsteadEffort(child));
        }
        
        return max;
    }

    private void newline() {
        sb.append(System.lineSeparator());
    }

    /**
     * append some spaces to the string builder
     * @param indent
     * @return
     */
    private void appendIndent(int indent) {
        for (int i = 0; i < indent; i++ ) {
            sb.append("  ");
        }
    }

}
