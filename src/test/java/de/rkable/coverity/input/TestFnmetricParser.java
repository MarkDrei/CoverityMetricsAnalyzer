package de.rkable.coverity.input;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.rkable.coverity.MethodMetrics;

public class TestFnmetricParser {

    @Test
    public void testThatParserParsesGoodLines() throws Exception {
        FnmetricParser parser = new FnmetricParser(
                "<fnmetric>",
                "<file>/path/to/file/File.c</file>",
                "<names><![CDATA[fn:MethodName;]]></names>",
                "<metrics>be:0;fe:9;bl:7;lc:26;on:14;ot:9;cc:4;pce:4;pcs:3;hf:1207.17;hr:0.0574841;ml:1229</metrics>",
                "<coverage>cu:-1;uu:-1;cf:-1;uf:-1</coverage>",
                "<impact>ad:-1;sd:-1;dd:-1;id:-1;md:-1;</impact>",
                "</fnmetric>"
                );
        MethodMetrics metrics = parser.parse();
        assertEquals(1207.17, metrics.getMetrics().halsteadEffort, 0.1);
    }
    
    @Test
    public void testThatWrongNumberOfLines_6_ThrowsExcpetion() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> new FnmetricParser(
                "line1",
                "line2",
                "line3",
                "line4",
                "line5",
                "line6"
                ));
    }
    
    @Test
    public void testThatWrongNumberOfLines_8_ThrowsExcpetion() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> new FnmetricParser(
                "line1",
                "line2",
                "line3",
                "line4",
                "line5",
                "line6",
                "line7",
                "line8"
                ));
    }
    
    @Test
    public void testThatMissingFileThrowsException() throws Exception {
        try {
            new FnmetricParser(
                    "<fnmetric>",
                    "foobar",
                    "<names><![CDATA[fn:MethodName;]]></names>",
                    "<metrics>be:0;fe:9;bl:7;lc:26;on:14;ot:9;cc:4;pce:4;pcs:3;hf:1207.17;hr:0.0574841;ml:1229</metrics>",
                    "<coverage>cu:-1;uu:-1;cf:-1;uf:-1</coverage>",
                    "<impact>ad:-1;sd:-1;dd:-1;id:-1;md:-1;</impact>",
                    "</fnmetric>"
                    ).parse();
            fail("");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("file"));
            assertTrue(e.getMessage().contains("foobar"));
        }
    }
    
    @Test
    public void testThatMissingNamesThrowsException() throws Exception {
        try {
            new FnmetricParser(
                    "<fnmetric>",
                    "<file>/path/to/file/File.c</file>",
                    "foobar",
                    "<metrics>be:0;fe:9;bl:7;lc:26;on:14;ot:9;cc:4;pce:4;pcs:3;hf:1207.17;hr:0.0574841;ml:1229</metrics>",
                    "<coverage>cu:-1;uu:-1;cf:-1;uf:-1</coverage>",
                    "<impact>ad:-1;sd:-1;dd:-1;id:-1;md:-1;</impact>",
                    "</fnmetric>"
                    ).parse();
            fail("");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("names"));
            assertTrue(e.getMessage().contains("foobar"));
        }
    }
    
    @Test
    public void testThatMissingMetricsThrowsException() throws Exception {
        try {
            new FnmetricParser(
                    "<fnmetric>",
                    "<file>/path/to/file/File.c</file>",
                    "<names><![CDATA[fn:MethodName;]]></names>",
                    "foobar",
                    "<coverage>cu:-1;uu:-1;cf:-1;uf:-1</coverage>",
                    "<impact>ad:-1;sd:-1;dd:-1;id:-1;md:-1;</impact>",
                    "</fnmetric>"
                    ).parse();
            fail("");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("metrics"));
            assertTrue(e.getMessage().contains("foobar"));
        }
    }
}
