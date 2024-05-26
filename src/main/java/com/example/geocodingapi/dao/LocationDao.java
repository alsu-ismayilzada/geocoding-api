package com.example.geocodingapi.dao;

public interface LocationDao {
    void insert(double placeId ,double lon, double lat, String name, String displayName);
}
