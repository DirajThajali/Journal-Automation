package com.project.journalautomation.services;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.journalautomation.services.Config.*;

@Service
public class SheetsServiceImpl implements SheetsService {

    private static Sheets sheetsService;
    private static final String APPLICATION_NAME = "Journal Entry";


    private Sheets getSheets() {
        NetHttpTransport transport = new NetHttpTransport.Builder().build();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpRequestInitializer httpRequestInitializer = request -> {
            request.setInterceptor(intercepted -> intercepted.getUrl().set("key", getApiKey()));
        };

        return new Sheets.Builder(transport, jsonFactory, httpRequestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Override
    public List<Map<String, Object>> getData(String from, String until) throws IOException {
        sheetsService = getSheets();
        // "journal!A2:F10"
        String range = "journal!" + from + ":" + until;

        ValueRange response = sheetsService.spreadsheets().values()
                .get(getSpreadsheetId(), range)
                .execute();

        List<List<Object>> values = response.getValues();

        List<Map<String, Object>> mapppedValues = new ArrayList<>();

        if (values == null || values.isEmpty()) {
            return null;
        } else {
            for (List<Object> row : values) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < row.size(); i++) {
                    String key = "";
                    if (i == 0) {
                        key = "Date";
                    } else if (i == 1) {
                        key = "Topic";
                    } else {
                        key = "Entry";
                    }
                    map.put(key, row.get(i));

                }
                mapppedValues.add(map);
            }
        }
        return mapppedValues;
    }

    @Override
    public void append() {
        // append last row, col 0: date col 1: topic col 2: entry
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
