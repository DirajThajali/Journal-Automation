package com.project.journalautomation.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SheetsService {

    List<Map<String, Object>> getData(String from, String until) throws IOException;

    void append();

    void update();

    void delete();
}
