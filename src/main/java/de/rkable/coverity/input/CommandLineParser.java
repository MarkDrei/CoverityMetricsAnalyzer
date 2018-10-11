package de.rkable.coverity.input;

public class CommandLineParser {

    private Configuration configuration;

    public CommandLineParser(String[] strings) {
        configuration = new Configuration();
        
        if (strings.length % 2 != 0) {
            // invalid, end with default configuration which has an error
            return;
        }
        
        for (int i = 0; i < strings.length; i += 2) {
            if ("-i".equals(strings[i])) {
                configuration.setInputFile(strings[i + 1]);
            } else if ("-e".equals(strings[i])) {
                configuration.addExcludePattern(strings[i+1]);
            } else {
                configuration.setError();
            }
        }
    }

    public Configuration getResult() {

        return configuration;
    }

}
