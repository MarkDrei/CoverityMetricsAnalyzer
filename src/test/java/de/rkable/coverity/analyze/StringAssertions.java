package de.rkable.coverity.analyze;

import static org.junit.jupiter.api.Assertions.*;

public class StringAssertions {

    public static void assertContains(String needle, String haystack) {
        assertTrue(haystack.contains(needle), "\"" + haystack +"\" is exptected to contain \"" + needle + "\"");
    }
}
