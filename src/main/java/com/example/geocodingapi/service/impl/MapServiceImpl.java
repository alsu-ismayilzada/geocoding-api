package com.example.geocodingapi.service.impl;

import com.example.geocodingapi.dao.LocationDao;
import com.example.geocodingapi.dao.impl.LocationDaoImpl;
import com.example.geocodingapi.service.MapService;
import com.example.geocodingapi.service.NominatimService;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapServiceImpl implements MapService {

    private final NominatimService nominatimService = new NominatimServiceImpl();
    private final LocationDao locationDao = new LocationDaoImpl();

    @Override
    public String findLocationJsonAsStringByParam(String q){
        String jsonResponse = nominatimService.searchDataByParam(q);
        insertForAuditing(jsonResponse);
        return jsonResponse;
    }

    @Override
    public String reverseLocationJsonAsStringByParams(String lon, String lat){
        String jsonResponse = nominatimService.reverseDataByParams(lon, lat);
        insertLocation(convertJsonStringToJsonObject(jsonResponse));
        return jsonResponse;
    }

    private void insertForAuditing(String jsonResponse) {
        JSONArray results = new JSONArray(jsonResponse);
        for (int i = 0; i < results.length(); i++) {
            insertLocation(results.getJSONObject(i));
        }
    }

    private void insertLocation(JSONObject locationJson){
        double lon = locationJson.getDouble("lon");
        double lat = locationJson.getDouble("lat");
        String name = locationJson.optString("name", "");
        String displayName = locationJson.getString("display_name");
        locationDao.insert(lon, lat, name, displayName);
    }

    private JSONObject convertJsonStringToJsonObject(String jsonString){
        return new JSONObject(jsonString);
    }

}
