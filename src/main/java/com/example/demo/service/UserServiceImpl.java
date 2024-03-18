package com.example.demo.service;

import com.example.demo.model.Block;
import com.example.demo.repository.BlockRepository;
import com.example.demo.repository.ParsedEventsRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final ParsedEventsRepository eventsRepository;
    private final BlockRepository blockRepository;


    private final String uri = "https://blockchain.info/rawblock/";
    private static final CloseableHttpClient httpClient;

    static {
        httpClient = HttpClients.createDefault();
    }


    public void getEvents() {
        Block block1 = blockRepository.findById(1L).get();
        String nextUrl = uri + block1.getLastBlock();
        while (nextUrl != null) {
            JsonObject event = getEventResponseSc(nextUrl);
            JsonArray jsonElement = event.getAsJsonArray("tx");
            for (JsonElement element : jsonElement) {
                eventsRepository.saveAll(new TxParser().getEqualInputs(element));
            }
            if (!event.get("next_block").getAsJsonArray().get(0).getAsString().isEmpty() || event.get("next_block").getAsJsonArray().get(0).getAsString() != null) {
                nextUrl = uri + event.get("next_block").getAsJsonArray().get(0).getAsString();
                block1.setLastBlock(event.get("next_block").getAsJsonArray().get(0).getAsString());
                blockRepository.save(block1);
            } else {
                nextUrl = null;
            }
        }
    }

    public JsonObject getEventResponseSc(String uri) {
        while (true) {
            try {
                URIBuilder uriBuilder = new URIBuilder(uri);

                HttpGet get = new HttpGet(uriBuilder.build());

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
