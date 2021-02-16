# Journal Automation

## Things I learend
* utilizing spreadsheet api
* sending e-mail through pure java program
* connecting java backend to react frontend
* deploying backend and frontend on azure cloud

## Reading Data from Spreadsheet
```java
public class Read {
    private static Sheets sheetsService;    
    private static final String SPREADSHEET_ID = "ksjdhfklsadjfhaskdjfhsjkdfhskajdfh";
    private static final Integer SHEET_ID = 1234567890;
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

    void readData() {
      sheetsService = getSheetsService();
        String range = "congress!A2:F10";
    
        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();
    
        List<List<Object>> values = response.getValues();
    
        if (values == null || values.isEmpty()) {
            System.out.println("No Data Found");
        } else {
            for (List row : values) {
                System.out.printf("%s %s from %s\n", row.get(5), row.get(4), row.get(1));
            }
        }   
    }
}
```

## Apending data to the last row
```java
public class Append {
    void append() {
        sheetsService = getSheetsService();
        List<List<Object>> values = Arrays.asList(
                Arrays.asList(
                        // cell values
                        "This", "was", "added", "from", "code!"
                )
                // additional rows
        );
        ValueRange body = new ValueRange()
                .setValues(values);
        AppendValuesResponse result =
                sheetsService.spreadsheets().values()
                        .append(SPREADSHEET_ID, "congress", body)
                        .setValueInputOption("USER_ENTERED")
                        .setInsertDataOption("INSERT_ROWS")
                        .setIncludeValuesInResponse(true)
                        .execute();
    }
}
```

## Update data
```java
public class Update {
    void append() {
        sheetsService = getSheetsService();
        List<List<Object>> values = Arrays.asList(
                Arrays.asList(
                        // cell values
                        "updated"
                )
                // additional rows
        );
        ValueRange body = new ValueRange()
                .setValues(values);
         UpdateValuesResponse result =
                sheetsService.spreadsheets().values()
                        .update(SPREADSHEET_ID, "C543", body)
                        .setValueInputOption("RAW")
                        .execute();
    }
}
```

## Delete
```java
public class Delete {
    void append() {
        sheetsService = getSheetsService();
        DeleteDimensionRequest deleteDimensionRequest = new DeleteDimensionRequest()
                .setRange(
                        new DimensionRange()
                        .setSheetId(SHEET_ID)
                        .setDimension("ROWS")
                        .setStartIndex(542) // index is 0 indexed so to delete 543 the index would be 542
                );
        List<Request> requests = new ArrayList<>();
        requests.add(new Request().setDeleteDimension(deleteDimensionRequest));

        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        sheetsService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();
    }
}
```