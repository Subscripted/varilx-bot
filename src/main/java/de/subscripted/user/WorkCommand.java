package de.subscripted.user;

import de.subscripted.Main;
import de.subscripted.backend.MoneySystem;
import de.subscripted.sql.MoneySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;



public class WorkCommand extends ListenerAdapter {

    private MoneySQLManager moneysqlManager;
    private static HashMap<String, Long> lastWorkTimes = new HashMap<>();

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        moneysqlManager = Main.moneysqlManager;
        if (!event.getName().equals("work"))
            return;

        String userId = event.getUser().getId();

        if (!canWork(userId)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle("Varilx Work")
                    .setDescription("Du kannst nur einmal pro Stunde arbeiten!")
                    .setFooter("Varilx Money Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        int coinsEarned = (int) (Math.random() * (360 - 10 + 1)) + 10;
        MoneySystem moneySystem = new MoneySystem(moneysqlManager);

        try {
            moneySystem.addCoins(event.getUser(), coinsEarned);
            lastWorkTimes.put(userId, System.currentTimeMillis());

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Varilx Work")
                    .setDescription("Du hast gearbeitet und dadurch " + coinsEarned + " Varonx verdient!")
                    .setFooter("Varilx Money Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Es ist ein Fehler aufgetreten. Bitte versuche es später erneut.").setEphemeral(true).queue();
        }
    }

    private static boolean canWork(String userId) {
        long lastWorkTime = lastWorkTimes.getOrDefault(userId, 0L);
        long currentTime = System.currentTimeMillis();
        long oneHourInMillis = 60 * 60 * 1000;

        return currentTime - lastWorkTime >= oneHourInMillis;
    }
}




