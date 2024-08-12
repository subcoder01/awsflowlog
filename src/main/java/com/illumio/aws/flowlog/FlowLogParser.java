package com.illumio.aws.flowlog;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses each flow log line and creates a flow record
 * ASSUME: Logs are well formed and do not miss any of these fields in regex
 */
public class FlowLogParser {
    private final static Logger log = Logger.getLogger(FlowLogParser.class.getName());

    private static final Pattern FLOW_LOG_V2_PATTERN = Pattern.compile("(?<version>\\d+)\\s+"
                     + "(?<accountId>\\d+)\\s+"
                     + "(?<interfaceId>\\S+)\\s+"
                     + "(?<srcaddr>\\S+)\\s+"
                     + "(?<dstaddr>\\S+)\\s+"
                     + "(?<srcport>\\d+|-)\\s+"
                     + "(?<dstport>\\d+|-)\\s+"
                     + "(?<protocol>\\d+|-)\\s+"
                     + "(?<packets>\\d+)\\s+"
                     + "(?<bytes>\\d+)\\s+"
                     + "(?<start>\\d+)\\s+"
                     + "(?<end>\\d+)\\s+"
                     + "(?<action>\\S+)\\s+"
                     + "(?<logStatus>\\S+)");

    public static FlowLog parse(String log) {
        Matcher matcher = FLOW_LOG_V2_PATTERN.matcher(log);
        FlowLog flowLog = null;
        if (matcher.matches()) {
            flowLog = new FlowLog(Integer.parseInt(matcher.group("version")),
            Integer.parseInt(matcher.group("dstport")), Integer.parseInt(matcher.group("protocol")));
        }
        return flowLog;
    }
}
