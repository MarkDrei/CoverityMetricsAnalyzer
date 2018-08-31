package de.rkable.coverity.analyze;

import java.util.Collection;

import de.rkable.coverity.metrics.Directory;

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
            //appendIndent(indent);
            sb.append("Directory: ");
            sb.append(directory.getDirectory());
            sb.append(System.lineSeparator());
            
            appendDirectories(directory.getChildren(), indent + 1);
        }
        
    }

    /**
     * append some spaces to the string buileder
     * @param indent
     * @return
     */
    private void appendIndent(int indent) {
        for (int i = 0; i < indent; i++ ) {
            sb.append("  ");
        }
    }

}
