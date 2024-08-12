package com.illumio.aws.flowlog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LookupTableProcessor {
    // 25,tcp -> sv_P1
    private final Map<String, String> lookup = new HashMap<>();
    private final String lookupFilePath;

    public LookupTableProcessor(String lookupFilePath) {
        this.lookupFilePath = lookupFilePath;
        process();
    }

    private void process() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.lookupFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                lookup.put(s[0].toLowerCase().trim() + "," + s[1].toLowerCase().trim(), s[2].toLowerCase().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lookup table processing failed!");
        }
    }

    public Map<String, String> getLookup() {
        return lookup;
    }
}
