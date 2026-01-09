package com.example.getlocalhelp.service;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
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

}
