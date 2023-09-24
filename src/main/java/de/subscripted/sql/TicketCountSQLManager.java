package de.subscripted.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketCountSQLManager {
    private static final String DB_URL = "jdbc:sqlite:ticketcount.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Tickets (" +
                             "UserID TEXT PRIMARY KEY, " +
                             "TicketCount INT)"
             )) {
            statement.execute();
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

    public static void addCount(String userId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT OR REPLACE INTO Tickets (UserID, TicketCount) VALUES (?, COALESCE((SELECT TicketCount FROM Tickets WHERE UserID = ?), 0) + 1)"
             )) {
            statement.setString(1, userId);
            statement.setString(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCount(String userId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Tickets SET TicketCount = COALESCE((SELECT TicketCount FROM Tickets WHERE UserID = ?), 0) - 1 WHERE UserID = ?"
             )) {
            statement.setString(1, userId);
            statement.setString(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCount(String userId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT TicketCount FROM Tickets WHERE UserID = ?"
             )) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("TicketCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
