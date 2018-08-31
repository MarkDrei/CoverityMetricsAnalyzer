package de.rkable.coverity.metrics;

public class FileMetrics {

    private final String fileName;

    public FileMetrics(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFile() {
        return fileName;
    }

}
