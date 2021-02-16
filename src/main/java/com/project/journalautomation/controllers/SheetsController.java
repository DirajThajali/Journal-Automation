package com.project.journalautomation.controllers;

import com.project.journalautomation.services.SheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@RestController
public class SheetsController {

    @Autowired
    private SheetsService sheetService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping({"", "/"})
    public List<Map<String, Object>> showData() throws IOException, GeneralSecurityException {
        return sheetService.getData("A2", "C3");
    }
}
