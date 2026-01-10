package com.example.getlocalhelp.service;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.springframework.stereotype.Service;

@Service

public class GetNearbyMatchService {
    private final DataSource dataSource;

    public GetNearbyMatchService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Double> locations = new ArrayList<>(List.of(234.21, 205.18, 120.23, -20.34));

    public String test(String inputLoc, String range) {
        String outString = "neighbour code nearby: ";
        for (Double loc : locations) {
            if (Math.abs(loc - Double.parseDouble(inputLoc)) < Double.parseDouble(range)) {
                outString += loc.toString() + ", ";
            }
        }
        return outString.substring(0, outString.length() - 2);
    }

    public String getDataFromDB(int roll) {
        // Use dataSource to get data from DB
        System.out.println("Getting data from DB for roll: " + roll);
        String sql = "SELECT name FROM test WHERE roll = ?";
        String name = "Not Found";
        Connection conn;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, roll);
            var rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return name;

    }

    public String saveRequestToDB(String loc_code, String msg, String contact, String duration) {
        String sql = "INSERT INTO t_request (request_text,location_coordinates,contact,request_expiry_duration,tip_amount,delete_flag) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn;
        String Status = "Failed to create request";
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, msg);
            ps.setString(2, loc_code);
            ps.setString(3, contact);
            ps.setString(4, duration);
            ps.setDouble(5, 100);
            ps.setString(6, "N");
            ps.executeUpdate();
            conn.close();
            Status = "Successfully made your request!";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Status;
    }

    public List<Map<String, Object>> searchResult(String loc_code, String contact) {
        String sql = "SELECT * FROM t_request WHERE location_coordinates = ? OR contact = ?";
        Connection conn;
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loc_code);
            ps.setString(2, contact);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("request_text", rs.getString("request_text"));
                row.put("location_coordinates", rs.getString("location_coordinates"));
                row.put("contact", rs.getString("contact"));
                row.put("request_expiry_duration", rs.getString("request_expiry_duration"));
                row.put("tip_amount", rs.getDouble("tip_amount"));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("Search results: " + results);
        return results;
    }
}