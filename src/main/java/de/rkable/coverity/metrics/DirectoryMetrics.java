package de.rkable.coverity.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DirectoryMetrics {
    
    private String directory;
    List<DirectoryMetrics> children = new ArrayList<>();
    Collection<FileMetrics> fileMetrics = new ArrayList<>();

    public DirectoryMetrics(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public void addChild(DirectoryMetrics child) {
        children.add(child);
    }

    public List<DirectoryMetrics> getChildren() {
        return children;
    }
    
    public void addFileMetrics(FileMetrics metric) {
        fileMetrics.add(metric);
    }

    public Collection<FileMetrics> getFileMetrics() {
        return fileMetrics;
    }

}
