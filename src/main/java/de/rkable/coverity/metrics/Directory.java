package de.rkable.coverity.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Directory {
    
    private String path;
    List<Directory> children = new ArrayList<>();
    Collection<File> files = new ArrayList<>();

    public Directory(String directory) {
        if (!directory.startsWith("/")) {
            throw new IllegalArgumentException("Directories must be absolute paths (start with '/')");
        }
        this.path = directory;
    }

    public String getPath() {
        return path;
    }
    
    public void addChild(Directory child) {
        children.add(child);
    }

    public List<Directory> getChildren() {
        return children;
    }
    
    public void addFile(File file) {
        files.add(file);
    }

    public Collection<File> getFiles() {
        return files;
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

    public String printHierarchy() {
        StringBuilder sb = new StringBuilder();
        appendDirectory(sb, 0, this);
        
        return sb.toString();
    }
    
    private void appendDirectory(StringBuilder sb, int indent, Directory directory) {
        addIndent(sb, indent);
        sb.append("directory: ");
        sb.append(directory.getPath());
        sb.append(System.lineSeparator());
        
        for (File file : directory.getFiles()) {
            addIndent(sb, indent + 2);
            sb.append("file: ");
            sb.append(file.getPath());
            sb.append(System.lineSeparator());
        }

        for (Directory child : directory.getChildren()) {
            appendDirectory(sb, indent + 4, child);
        }
    }
    
    private void addIndent(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; ++i) {
            sb.append(" ");
        }
    }

}
