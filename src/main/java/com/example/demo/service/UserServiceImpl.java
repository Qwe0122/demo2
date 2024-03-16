package com.example.demo.service;

import com.google.gson.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl {

    private String uri = "https://api.blockcypher.com/v1/btc/main/blocks/00000000000000000003dc20b868d17121303308f6bba329302e75913f0790db";
    private String limit = "?txstart=0&limit=2000";
    private final CloseableHttpClient httpClient;

    public UserServiceImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    public List<String> getEvents() {
        List<String> list = new ArrayList<>();
        String nextUrl = uri+limit;

        while (nextUrl != null) {
            System.out.println(list.size());
            JsonObject event = null;
            event = getEventResponseSc(nextUrl);

            JsonArray jsonElement = event.getAsJsonArray("txids");
            for (JsonElement element : jsonElement) {
                if (list.size() >= 2000) {
                    System.out.println("Last Uri " + nextUrl);
                    System.out.println(event.get("prev_block_url").getAsJsonPrimitive().getAsString());
                    return list;
//                    nextUrl = event.get("prev_block_url").getAsJsonPrimitive().getAsString();
                }
                list.add(element.getAsString());
            }
            if (!event.get("next_txids").getAsJsonPrimitive().getAsString().isEmpty() || event.get("next_txids").getAsJsonPrimitive().getAsString() != null) {
                nextUrl = event.get("next_txids").getAsJsonPrimitive().getAsString();
            } else if (!event.get("prev_block_url").getAsJsonPrimitive().getAsString().isEmpty() || event.get("prev_block_url").getAsJsonPrimitive().getAsString() != null){
                nextUrl = event.get("prev_block_url").getAsJsonPrimitive().getAsString();
            }  else {
                nextUrl = null;
            }
        }
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    public JsonObject getEventResponseSc(String uri) {
        while (true) {
            try {
                URIBuilder uriBuilder = new URIBuilder(uri);

                HttpGet get = new HttpGet(uriBuilder.build());
                get.setHeader("X-Ratelimit-Remaining","0b6c36c61250419fb0ffc3125da3850d");

                try (CloseableHttpResponse response = httpClient.execute(get)) {
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 429) {
                        System.out.println("Sleep time: " + TimeUnit.HOURS.toMillis(1));
                        System.out.println("Continue in: " + LocalDateTime.now().plusHours(1));
                        Thread.sleep(TimeUnit.HOURS.toMillis(1));
                        getEventResponseSc(uri);
                    }

                    return JsonParser.parseReader(new InputStreamReader(response.getEntity().getContent())).getAsJsonObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
