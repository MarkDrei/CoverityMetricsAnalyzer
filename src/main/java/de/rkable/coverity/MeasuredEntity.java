package de.rkable.coverity;

/**
 * An entity which has code metrics associated to it.
 * 
 * @author mdreiuck
 *
 */
public class MeasuredEntity {

	public Metrics getMetrics() {
		return new Metrics(0);
	}

}
