package com.rafay.bot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Db {

    private static final String URL = "jdbc:sqlite:analytics.db";

    static {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Stores total message counts per user (simple + fast)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS user_stats (
                    user_id TEXT PRIMARY KEY,
                    message_count INTEGER NOT NULL
                )
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }
}


