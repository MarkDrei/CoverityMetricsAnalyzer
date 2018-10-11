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
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.123, 0.02));
        metrics.methodName("MethodName1");
        metrics.fileName("/dir/file1");
        Method method = metrics.build();
        
        File file = new File("/dir/file1");
        file.addMethod(method);
        Directory directory = new Directory("/dir");
        directory.addFile(file);
        
        metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.234, 0.04));
        metrics.methodName("MethodName2");
        metrics.fileName("/dir/file2");
        method = metrics.build();
        
        file = new File("/dir/file2");
        file.addMethod(method);
        directory.addFile(file);
        
        return Arrays.asList(directory);
    }
    
    public static Collection<Directory> getThreeInputs() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.123, 0.02));
        metrics.methodName("MethodName1");
        metrics.fileName("/dir/sub/file1");
        Method method = metrics.build();
        
        metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.3, 0.03));
        metrics.methodName("MethodName3");
        metrics.fileName("/dir/sub/file1");
        Method method3 = metrics.build();
        
        File file = new File("/dir/sub/file1");
        file.addMethod(method);
        file.addMethod(method3);
        Directory sub = new Directory("/dir/sub");
        sub.addFile(file);
        
        metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.234, 0.04));
        metrics.methodName("MethodName2");
        metrics.fileName("/dir/file2");
        method = metrics.build();
        
        file = new File("/dir/file2");
        file.addMethod(method);
        Directory directory = new Directory("/dir/");
        directory.addFile(file);
        directory.addChild(sub);
        
        return Arrays.asList(directory);
    }
    

    public static Directory getInputMetricFile1Method1() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.123, 0.02));
        metrics.methodName("MethodName1");
        metrics.fileName("/dir/file1");
        Method method = metrics.build();
        
        File file = new File("/dir/file1");
        file.addMethod(method);
        Directory directory = new Directory("/dir");
        directory.addFile(file);
        
        return directory;
    }
    
    public static Directory getInputMetricFile2Method2() {
        MethodMetricsBuilder metrics = new MethodMetricsBuilder();
        metrics.metrics(new Metrics(0.234, 0.04));
        metrics.methodName("MethodName2");
        metrics.fileName("/dir/file2");
        Method method = metrics.build();
        
        File file = new File("/dir/file2");
        file.addMethod(method);
        Directory directory = new Directory("/dir");
        directory.addFile(file);
        
        return directory;
    }
}
