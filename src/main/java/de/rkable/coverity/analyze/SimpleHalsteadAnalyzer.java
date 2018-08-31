package de.rkable.coverity.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.rkable.coverity.metrics.DirectoryMetrics;
import de.rkable.coverity.metrics.FileMetrics;
import de.rkable.coverity.metrics.MethodMetrics;

public class SimpleHalsteadAnalyzer implements MetricsAnalyzer {
    
    private final static String HALSTEAD_EFFORT = "Halstead effort";
    private final static String HALSTEAD_ERROR = "Halstead error";
    
    private MethodMetrics worstMethod;


    @Override
    public void startAnalysis(Collection<DirectoryMetrics> directories) {
        Collection<MethodMetrics> methodMetrics = new ArrayList<>();
        addAllMethodMetrics(methodMetrics, directories);
        
        for (MethodMetrics metric : methodMetrics) {
            if (worstMethod == null) {
                worstMethod = metric;
                continue;
            }
            if (worstMethod.getMetrics().halsteadEffort < metric.getMetrics().halsteadEffort) {
                worstMethod = metric;
            }
        }
    }

    private void addAllMethodMetrics(Collection<MethodMetrics> methodMetrics,
            Collection<DirectoryMetrics> directories) {
        for(DirectoryMetrics dir : directories) {
            addAllMethodMetrics(methodMetrics, dir.getChildren());
            for (FileMetrics file : dir.getFileMetrics()) {
                for (MethodMetrics method : file.getMethodMetrics()) {
                    methodMetrics.add(method);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see de.rkable.coverity.analyze.MetricsAnalyzer#getAnalysis()
     */
    @Override
    public Report getAnalysis() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("File path: ");
        sb.append(worstMethod.getFileName());
        sb.append(System.lineSeparator());
        
        sb.append("Method name: ");
        sb.append(worstMethod.getMethodName());
        sb.append(System.lineSeparator());
        
        sb.append(HALSTEAD_EFFORT);
        sb.append(": ");
        sb.append(worstMethod.getMetrics().halsteadEffort);
        sb.append(System.lineSeparator());
        
        sb.append(HALSTEAD_ERROR);
        sb.append(": ");
        sb.append(worstMethod.getMetrics().halsteadError);
        sb.append(System.lineSeparator());
        
        StringReport report = new StringReport(sb.toString());
        
        
        return report;
    }

}
