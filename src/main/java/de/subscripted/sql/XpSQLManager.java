package de.subscripted.sql;

import java.sql.*;

public class XpSQLManager {
    private Connection connection;

    public XpSQLManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:varilx_level.db");
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

    private boolean userAlreadyExists(String user_id) throws SQLException {
        ResultSet rs = query("SELECT user_id FROM players WHERE user_id = '" + user_id + "'");
        return rs.next();
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS players (user_id VARCHAR(22) PRIMARY KEY, xp INTEGER, level INTEGER)";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    public int getXP(String userId) throws SQLException {
        String query = "SELECT xp FROM players WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("xp");
            } else {
                setXP(userId, 0);
                return 0;
            }
        }
    }

    public void setXP(String userId, int xp) throws SQLException {
        if (!userAlreadyExists(userId)) {
            String query = "INSERT INTO players (user_id, xp) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userId);
                statement.setInt(2, xp);
                statement.executeUpdate();
            }
        } else {
            update("UPDATE players SET xp = " + xp + " WHERE user_id = '" + userId + "'");
        }
    }

    public int getLevel(String userId) throws SQLException {
        String query = "SELECT level FROM players WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("level");
            } else {
                setLevel(userId, 1);
                return 1;
            }
        }
    }

    public void setLevel(String userId, int level) throws SQLException {
        String query = "UPDATE players SET level = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, level);
            statement.setString(2, userId);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void incrementXP(String userId, int amount) throws SQLException {
        int currentXP = getXP(userId);
        int newXP = currentXP + amount;
        setXP(userId, newXP);
    }
}
