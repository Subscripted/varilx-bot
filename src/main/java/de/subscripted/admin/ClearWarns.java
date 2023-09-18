package de.subscripted.admin;

import de.subscripted.Main;
import de.subscripted.sql.WarnSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.*;
import java.sql.SQLException;

public class ClearWarns extends ListenerAdapter {
    WarnSQLManager warnSQLManager;

    public ClearWarns(WarnSQLManager warnSQLManager) {
        this.warnSQLManager = warnSQLManager;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("deletewarns"))
            return;

        Member sender = event.getMember();
        Member target = event.getOption("nutzer").getAsMember();

        if (!sender.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        try {
            if (warnSQLManager.getReasons(target.getId()).isEmpty()) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx System")
                        .setColor(Color.RED)
                        .setDescription(target.getAsMention() + " hat keine Warns!")
                        .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            } else {
                try {
                    warnSQLManager.clearReasons(target.getId());
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Varilx Safety")
                            .setColor(Color.GREEN)
                            .setDescription("Du hast alle Warns von " + target.getAsMention() + " entfernt!")
                            .setFooter("Varilx Safety Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
