package com.illumio.aws.flowlog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class FlowLogProcessor {
    private final static Logger log = Logger.getLogger(FlowLogProcessor.class.getName());
    private final String logFilePath;
    private final int bufferSize;
    private final FlowLogMetrics flowLogMetrics;

    public FlowLogProcessor(String logFilePath, int bufferSize, Map<String, String> lookupTable, String outputFilePath) {
        this.logFilePath = logFilePath;
        this.bufferSize = bufferSize;
        this.flowLogMetrics = new FlowLogMetrics(lookupTable, outputFilePath);
    }

    public void process() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(logFilePath), bufferSize)) {
            String logLine;
            while ((logLine=bufferedReader.readLine()) != null) {
                // log.info(logLine);
                FlowLog flowLog = FlowLogParser.parse(logLine);
                flowLogMetrics.updateTagCount(flowLog);
                flowLogMetrics.updateDestPortProtocolCount(flowLog);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Flow log processing failed!");
        }
        flowLogMetrics.flushMetrics();
    }
}
