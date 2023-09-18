package de.subscripted.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarnSQLManager {
    private Connection connection;

    public WarnSQLManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:varilx_warns.db");
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String qry) throws SQLException {
        ResultSet rs = null;
        PreparedStatement st = connection.prepareStatement(qry);
        rs = st.executeQuery();
        return rs;
    }

    public boolean update(String qry) {
        try {
            PreparedStatement st = connection.prepareStatement(qry);
            int rows = st.executeUpdate();
            st.close();
            return (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS warnings (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id VARCHAR(22), reason VARCHAR(1000))";
        PreparedStatement statement = connection.prepareStatement(createTableQuery);
        statement.execute();
    }

    public String getReasons(String userId) throws SQLException {
        String query = "SELECT reason FROM warnings WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<String> reasons = new ArrayList<>();
            while (resultSet.next()) {
                reasons.add(resultSet.getString("reason"));
            }
            if (reasons.isEmpty()){
                return noWarns();
            }
            return String.join("\n", reasons);
        }
    }

    public boolean clearReasons(String userId) throws SQLException {
        String query = "DELETE FROM warnings WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }


    public void setReason(String userId, String reason) throws SQLException {
        String query = "INSERT INTO warnings (user_id, reason) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            statement.setString(2, reason);
            statement.executeUpdate();
        }
    }

    public String noWarns() {
        return "Keine Warns";
    }
}
