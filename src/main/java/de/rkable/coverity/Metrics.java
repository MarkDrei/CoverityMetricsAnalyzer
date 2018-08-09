package de.rkable.coverity;

public class Metrics {

	final public double halsteadEffort;

	public Metrics(double halsteadEffort) {
		this.halsteadEffort = halsteadEffort;
	}

	@Override
	public String toString() {
		return "Metrics [halsteadEffort=" + halsteadEffort + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(halsteadEffort);
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
		return true;
	}
	
	
}
