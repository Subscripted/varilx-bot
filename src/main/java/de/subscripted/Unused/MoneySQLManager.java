/*package de.subscripted.Unused;

import de.subscripted.Main;
import de.subscripted.Unused.TopMoneyList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.sql.*;

public class MoneySQLManager {
    private Connection connection;

    public MoneySQLManager() {
        if (connection != null) {
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:varilxcoins.db");
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
        String createTableQuery = "CREATE TABLE IF NOT EXISTS players (user_id VARCHAR(22) PRIMARY KEY, coins INTEGER)";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    public int getCoins(String userId) throws SQLException {
        String query = "SELECT coins FROM players WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("coins");
            } else {
                setCoins(userId, 0);
                return 0;
            }
        }
    }

    public synchronized void setCoins(String userId, int coins) throws SQLException {
        if (!userAlreadyExists(userId)) {
            String query = "INSERT INTO players (user_id, coins) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userId);
                statement.setInt(2, coins);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            update("UPDATE players SET coins = " + coins + " WHERE user_id = '" + userId + "'");
        }

        Guild guild = Main.getJda().getGuildById("886262410489520168");

        TextChannel moneyTopChannel = guild.getTextChannelById("1139090218385944636");
        moneyTopChannel.getHistory().retrievePast(1).queue(messages -> {
            messages.get(0).editMessageEmbeds(TopMoneyList.sendTopMoneyList(guild)).queue();
        });
    }

    public synchronized void addCoins(String userId, int amount) throws SQLException {
        int currentCoins = getCoins(userId);
        int newCoins = currentCoins + amount;
        setCoins(userId, newCoins);

        Guild guild = Main.getJda().getGuildById("886262410489520168");

        TextChannel moneyTopChannel = guild.getTextChannelById("1139090218385944636");
        moneyTopChannel.getHistory().retrievePast(1).queue(messages -> {
            messages.get(0).editMessageEmbeds(TopMoneyList.sendTopMoneyList(guild)).queue();
        });
    }

    public synchronized void removeCoins(String userId, int amount) throws SQLException {
        int currentCoins = getCoins(userId);
        if (currentCoins >= amount) {
            int newCoins = currentCoins - amount;
            setCoins(userId, newCoins);
        }
    }
}

 */

