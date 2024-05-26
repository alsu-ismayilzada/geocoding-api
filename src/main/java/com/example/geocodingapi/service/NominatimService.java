package com.example.geocodingapi.service;

public interface NominatimService {
    String searchDataByParam(String q);
    String reverseDataByParams(String lon, String lat);
}
