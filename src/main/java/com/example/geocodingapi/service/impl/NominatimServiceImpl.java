package com.example.geocodingapi.service.impl;

import com.example.geocodingapi.service.NominatimService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.example.geocodingapi.contant.NominatimConstants.REVERSE_BASE_URL;
import static com.example.geocodingapi.contant.NominatimConstants.SEARCH_BASE_URL;

public class NominatimServiceImpl implements NominatimService {

    @Override
    public String searchDataByParam(String param) {

        String encodedQuery = URLEncoder.encode(param, StandardCharsets.UTF_16);
        String nominatimUrl = SEARCH_BASE_URL + "q=" + encodedQuery;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(nominatimUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed to query Nominatim: HTTP response code " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return response.toString();
    }

    @Override
    public String reverseDataByParams(String lon, String lat) {
        String nominatimUrl = REVERSE_BASE_URL + "lat=" + lat + "&lon=" + lon;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(nominatimUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed to query Nominatim: HTTP response code " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return response.toString();
    }

}
