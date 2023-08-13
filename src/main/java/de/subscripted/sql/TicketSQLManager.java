package de.subscripted.sql;

import java.sql.*;

public class TicketSQLManager {
    private static final String DB_URL = "jdbc:sqlite:tickets.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS Claims (" +
                            "ChannelID TEXT PRIMARY KEY, " +
                            "ClaimerID TEXT)"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveClaimInfo(String channelID, String claimerID) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT OR REPLACE INTO Claims (ChannelID, ClaimerID) VALUES (?, ?)")) {
            statement.setString(1, channelID);
            statement.setString(2, claimerID);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows affected."); // Log-Ausgabe
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteClaimInfo(String channelID) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Claims WHERE ChannelID = ?")) {
            statement.setString(1, channelID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getClaimerID(String channelID) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT ClaimerID FROM Claims WHERE ChannelID = ?")) {
            statement.setString(1, channelID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("ClaimerID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}