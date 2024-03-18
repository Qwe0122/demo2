package com.example.demo.service;

import com.example.demo.model.ParsedEvents;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TxParser {

    public List<ParsedEvents> getEqualInputs(JsonElement inputJson) {
        List<ParsedEvents> events = new ArrayList<>();
        if (inputJson.getAsJsonObject().get("inputs").getAsJsonArray().size() > 1) {
            log.info("Parse ///////");
            String script;
            JsonArray asJsonArray = inputJson.getAsJsonObject().get("inputs").getAsJsonArray();
            for (int j = 0; j < asJsonArray.size() - 1; j++) {
                script = asJsonArray.get(j).getAsJsonObject().get("script").getAsString();
                String substring1;
                if (script.length() > 150) {
                    substring1 = script.substring(9, 63);
                    for (int k = j + 1; k < asJsonArray.size(); k++) {
                        String nextSplit = asJsonArray.get(k).getAsJsonObject().get("script").getAsString();
                        String substring2;
                        if (nextSplit.length() > 150) {
                            substring2 = nextSplit.substring(9, 63);
                            if (substring1.equals(substring2)) {
                                System.out.println("Parsed: " + substring1);
                                System.out.println(inputJson.getAsJsonObject().get("hash").getAsString());
                                System.out.println(asJsonArray.get(k).getAsJsonObject().get("prev_out").getAsJsonObject().get("addr").getAsString());
                                events.add(new ParsedEvents()
                                        .setTXId(inputJson.getAsJsonObject().get("hash").getAsString())
                                        .setWallet(asJsonArray.get(k).getAsJsonObject().get("prev_out").getAsJsonObject().get("addr").getAsString())
                                        .setScript(script)
                                );
                                break;
                            }
                        }
                    }
                }
            }
        }
        return events.isEmpty() ? Collections.emptyList() : events;
    }
}
