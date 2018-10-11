package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CommandLineParserTest {

    @Test
    public void testNoParameters() {
        CommandLineParser parser = new CommandLineParser(new String[] {});
        Configuration config = parser.getResult();
        assertTrue(config.hasErrors());
    }
    
    @Test
    public void testWrongNumberOfParameters() {
        CommandLineParser parser = new CommandLineParser(new String[] {"-i"});
        Configuration config = parser.getResult();
        assertTrue(config.hasErrors());
    }
    
    @Test
    public void testWrongNumberOfParameters2() {
        CommandLineParser parser = new CommandLineParser(new String[] {"-i", "/tmp/file", "-d"});
        Configuration config = parser.getResult();
        assertTrue(config.hasErrors());
    }
    
    @Test
    public void testInvalidParameter() {
        CommandLineParser parser = new CommandLineParser(new String[] {"-i", "/tmp/file", "-a", "/tmp/file",});
        Configuration config = parser.getResult();
        assertTrue(config.hasErrors());
    }
    
    @Test
    public void testFileOnly() {
        CommandLineParser parser = new CommandLineParser(new String[] {"-i", "/tmp/file"});
        Configuration config = parser.getResult();
        assertFalse(config.hasErrors());
        assertEquals("/tmp/file", config.getInputFile());
    }
    
    @Test
    public void testFileAndExcludes() {
        CommandLineParser parser = new CommandLineParser(new String[] {"-i", "/tmp/file", "-e", "foo"});
        Configuration config = parser.getResult();
        assertFalse(config.hasErrors());
        assertEquals(1, config.getExcludePatterns().size());
        assertEquals("foo", config.getExcludePatterns().iterator().next());
    }
    
    @Test
    public void testFileAndTwoExcludes() {
        CommandLineParser parser = new CommandLineParser(new String[] {"-e", "foo", "-i", "/tmp/file", "-e", "bar"});
        Configuration config = parser.getResult();
        assertFalse(config.hasErrors());
        assertEquals(2, config.getExcludePatterns().size());
        assertTrue(config.getExcludePatterns().contains("foo"));
        assertTrue(config.getExcludePatterns().contains("bar"));
    }
}
