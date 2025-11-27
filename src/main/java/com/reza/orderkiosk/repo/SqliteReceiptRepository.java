package com.reza.orderkiosk.repo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.Instant;

public class SqliteReceiptRepository {
    private final Path dbPath;

    public SqliteReceiptRepository(Path dbPath) {
        this.dbPath = dbPath;
        initDatabase();
    }

    private void initDatabase() {
        try {
            Path dir = dbPath.getParent();
            if (dir != null && !Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute("""
                    CREATE TABLE IF NOT EXISTS receipts (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        customer_name TEXT NOT NULL,
                        subtotal TEXT NOT NULL,
                        tax TEXT NOT NULL,
                        total TEXT NOT NULL,
                        receipt_text TEXT NOT NULL,
                        created_at TEXT NOT NULL
                    )
                    """);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to create database directory", e);
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:" + dbPath.toString();
        return DriverManager.getConnection(url);
    }

    public long save(String customerName, String subtotal, String tax, String total, String receiptText) throws Exception {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (receiptText == null || receiptText.trim().isEmpty()) {
            throw new IllegalArgumentException("Receipt text is required");
        }

        String sql = """
            INSERT INTO receipts (customer_name, subtotal, tax, total, receipt_text, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, customerName);
            pstmt.setString(2, subtotal);
            pstmt.setString(3, tax);
            pstmt.setString(4, total);
            pstmt.setString(5, receiptText);
            pstmt.setString(6, Instant.now().toString());
            
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException("Failed to get receipt ID");
                }
            }
        }
    }
}
