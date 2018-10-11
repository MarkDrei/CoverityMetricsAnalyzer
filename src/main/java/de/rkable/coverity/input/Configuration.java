package de.rkable.coverity.input;

import java.util.HashSet;
import java.util.Set;

public class Configuration {

    private String intputFile;
    private HashSet<String> excludePatterns = new HashSet<>();
    private boolean hasError = false;

    public boolean hasErrors() {
        return hasError || intputFile == null;
    }

    public void setInputFile(String intputFile) {
        this.intputFile = intputFile;
    }

    public void addExcludePattern(String pattern) {
        excludePatterns.add(pattern);
    }

    public Set<String> getExcludePatterns() {
        return excludePatterns;
    }
    
    public void setError() {
        hasError = true;
    }

    public String getInputFile() {
        return intputFile;
    }

}
