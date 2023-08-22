package de.subscripted.user;

import de.subscripted.Main;
import de.subscripted.sql.MoneySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;

public class PayCommand extends ListenerAdapter {

    private MoneySQLManager moneySQLManager;

    public PayCommand(MoneySQLManager moneysqlManager) {
        this.moneySQLManager = moneysqlManager;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
         if (!event.getName().equals("pay"))
             return;
        Member from = event.getMember();
        Member to = event.getOption("nutzer").getAsMember();
        if (event.getOption("coins") != null) {
            int coinsAmount = Integer.parseInt(event.getOption("coins").getAsString());
            String formattedCoins = String.format("%,d", coinsAmount);

            String fromMoney = null;
            try {
                fromMoney = String.valueOf(moneySQLManager.getCoins(from.getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int fromMoneyInt = Integer.parseInt(fromMoney);

            if (to == null) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Money")
                        .setDescription("Der von dir angegebene Nutzer existiert nicht!")
                        .setFooter("Varilx Money Feature | Update 2023 ©", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            if (to == from) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Money")
                        .setDescription("Du kannst dir selber nichts payen!")
                        .setFooter("Varilx Money Feature | Update 2023 ©", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            if (coinsAmount >= fromMoneyInt) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Money")
                        .setDescription("Du besitzt nicht so viele Varonx!")
                        .setFooter("Varilx Money Feature | Update 2023 ©", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }


            try {
                moneySQLManager.removeCoins(from.getId(), coinsAmount);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                    .setTitle("Varilx Money")
                    .setDescription("Du hast " + to.getAsMention() + " " + formattedCoins + " Varonx gepayt!")
                    .setFooter("Varilx Money Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            try {
                moneySQLManager.addCoins(to.getId(), coinsAmount);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            EmbedBuilder embedBuilder2 = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                    .setTitle("Varilx Money")
                    .setDescription("Du hast von " + from.getAsMention() + " " + formattedCoins + " Varonx erhalten!")
                    .setFooter("Varilx Money Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            to.getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder2.build()).queue();
        }
    }
}
