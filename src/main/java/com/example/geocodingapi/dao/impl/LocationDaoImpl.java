package com.example.geocodingapi.dao.impl;

import com.example.geocodingapi.config.DatabaseConfiguration;
import com.example.geocodingapi.dao.LocationDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LocationDaoImpl implements LocationDao {

    @Override
    public void insert(double placeId, double lon, double lat, String name, String displayName){
        String sql = "INSERT INTO public.locations (place_id, lon, lat, name, display_name) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfiguration.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, placeId);
            pstmt.setDouble(2, lon);
            pstmt.setDouble(3, lat);
            pstmt.setString(4, name);
            pstmt.setString(5, displayName);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
