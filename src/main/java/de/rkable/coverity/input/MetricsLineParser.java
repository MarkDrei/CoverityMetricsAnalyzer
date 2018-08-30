package de.rkable.coverity.input;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.rkable.coverity.Metrics;

/**
 * Parser for a single line in the metrics file
 *
 */
public class MetricsLineParser {

    private static abstract class MatcherHandler<T> {

        /**
         * State for each single matcher
         */
        private enum MatcherState {
            NOT_CALLED, MATCHED, NOT_MATCHED,
        }

        private Matcher matcher;
        private MatcherState matcherState = MatcherState.NOT_CALLED;

        MatcherHandler(String line, String regex) {
            Pattern pattern = Pattern.compile(regex);
            matcher = pattern.matcher(line);
        }

        T getParseResult() {
            matchIfNeeded();
            if (matcherState == MatcherState.MATCHED) {
                return convertMatcherToResult(matcher);
            }
            throw new IllegalStateException("Expected value not found");
        }

        abstract T convertMatcherToResult(Matcher matcher2);

        boolean isMatch() {
            matchIfNeeded();
            return matcherState == MatcherState.MATCHED;
        }

        private void matchIfNeeded() {
            if (matcherState == MatcherState.NOT_CALLED) {
                if (matcher.find()) {
                    matcherState = MatcherState.MATCHED;
                } else {
                    matcherState = MatcherState.NOT_MATCHED;
                }
            }
        }
    }

    private static class MetricMatcherHandler extends MatcherHandler<Metrics> {
        private final static String NUMBER = "\\d+(\\.\\d+)?(e\\+\\d+)?";
        /// always adds 3 groups
        private final static String NUMBER_GROUP = "(" + NUMBER + ")"; 

        MetricMatcherHandler(String line) {
            super(line,
                    "<metrics>"
                    + "be:"  + NUMBER_GROUP + ";" // groups 1..3
                    + "fe:"  + NUMBER_GROUP + ";" // groups 4..6
                    + "bl:"  + NUMBER_GROUP + ";" // groups 7..9
                    + "lc:"  + NUMBER_GROUP + ";" // groups 10..12
                    + "on:"  + NUMBER_GROUP + ";" // groups 13..15
                    + "ot:"  + NUMBER_GROUP + ";" // groups 16..18
                    + "cc:"  + NUMBER_GROUP + ";" // groups 19..21
                    + "pce:" + NUMBER_GROUP + ";" // groups 22..24
                    + "pcs:" + NUMBER_GROUP + ";" // groups 25..27
                    + "hf:"  + NUMBER_GROUP + ";" // groups 28..30
                    + "hr:"  + NUMBER_GROUP + ";" // groups 31..33
                    + "ml:" + NUMBER_GROUP
                    + "</metrics>"
                    );
		}

        @Override
        Metrics convertMatcherToResult(Matcher matcher2) {
            return new Metrics(Double.parseDouble(matcher2.group(28)));
        }
    }

    private static class FileMatcherHandler extends MatcherHandler<Path> {

        FileMatcherHandler(String line) {
            super(line, "(<file>)(.+)(</file>)");
        }

        @Override
        Path convertMatcherToResult(Matcher matcher2) {
            return Paths.get(matcher2.group(2));
        }
    }

    private static class MethodNameMatcherHandler extends MatcherHandler<String> {

        MethodNameMatcherHandler(String line) {
            super(line, "<names><!\\[CDATA\\[fn:(.*);\\]\\]></names>");
        }

        @Override
        String convertMatcherToResult(Matcher matcher) {
            return matcher.group(1);
        }

    }

    private MetricMatcherHandler metricMatcherHandler;
    private FileMatcherHandler fileMatcherHandler;
    private MethodNameMatcherHandler methodNameMatcherHandler;

    /**
     * Create a new parser which handles a single line
     * 
     * @param line
     *            The line to parse
     */
    public MetricsLineParser(String line) {
        metricMatcherHandler = new MetricMatcherHandler(line);
        fileMatcherHandler = new FileMatcherHandler(line);
        methodNameMatcherHandler = new MethodNameMatcherHandler(line);
    }

    public boolean isLineWithFile() {
        return fileMatcherHandler.isMatch();
    }

    public Path getFileName() {
        return fileMatcherHandler.getParseResult();
    }

    public boolean isLineWithMetrics() {
        return metricMatcherHandler.isMatch();
    }

    public Metrics getMetrics() {
        return metricMatcherHandler.getParseResult();
    }

    public boolean isLineWithMethodName() {
        return methodNameMatcherHandler.isMatch();
    }

    public String getMethodName() {
        return methodNameMatcherHandler.getParseResult();
    }

}
