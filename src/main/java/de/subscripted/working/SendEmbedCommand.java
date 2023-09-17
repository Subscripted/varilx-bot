package de.subscripted.working;

import de.subscripted.sql.EmbedSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.awt.*;
import java.sql.SQLException;

public class SendEmbedCommand extends ListenerAdapter {

    EmbedSQLManager sqlManager;

    public SendEmbedCommand() {
        sqlManager = new EmbedSQLManager();
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("sendembed"))
            return;



        Member member = event.getMember();
        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("Du hast keine Berechtigung, diesen Befehl auszuführen.")
                    .setEphemeral(true)
                    .queue();
            return;
        }
        String code = event.getOption("code").getAsString();
        TextChannel targetChannel = (TextChannel) event.getOption("channel").getAsChannel();

        try {
            String embedData = sqlManager.getEmbedValueByKey(code, "data");
            if (embedData == null) {
                event.reply("Keine Embed-Daten für diesen Code gefunden.")
                        .setEphemeral(true)
                        .queue();
                return;
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();

            String title = sqlManager.getEmbedValueByKey(code, "title");
            if (title != null) {
                embedBuilder.setTitle(title);
            }

            String color = sqlManager.getEmbedValueByKey(code, "color");
            if (color != null) {
                embedBuilder.setColor(Color.decode(color));
            }

            String description = sqlManager.getEmbedValueByKey(code, "description");
            if (description != null) {
                embedBuilder.setDescription(description);
            }

            String footer = sqlManager.getEmbedValueByKey(code, "footer");
            if (footer != null) {
                embedBuilder.setFooter(footer);
            }


            targetChannel.sendMessageEmbeds(embedBuilder.build()).queue();

            event.reply("Embed erfolgreich gesendet!")
                    .setEphemeral(true)
                    .queue();
        } catch (SQLException e) {
            event.reply("Ein Fehler ist aufgetreten.")
                    .setEphemeral(true)
                    .queue();
        }
    }
}

