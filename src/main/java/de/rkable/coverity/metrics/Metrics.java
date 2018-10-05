package de.rkable.coverity.metrics;

/**
 * Metrics for a single method
 *
 */
public class Metrics {

    final public double halsteadEffort;
    final public double halsteadError;

    public Metrics(double halsteadEffort, double halsteadError) {
        this.halsteadEffort = halsteadEffort;
        this.halsteadError = halsteadError;
    }

    @Override
    public String toString() {
        return "Metrics [halsteadEffort=" + halsteadEffort + "; halsteadError=" + halsteadError + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(halsteadEffort);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(halsteadError);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Metrics other = (Metrics) obj;
        if (Double.doubleToLongBits(halsteadEffort) != Double.doubleToLongBits(other.halsteadEffort))
            return false;
        if (Double.doubleToLongBits(halsteadError) != Double.doubleToLongBits(other.halsteadError))
            return false;
        return true;
    }

    public Metrics combine(Metrics metrics) {
        return new Metrics(metrics.halsteadEffort + halsteadEffort, metrics.halsteadError + halsteadError);
    }

    public static Metrics createEmptyMetrics() {
        return new Metrics(0, 0);
    }

}
