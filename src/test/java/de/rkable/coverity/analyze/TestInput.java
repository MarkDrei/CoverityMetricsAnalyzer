package de.rkable.coverity.analyze;

import java.util.Arrays;
import java.util.Collection;

import de.rkable.coverity.metrics.Directory;
import de.rkable.coverity.metrics.File;
import de.rkable.coverity.metrics.Method;
import de.rkable.coverity.metrics.Metrics;
import de.rkable.coverity.metrics.Method.MethodMetricsBuilder;

public class TestInput {
    public static Collection<Directory> getTwoInputs() {
        Arrays.asList(getInputMetricFile1Method1(), getInputMetricFile2Method2());
        return Arrays.asList(getInputMetricFile1Method1(), getInputMetricFile2Method2());
    }
    
    /**
     * one directory, two files, three methods
     * @return
     */
    public static Collection<Directory> getThreeInputs() {
        Arrays.asList(getInputMetricFile1Method1(), getInputMetricFile2Method2());
        return Arrays.asList(getInputMetricFile1Method1(), getInputMetricFile2Method2());
    }

    public static Directory getInputMetricFile1Method1() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.123, 0.02));
        metrics.methodName("MethodName1");
        metrics.fileName("/dir/file1");
        Method method = metrics.build();
        
        File file = new File("file1");
        file.addMethodMetric(method);
        Directory directory = new Directory("dir");
        directory.addFileMetrics(file);
        
        return directory;
    }

    public static Directory getInputMetricFile1Method3() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.3, 0.03));
        metrics.methodName("MethodName3");
        metrics.fileName("/dir/file1");
        Method method = metrics.build();
        
        File file = new File("file1");
        file.addMethodMetric(method);
        Directory directory = new Directory("dir");
        directory.addFileMetrics(file);
        
        return directory;
    }
    
    public static Directory getInputMetricFile2Method2() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.234, 0.04));
        metrics.methodName("MethodName2");
        metrics.fileName("/dir/file2");
        Method method = metrics.build();
        
        File file = new File("file2");
        file.addMethodMetric(method);
        Directory directory = new Directory("dir");
        directory.addFileMetrics(file);
        
        return directory;
    }
}
