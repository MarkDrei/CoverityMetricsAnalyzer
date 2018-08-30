package de.rkable.coverity.analyze;

/**
 * A report which describes the results of a metrics analysis
 *
 */
public interface Report {
    
    /**
     * 
     * @return a human readable representation of the report
     */
    @Override
    String toString();

}
