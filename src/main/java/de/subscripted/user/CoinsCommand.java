 package de.subscripted.user;

import de.subscripted.Main;
import de.subscripted.backend.MoneySystem;
import de.subscripted.sql.MoneySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;



public class CoinsCommand extends ListenerAdapter {

    private MoneySQLManager moneysqlManager;


    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("varonx"))
            return;
        moneysqlManager = Main.moneysqlManager;
        Member targetMember = event.getOption("nutzer").getAsMember();

        MoneySystem moneySystem = new MoneySystem(moneysqlManager);
        try {
            int coins = moneySystem.getCoins(targetMember.getUser());

            String formattedCoins = String.format("%,d", coins);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Varonx")
                    .setColor(Color.GREEN)
                    .setDescription("Der Nutzer " + targetMember.getAsMention() + " besitzt " + formattedCoins + " Varonx.")
                    .setFooter("Varilx Coins Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png");

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


