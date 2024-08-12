package com.illumio.aws.flowlog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class FlowLogMetrics {
    private static final Logger log = Logger.getLogger(FlowLogMetrics.class.getName());
    private static final String UNTAGGED = "untagged";
    private final Map<String, Integer> tagCount = new HashMap<>();
    private final Map<String, Integer> destPortProtocolCount = new HashMap<>();
    private final Map<String, String> lookupTable;
    private final String outputFilePath;

    public FlowLogMetrics(Map<String, String> lookupTable, String outputFilePath) {
        this.lookupTable = lookupTable;
        this.outputFilePath = outputFilePath;
    }

    public void updateTagCount(FlowLog flowLog) {
        if (flowLog != null) {
            tagCount.compute(lookupTable.getOrDefault(getKey(flowLog), UNTAGGED), (k, v) -> {
                if (v == null) {
                    v = 0;
                }
                return ++v;
            });
        }
    }

    public void updateDestPortProtocolCount(FlowLog flowLog) {
        if (flowLog != null) {
            destPortProtocolCount.compute(getKey(flowLog), (k,v) -> {
                if (v == null) {
                    v = 0;
                }
                return ++v;
            });
        }
    }

    public void flushMetrics() {
        // log.info("TAG COUNT");
        // tagCount.entrySet().forEach(e -> log.info(e.getKey() + "," + e.getValue()));
        // log.info("DEST PORT PROTO COUNT");
        // destPortProtocolCount.entrySet().forEach(e -> log.info(e.getKey() + "," + e.getValue()));
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            flushTagCount(writer);
            flushDestPortProtocolCount(writer);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Error writing mettrics");
        }
    }

    private void flushTagCount(BufferedWriter writer) throws IOException {
        writer.write("TAG,Count");
        writer.newLine();
        for (Map.Entry<String, Integer> e : tagCount.entrySet()) {
            writer.write(e.getKey() + "," + e.getValue());
            writer.newLine();
        }
        writer.newLine();
    }
    
    private void flushDestPortProtocolCount(BufferedWriter writer) throws IOException {
        writer.write("Port,Protocol,Count");
        writer.newLine();
        for (Map.Entry<String, Integer> e : destPortProtocolCount.entrySet()) {
            writer.write(e.getKey() + "," + e.getValue());
            writer.newLine();
        }
        writer.newLine();
    }

    private String getKey(FlowLog flowLog) {
        return flowLog.destPort() + "," + Protocol.getName(flowLog.protocol());
    }
}
