package de.rkable.coverity;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import de.rkable.coverity.analyze.MetricsAnalyzer;
import de.rkable.coverity.analyze.SimpleHalsteadAnalyzer;
import de.rkable.coverity.input.MetricsFileReader;
import de.rkable.coverity.metrics.DirectoryHierarchyGenerator;
import de.rkable.coverity.metrics.DirectoryMetrics;
import de.rkable.coverity.metrics.FileMetrics;
import de.rkable.coverity.metrics.FileMetricsList;
import de.rkable.coverity.metrics.MethodMetrics;

public class MetricAnalyzer {
    
    static MetricsAnalyzer analyzer = new SimpleHalsteadAnalyzer();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Analyzes a metrics file generated by coverity\n"
                    + "Give the file name as a parameter\n");
            return;
        }

        MetricsFileReader fileReader = new MetricsFileReader(args[0]);
        try {
            fileReader.parseFile();
        } catch (IOException e) {
            System.err.println("Failed to parse the given file: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        List<MethodMetrics> metricEntities = fileReader.getMetricEntities();
        FileMetricsList metricsList = new FileMetricsList();
        Collection<FileMetrics> fileMetrics = metricsList.generateFileMetrics(metricEntities);
        DirectoryHierarchyGenerator hierarchyGenerator = new DirectoryHierarchyGenerator();
        Collection<DirectoryMetrics> hierarchy = hierarchyGenerator.buildHierarchy(fileMetrics);
        
        analyzer.startAnalysis(hierarchy);
        System.out.println(analyzer.getAnalysis());
    }

}
