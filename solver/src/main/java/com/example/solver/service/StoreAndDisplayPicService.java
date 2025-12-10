package com.example.solver.service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreAndDisplayPicService {
    private final DataSource dataSource;

    public StoreAndDisplayPicService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        // Create table if it doesn't exist
        String sql = """
                    CREATE TABLE IF NOT EXISTS pictures (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        pic_date TIMESTAMP,
                        pic_location VARCHAR(255) ,
                        image BYTEA NOT NULL
                    )
                """;

        try (Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePicture(String title, Timestamp picDate, String picLocation, byte[] image) {
        String sql = "INSERT INTO pictures (title, pic_date, pic_location, image) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setTimestamp(2, picDate);
            ps.setString(3, picLocation);
            ps.setBytes(4, image);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getAllPictures() {
        List<Map<String, Object>> pictures = new ArrayList<>();
        String sql = "SELECT id, title, pic_date, pic_location, image FROM pictures";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> picData = new HashMap<>();
                picData.put("id", rs.getLong("id"));
                picData.put("title", rs.getString("title"));
                picData.put("pic_date", rs.getTimestamp("pic_date"));
                picData.put("pic_location", rs.getString("pic_location"));
                // picData.put("image", rs.getBytes("image"));
                byte[] img = rs.getBytes("image");
                picData.put("imageBase64", Base64.getEncoder().encodeToString(img));

                pictures.add(picData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pictures;
    }
}
