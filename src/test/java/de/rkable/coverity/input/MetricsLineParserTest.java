package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.Metrics;
import de.rkable.coverity.MetricsLineParser;

public class MetricsLineParserTest {
	
	@Test
	public void testThatNonFileLineIsRecognized() {
		MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c");
		assertFalse(parser.isLineWithFile());
		assertThrows(IllegalStateException.class, () -> parser.getFileName());
	}
	
	@Test
	public void testThatFileNameIsRecognized() {
		MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c</file>\n");
		assertTrue(parser.isLineWithFile());
		assertEquals(Paths.get("/path/to/file/File.c"), parser.getFileName());
	}
	
	@Test
	public void testThatIsLineWithFileNameMustNotBeCalledFirst() {
		MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c</file>\n");
		assertEquals(Paths.get("/path/to/file/File.c"), parser.getFileName());
	}
	
	@Test
	public void testThatNonMetricsLineIsRecognized() {
		MetricsLineParser parser = new MetricsLineParser("<file>/path/to/file/File.c");
		assertFalse(parser.isLineWithMetrics());
		assertThrows(IllegalStateException.class, () -> parser.getMetrics());
	}
	
	@Test
	public void testThatLineWithMetricsIsRecognized() {
		MetricsLineParser parser = new MetricsLineParser(
				"<metrics>be:0;"
				+ "fe:9;"
				+ "bl:7;"
				+ "lc:26;"
				+ "on:14;"
				+ "ot:9;"
				+ "cc:4;"
				+ "pce:4;"
				+ "pcs:3;"
				+ "hf:1207.17;"
				+ "hr:0.0574841;"
				+ "ml:1229"
				+ "</metrics>"
				);
		assertTrue(parser.isLineWithMetrics());
		assertEquals(new Metrics(1207.17), parser.getMetrics());
	}

}
