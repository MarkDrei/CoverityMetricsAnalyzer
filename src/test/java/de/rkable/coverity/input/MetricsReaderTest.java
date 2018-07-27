package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.Metrics;
import de.rkable.coverity.reader.MetricsReader;

public class MetricsReaderTest {

	@Test
	public void readSingleElement() {
		MetricsReader reader = new MetricsReader("resources/MetricsSingle.xml");
		List<Metrics> metrics = reader.getMetrics();
		assertNull(metrics);
		
		reader.parseFile();
		metrics = reader.getMetrics();
		assertNotNull(metrics);
	}
}
