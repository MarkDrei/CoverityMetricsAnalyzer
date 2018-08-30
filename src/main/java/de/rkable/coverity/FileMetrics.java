package de.rkable.coverity;

public class FileMetrics {

    private String fileName;

    public FileMetrics(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFile() {
        return fileName;
    }

}
