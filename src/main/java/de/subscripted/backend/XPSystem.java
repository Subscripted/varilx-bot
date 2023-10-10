package de.subscripted.backend;

import de.subscripted.Main;
import de.subscripted.sql.XpSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class XPSystem extends ListenerAdapter {
    private XpSQLManager xpSqlManager;
    public static Map<Integer, Integer> levelXPRequirements;

    public XPSystem() {
        xpSqlManager = Main.xpSqlManager;
        levelXPRequirements = new HashMap<>();

        int kek = 10;
        for (int x = 1; x < 51; x++) {
            levelXPRequirements.put(x, kek);
            kek = kek + (kek * 101 / 100);
        }
        for (Integer m : levelXPRequirements.keySet()) {
            System.out.println(m + " / " + levelXPRequirements.get(m));
        }
    }

    public static int getLevelXP(int xp) {
        int levelXP = 0;
        int previousLevelXP = 0;
        for (Map.Entry<Integer, Integer> entry : levelXPRequirements.entrySet()) {
            if (entry.getValue() > xp) {
                levelXP = entry.getValue() - previousLevelXP;
                break;
            }
            previousLevelXP = entry.getValue();
        }
        if (levelXP == 0) {
            levelXP = levelXPRequirements.get(levelXPRequirements.size()) - previousLevelXP;
        }
        return levelXP;
    }


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        User user = event.getAuthor();
        String userId = user.getId();
        Message message = event.getMessage();

        try {
            int currentXP = xpSqlManager.getXP(userId);
            int currentLevel = xpSqlManager.getLevel(userId);

            int xpGained = getRandomXP(message.getContentRaw());
            currentXP += xpGained;

            int newLevel = calculateLevel(currentXP);
            System.out.println(currentLevel);
            if (newLevel > currentLevel) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx XP")
                        .setDescription("<a:Vaxparty:1137213809128382474> - Du bist  auf Level " + newLevel + " aufgestiegen!")
                        .setColor(Color.GREEN)
                        .setThumbnail(Main.getThumbnail())
                        .setFooter("Varilx XP Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessageEmbeds(embedBuilder.build()).queue());

                xpSqlManager.setLevel(userId, newLevel);
                if (currentLevel > 0) {
                    currentXP = 0;
                }
            }

            System.out.println(currentXP);

            xpSqlManager.setXP(userId, currentXP);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int calculateLevel(int xp) {
        int level = 1;
        for (Integer m : levelXPRequirements.keySet()) {
            if (xp >= levelXPRequirements.get(m)) {
                level = m;
            }
        }

        return level;
    }

    private int getRandomXP(String msg) {
        double min = 0.5;
        double max = 1.5;
        double totalXP = 0;
        String[] words = msg.split(" ");
        for (String s : words) {
            totalXP += Math.random() * (max - min) + min;
        }

        return (int) Math.round(totalXP);
    }

    public static int getRequiredXP(int level) {
        if (level == 0){
            return 0;
        }
        return levelXPRequirements.get(level);
    }
}
