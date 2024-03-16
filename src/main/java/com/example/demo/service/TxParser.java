package com.example.demo.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TxParser {

    private final CloseableHttpClient httpClient;

    public TxParser() {
        this.httpClient = HttpClients.createDefault();
    }

    public StringBuilder getEqualInputs(List<String> tx) {
        System.out.println("Parse Tx /////////////////////");
        String uri = "https://blockchain.info/rawtx/";
        StringBuilder inputs = new StringBuilder();
        for (String s : tx) {
            System.out.println(inputs.length());
            JsonObject inputJson = getInputJson(uri + s);
            if (inputJson.get("inputs").getAsJsonArray().size() > 1) {
                String script;
                JsonArray asJsonArray = inputJson.get("inputs").getAsJsonArray();
                for (int j = 0; j < asJsonArray.size() - 1; j++) {
                    script = asJsonArray.get(j).getAsJsonObject().get("script").getAsString();
                    String substring1;
                    if (script.length() > 64) {
                        substring1 = script.substring(9, 63);
                        for (int k = j + 1; k < asJsonArray.size(); k++) {
                            String nextSplit = asJsonArray.get(k).getAsJsonObject().get("script").getAsString();
                            String substring2;
                            if (nextSplit.length() > 64) {
                                substring2 = nextSplit.substring(9, 63);
                                if (substring1.equals(substring2)) {
                                    System.out.println("Parsed: " + substring1);
                                    inputs.append("Wallet: ")
                                            .append(asJsonArray.get(k).getAsJsonObject().get("prev_out").getAsJsonObject().get("addr").getAsString()).append(script)
                                            .append("\n")
                                            .append(script)
                                            .append("\n" + "/////////////////////////")
                                            .append("\n");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return inputs;
    }


    public JsonObject getInputJson(String uri) {
        while (true) {
            try {
                URIBuilder uriBuilder = new URIBuilder(uri);

                HttpGet get = new HttpGet(uriBuilder.build());
                get.setHeader("X-Ratelimit-Remaining", "e5c11cb2da6a48d8b730b892f48b4314");

                try (CloseableHttpResponse response = httpClient.execute(get)) {
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 429) {
                        System.out.println("Sleep time: " + TimeUnit.HOURS.toMillis(1));
                        System.out.println("Continue in: " + LocalDateTime.now().plusHours(1));
                        Thread.sleep(TimeUnit.HOURS.toMillis(1));
                        continue;
                    }

                    if (statusCode != HttpStatus.SC_OK) {
                        throw new HttpResponseException(statusCode, "HTTP request failed");
                    }

                    return JsonParser.parseReader(new InputStreamReader(response.getEntity().getContent())).getAsJsonObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
