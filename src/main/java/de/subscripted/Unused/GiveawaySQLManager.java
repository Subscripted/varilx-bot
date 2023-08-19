package de.subscripted.Unused;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GiveawaySQLManager {
    private Connection connection;

    public GiveawaySQLManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:giveaways.db");
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String qry) throws SQLException {
        ResultSet rs = null;
        Statement st = connection.createStatement();
        rs = st.executeQuery(qry);
        return rs;
    }

    public boolean update(String qry) {
        try {
            Statement st = connection.createStatement();
            int rows = st.executeUpdate(qry);
            st.close();
            return (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createTableIfNotExists() throws SQLException {
        String createGiveawaysTableQuery = "CREATE TABLE IF NOT EXISTS giveaways (id INTEGER PRIMARY KEY AUTOINCREMENT, dcusername VARCHAR(255), giveaway_item TEXT, end_time TIMESTAMP)";
        String createGiveawayUsersTableQuery = "CREATE TABLE IF NOT EXISTS giveaway_users (giveaway_id VARCHAR(22), user_id VARCHAR(22))";

        PreparedStatement statement1 = connection.prepareStatement(createGiveawaysTableQuery);
        statement1.execute();

        PreparedStatement statement2 = connection.prepareStatement(createGiveawayUsersTableQuery);
        statement2.execute();
    }


    public void addUserToGiveaway(String giveawayId, String userId) throws SQLException {
        String query = "INSERT INTO giveaway_users (giveaway_id, user_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, giveawayId);
            statement.setString(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userParticipated(String giveawayId, String userId) throws SQLException {
        String query = "SELECT user_id FROM giveaway_users WHERE giveaway_id = ? AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, giveawayId);
            statement.setString(2, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<String> getParticipants(String giveawayId) throws SQLException {
        List<String> participants = new ArrayList<>();
        String query = "SELECT user_id FROM giveaway_users WHERE giveaway_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, giveawayId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                participants.add(resultSet.getString("user_id"));
            }
        }
        return participants;
    }
    public String createGiveaway(String dcusername, String giveawayItem, long durationInSeconds) throws SQLException {
        String insertQuery = "INSERT INTO giveaways (dcusername, giveaway_item, end_time) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dcusername);
            statement.setString(2, giveawayItem);
            Instant endTime = Instant.now().plusSeconds(durationInSeconds);
            statement.setTimestamp(3, Timestamp.from(endTime));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating giveaway failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getString(1);
                } else {
                    throw new SQLException("Creating giveaway failed, no ID obtained.");
                }
            }
        }
    }

}
