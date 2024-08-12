package com.illumio.aws.flowlog;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import com.illumio.aws.flowlog.ArgParser.Options;

public class Main {
    private final static Logger log = Logger.getLogger(Main.class.getName());
    private final static int bufferSize = 4096;

    public static void main(String[] args) {
        log.info("Welcome to AWS Flow log analyzer! - args = " + Arrays.toString(args));
        log.info("Sample command:\n >java -jar .\\target\\awsflowlog-1.0-SNAPSHOT.jar --log .\\src\\main\\resources\\flowlog.txt --lookup .\\src\\main\\resources\\lookuptable.txt");
        Options options = new ArgParser(args).parseArguments();
        log.info("Options = " + options);
        LookupTableProcessor lookupTableProcessor = new LookupTableProcessor(options.lookupFile());
        FlowLogProcessor flowLogProcessor = new FlowLogProcessor(options.logFile(), bufferSize, lookupTableProcessor.getLookup(), options.outputFile());
        flowLogProcessor.process();
    }
}