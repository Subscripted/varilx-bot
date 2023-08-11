package de.subscripted.backend;

import de.subscripted.Main;
import de.subscripted.admin.TopMoneyList;
import de.subscripted.sql.MoneySQLManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.sql.SQLException;

public class MoneySystem {
    private MoneySQLManager moneysqlManager;

    public MoneySystem(MoneySQLManager moneysqlManager) {
        moneysqlManager = Main.moneysqlManager;
        this.moneysqlManager = moneysqlManager;
    }

    public int getCoins(User user) throws SQLException {
        return moneysqlManager.getCoins(user.getId());
    }



    public void setCoins(User user, int coins) throws SQLException {
        moneysqlManager.setCoins(String.valueOf(user), coins); // Die Methode aufrufen

        Guild guild = Main.getJda().getGuildById("886262410489520168");
        TextChannel moneyTopChannel = guild.getTextChannelById("1139090218385944636");

        moneyTopChannel.getHistory().retrievePast(1).queue(messages -> {
            messages.get(0).editMessageEmbeds(TopMoneyList.sendTopMoneyList(guild)).queue(); // Embed aktualisieren
        });
    }


    public void addCoins(User user, int amount) throws SQLException {
        int currentCoins = moneysqlManager.getCoins(user.getId());
        int newCoins = currentCoins + amount;
        moneysqlManager.setCoins(user.getId(), newCoins);

        Guild guild = Main.getJda().getGuildById("886262410489520168");

        TextChannel moneyTopChannel = guild.getTextChannelById("1139090218385944636");
        moneyTopChannel.getHistory().retrievePast(1).queue(messages -> {
            messages.get(0).editMessageEmbeds(TopMoneyList.sendTopMoneyList(guild)).queue();
        });
    }


    public void removeCoins(User user, int amount) throws SQLException {
        int currentCoins = moneysqlManager.getCoins(user.getId());
        int newCoins = Math.max(0, currentCoins - amount); // Ensures that coins cannot go below 0
        moneysqlManager.setCoins(user.getId(), newCoins);
        Guild guild = Main.getJda().getGuildById("886262410489520168");

        TextChannel moneyTopChannel = guild.getTextChannelById("1139090218385944636");
        moneyTopChannel.getHistory().retrievePast(1).queue(messages -> {
            messages.get(0).editMessageEmbeds(TopMoneyList.sendTopMoneyList(guild)).queue();
        });
    }
    }


