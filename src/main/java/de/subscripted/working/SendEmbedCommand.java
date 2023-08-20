package de.subscripted.working;

import de.subscripted.sql.EmbedSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

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

        String embedData = null;
        try {
            embedData = sqlManager.getEmbedData(code);
            if (embedData == null) {
                System.out.println("Embed-Daten nicht gefunden!");
                event.reply("Keine Embed-Daten für diesen Code gefunden.")
                        .setEphemeral(true)
                        .queue();
                return;
            }

            System.out.println("Embed-Daten gefunden: " + embedData);


        } catch (SQLException e) {
            event.reply("Ein Fehler ist aufgetreten.")
                    .setEphemeral(true)
                    .queue();
        }

        DataObject embedJson = DataObject.fromJson(embedData);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        for (String key : embedJson.keys()) {
            String value = embedJson.getString(key);
            if (value != null) {
                switch (key) {
                    case "title":
                        embedBuilder.setTitle(value);
                        break;
                    case "color":
                        embedBuilder.setColor(Color.decode(value));
                        break;
                    case "description":
                        embedBuilder.setDescription(value);
                        break;
                    case "footer":
                        embedBuilder.setFooter(value);
                        break;
                }


                MessageEmbed embed = embedBuilder.build();
                targetChannel.sendMessageEmbeds(embed).queue();

                event.reply("Embed erfolgreich gesendet!")
                        .setEphemeral(true)
                        .queue();
                event.reply("Ein Fehler ist aufgetreten.")
                        .setEphemeral(true)
                        .queue();
            }
        }
    }
}
