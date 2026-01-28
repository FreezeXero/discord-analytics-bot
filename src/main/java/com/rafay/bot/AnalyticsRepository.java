package com.rafay.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsRepository {

    public void incrementMessageCount(String userId) {
        try (Connection conn = Db.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO user_stats(user_id, message_count)
                VALUES(?, 1)
                ON CONFLICT(user_id)
                DO UPDATE SET message_count = message_count + 1
            """);

            ps.setString(1, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMessageCount(String userId) {
        try (Connection conn = Db.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT message_count
                FROM user_stats
                WHERE user_id = ?
            """);

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("message_count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // NEW: leaderboard query
    public List<UserCount> getTopUsers(int limit) {
        List<UserCount> results = new ArrayList<>();

        // Safety clamp
        if (limit < 1) limit = 10;
        if (limit > 50) limit = 50;

        try (Connection conn = Db.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT user_id, message_count
                FROM user_stats
                ORDER BY message_count DESC
                LIMIT ?
            """);

            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(new UserCount(rs.getString("user_id"), rs.getInt("message_count")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public static class UserCount {
        public final String userId;
        public final int count;

        public UserCount(String userId, int count) {
            this.userId = userId;
            this.count = count;
        }
    }
}
