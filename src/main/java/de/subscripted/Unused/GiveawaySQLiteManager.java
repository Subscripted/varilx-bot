package de.subscripted.Unused;

import de.subscripted.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.sql.*;

public class GiveawaySQLiteManager {
    private Connection connection;
    private final String dbPath = "jdbc:sqlite:varilx_giveaways.db";

    public GiveawaySQLiteManager() {
        try {
            connection = DriverManager.getConnection(dbPath);
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
        ResultSet rs = query("SELECT user_id FROM participants WHERE user_id = '" + user_id + "'");
        return rs.next();
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS participants (user_id VARCHAR(22) PRIMARY KEY)";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    public void addUser(String userId) throws SQLException {
        if (!userAlreadyExists(userId)) {
            String query = "INSERT INTO participants (user_id) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteUser(String userId) throws SQLException {
        String query = "DELETE FROM participants WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
