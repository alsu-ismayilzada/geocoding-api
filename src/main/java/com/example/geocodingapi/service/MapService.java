package com.example.geocodingapi.service;

public interface MapService {
    String findLocationJsonAsStringByParam(String parameter);

    String reverseLocationJsonAsStringByParams(String lon, String lat);
}
