package de.rkable.coverity.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.rkable.coverity.metrics.Directory;
import de.rkable.coverity.metrics.File;
import de.rkable.coverity.metrics.Method;

public class SimpleHalsteadAnalyzer implements MetricsAnalyzer {
    
    private final static String HALSTEAD_EFFORT = "Halstead effort";
    private final static String HALSTEAD_ERROR = "Halstead error";
    
    private Method worstMethod;


    @Override
    public void startAnalysis(Collection<Directory> directories) {
        Collection<Method> methodMetrics = new ArrayList<>();
        addAllMethodMetrics(methodMetrics, directories);
        
        for (Method metric : methodMetrics) {
            if (worstMethod == null) {
                worstMethod = metric;
                continue;
            }
            if (worstMethod.getMetrics().halsteadEffort < metric.getMetrics().halsteadEffort) {
                worstMethod = metric;
            }
        }
    }

    private void addAllMethodMetrics(Collection<Method> methodMetrics,
            Collection<Directory> directories) {
        for(Directory dir : directories) {
            addAllMethodMetrics(methodMetrics, dir.getChildren());
            for (File file : dir.getFileMetrics()) {
                for (Method method : file.getMethodMetrics()) {
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
