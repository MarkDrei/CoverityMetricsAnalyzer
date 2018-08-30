package de.rkable.coverity.analyze;

/**
 * Simple report which is a String
 *
 */
public class StringReport implements Report {
    
    private String report;

    public StringReport(String report) {
        this.report = report;
    }
    
    @Override
    public String toString() {
        return report;
    }

}
