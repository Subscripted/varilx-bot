package de.subscripted.admin;

import de.subscripted.Main;
import de.subscripted.backend.MoneySystem;
import de.subscripted.sql.MoneySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;

public class SetCoins extends ListenerAdapter {

    private MoneySQLManager moneysqlManager;
    private MoneySystem moneySystem;

    public SetCoins(MoneySQLManager moneysqlManager) {
        this.moneysqlManager = moneysqlManager;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        Member targetMember = event.getOption("nutzer").getAsMember();

        if (!event.getName().equals("setcoins"))
            return;

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        int coinsAmount = Integer.parseInt(event.getOption("coins").getAsString());
        String formattedCoins = String.format("%,d", coinsAmount);
        try {
            moneysqlManager.setCoins(targetMember.getId(), coinsAmount);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Money")
                    .setColor(Color.GREEN)
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                    .setDescription("Varonx erfolgreich für " + targetMember.getAsMention() + " auf " + formattedCoins + " gesetzt!")
                    .setFooter("Varilx Money Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
