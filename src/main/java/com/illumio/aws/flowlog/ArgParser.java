package com.illumio.aws.flowlog;

import java.util.Arrays;
import java.util.logging.Logger;

public class ArgParser {
    private final static Logger log = Logger.getLogger(ArgParser.class.getName());
    private final static String LOG_OPT = "--log";
    private final static String LOOKUP_OPT = "--lookup";
    private final static String OUTPUT_OPT = "--output";

    private final String[] args;

    public ArgParser(String[] args) {
        this.args = args;
    }

    public Options parseArguments() {
        if (args.length != 6) {
            throw new IllegalArgumentException("Invalid or unknown arguments - args = " + Arrays.toString(args));
        }

        String logFile = null;
        String lookupFile = null;
        String outputFile = null;
        for (int i=1; i<args.length; i+=2) {
            if (LOG_OPT.equals(args[i-1])) {
                logFile = args[i];
            } else if (LOOKUP_OPT.equals(args[i-1])) {
                lookupFile = args[i];
            } else if (OUTPUT_OPT.equals(args[i-1])) {
                outputFile = args[i];
            } else {
                log.info("unknown param - " + args[i-1]);
                throw new IllegalArgumentException("Invalid or unknown arguments - args = " + Arrays.toString(args));
            }
        }
        if (logFile == null || lookupFile == null || outputFile == null|| logFile.isBlank() || lookupFile.isBlank() || outputFile.isBlank()) {
            throw new IllegalArgumentException("Invalid or unknown arguments - args = " + Arrays.toString(args));
        }
        // log.info("logFile=" + logFile + ", lookupFile=" + lookupFile);
        return new Options(logFile, lookupFile, outputFile);
    }

    public record Options(String logFile, String lookupFile, String outputFile) {
    }
}
