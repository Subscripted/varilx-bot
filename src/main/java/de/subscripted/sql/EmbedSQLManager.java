package de.subscripted.sql;

import java.sql.*;

public class EmbedSQLManager {
    private Connection connection;

    public EmbedSQLManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:varilxembed.db");
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

    private boolean embedKeyExists(String code, String key) throws SQLException {
        ResultSet rs = query("SELECT key FROM embeds WHERE code = '" + code + "' AND key = '" + key + "'");
        return rs.next();
    }

    private void createTableIfNotExists() throws SQLException {
        String createEmbedsTableQuery = "CREATE TABLE IF NOT EXISTS embeds (code VARCHAR(50), key VARCHAR(50), data TEXT)";
        String createEmbedCodesTableQuery = "CREATE TABLE IF NOT EXISTS embed_codes (code VARCHAR(50) PRIMARY KEY)";

        Statement statement = connection.createStatement();
        statement.execute(createEmbedsTableQuery);
        statement.execute(createEmbedCodesTableQuery);
    }

    public String getEmbedData(String code) throws SQLException {
        String query = "SELECT data FROM embeds WHERE code = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("data");
            }
        }
        return null;
    }

    public void saveEmbedData(String code, String key, String embedData) throws SQLException {
        if (!embedKeyExists(code, key)) {
            String insertQuery = "INSERT INTO embeds (code, key, data) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, code);
                statement.setString(2, key);
                statement.setString(3, embedData);
                statement.executeUpdate();
            }
        } else {
            String updateQuery = "UPDATE embeds SET data = ? WHERE code = ? AND key = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, embedData);
                statement.setString(2, code);
                statement.setString(3, key);
                statement.executeUpdate();
            }
        }
    }

    public void saveEmbedCode(String code) throws SQLException {
        String insertQuery = "INSERT INTO embed_codes (code) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, code);
            statement.executeUpdate();
        }
    }

    public String getEmbedCode() throws SQLException {
        String query = "SELECT code FROM embed_codes";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("code");
            }
        }
        return null;
    }
    public String getEmbedValueByKey(String code, String key) throws SQLException {
        String query = "SELECT data FROM embeds WHERE code = ? AND key = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            statement.setString(2, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("data");
            }
        }
        return null;
    }
    // ich mach ma solange mein giveaway xD


}
