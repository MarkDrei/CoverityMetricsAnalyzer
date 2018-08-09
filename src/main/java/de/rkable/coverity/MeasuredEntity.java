package de.rkable.coverity;

/**
 * An entity which has code metrics associated to it.
 * 
 */
public class MeasuredEntity {

	public Metrics getMetrics() {
		return new Metrics(0);
	}

}
