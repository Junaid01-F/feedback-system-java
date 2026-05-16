package com.feedback.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsService {

    private static final String SPREADSHEET_ID = "103wQcVvsGTKFPjqVbGC7OE7GSVzvRH756k1dbqu2J98";

    public void appendData(String name, String email, String rating, String comments) throws Exception {
        System.out.println("🔥 GOOGLE SHEETS SERVICE STARTED");

        try (InputStream stream = new ClassPathResource("credentials.json").getInputStream()) {

            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

            Sheets sheets = new Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials)
            )
                    .setApplicationName("Feedback App")
                    .build();

            List<Object> row = Arrays.asList(
                    name,
                    email,
                    rating,
                    comments,
                    LocalDateTime.now().toString()
            );

            ValueRange body = new ValueRange().setValues(List.of(row));
            System.out.println("Writing row: " + row);

            sheets.spreadsheets().values()
                    .append(SPREADSHEET_ID, "Sheet1!A:E", body)
                    .setValueInputOption("RAW")
                    .execute();

            System.out.println("✅ Data successfully written to Google Sheet");
        }
    }
}