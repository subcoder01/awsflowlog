package com.illumio.aws.flowlog;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

// ASSUME: Flow log has only these protocols
public enum Protocol {
    ICMP("icmp", 1),
    IGMP("igmp", 2),
    TCP("tcp", 6),
    UDP("udp", 17),
    UNKNOWN("unknown", 256);

    private final String name;
    private final int num;
    private static final Map<Integer, String> numToNameMap = Arrays.stream(Protocol.values()).collect(Collectors.toUnmodifiableMap(p -> p.getNum(), p -> p.getName()));

    Protocol(String name, int num) {
        this.name = name;
        this.num = num;
    }

    String getName() {
        return this.name;
    }
    
    static String getName(int num) {
        return numToNameMap.getOrDefault(num, UNKNOWN.name);
    }

    int getNum() {
        return this.num;
    }
}
