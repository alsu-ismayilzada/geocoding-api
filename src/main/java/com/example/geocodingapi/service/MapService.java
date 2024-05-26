package com.example.geocodingapi.service;

public interface MapService {
    String searchLocationJsonAsStringByParam(String parameter);

    String reverseLocationJsonAsStringByParams(String lon, String lat);
}
