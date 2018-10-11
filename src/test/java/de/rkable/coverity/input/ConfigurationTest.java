package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class ConfigurationTest {

    @Test
    public void testEmptyConfiguration() {
        Configuration configuration = new Configuration();
        assertTrue(configuration.hasErrors());
    }
    
    @Test
    public void testInputFile() {
        Configuration configuration = new Configuration();
        configuration.setInputFile("/tmp/file");
        assertFalse(configuration.hasErrors());
        assertTrue(configuration.getExcludePatterns().isEmpty());
    }
    
    @Test
    public void testExcludeStrings() {
        Configuration configuration = new Configuration();
        configuration.setInputFile("/tmp/file");
        configuration.addExcludePattern("patternA");
        Set<String> patterns = configuration.getExcludePatterns();
        assertEquals(1, patterns.size());
        assertEquals("patternA", patterns.iterator().next());
        
        assertFalse(configuration.hasErrors());
    }
}
