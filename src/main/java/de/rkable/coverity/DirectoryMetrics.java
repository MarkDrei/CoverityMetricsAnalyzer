package de.rkable.coverity;

import java.util.ArrayList;
import java.util.List;

public class DirectoryMetrics {
    
    private String directory;
    List<DirectoryMetrics> children = new ArrayList<>();

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

}
