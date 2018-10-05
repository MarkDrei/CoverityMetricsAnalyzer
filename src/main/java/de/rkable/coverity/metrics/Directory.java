package de.rkable.coverity.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Directory {
    
    private String directory;
    List<Directory> children = new ArrayList<>();
    Collection<File> fileMetrics = new ArrayList<>();

    public Directory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public void addChild(Directory child) {
        children.add(child);
    }

    public List<Directory> getChildren() {
        return children;
    }
    
    public void addFileMetrics(File metric) {
        fileMetrics.add(metric);
    }

    public Collection<File> getFiles() {
        return fileMetrics;
    }

    public Metrics getCombindedMetric() {
        Metrics result = Metrics.createEmptyMetrics();
        for (Directory directory : getChildren()) {
            result = result.combine(directory.getCombindedMetric());
        }
        for (File file : getFiles()) {
            result = result.combine(file.getCombinedMetric());
        }
        return result;
    }

}
