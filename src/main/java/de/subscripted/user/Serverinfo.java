package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Serverinfo extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("serverinfo"))
            return;

        OffsetDateTime guildCreationTime = event.getGuild().getTimeCreated();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String formattedGuildCreationTime = formatter.format(guildCreationTime);

        OffsetDateTime guildCreationTime2 = event.getGuild().getTimeCreated();
        OffsetDateTime currentTime = OffsetDateTime.now();

        Duration ageDuration = Duration.between(guildCreationTime2, currentTime);
        long years = ageDuration.toDays() / 365;
        long months = (ageDuration.toDays() % 365) / 30;
        long days = ageDuration.toDays() % 30;

        String name = event.getGuild().getName();
        String id = event.getJDA().getSelfUser().getId();
        String eigentümer = event.getGuild().getOwner().getAsMention();
        String memberCount = String.valueOf(event.getGuild().getMemberCount());
        String channel = String.valueOf(event.getGuild().getChannels().size());
        String kategorien = String.valueOf(event.getGuild().getCategories().size());
        String text = String.valueOf(event.getGuild().getTextChannels().size());
        String sprache = String.valueOf(event.getGuild().getVoiceChannels().size());
        String threads = String.valueOf(event.getGuild().getThreadChannels().size());
        String Foren = String.valueOf(event.getGuild().getForumChannels().size());

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor("Varilx.de")
                .setTitle("<:varilx_star:1139957135707484290> Information zu Varilx.de - Bot")
                .setThumbnail(Main.getJda().getSelfUser().getAvatarUrl())
                .setColor(Color.GREEN)
                .setFooter("Varilx.de · Hosted By TubeHosting", "https://cdn.discordapp.com/emojis/1101657813794693120.webp?size=96&quality=lossless")
                .setDescription("<:varilx_discord:1139957449516924959> Name : **" + name + "**\n" +
                        "<:varilx_id:1139957240963539054> ID : **" + id + "**\n" +
                        "<:varilx_owner:1139957062638506094> Eigentümer: **" + eigentümer + "**\n" +
                        "<:varilx_user:1139957321196376107> Mitglieder : **" + memberCount + "**\n" +
                        "Commands : " + "\n\n" +
                        "<:varilx_channel:1139957362380263538> Channel : **" + channel + "**\n" +
                        "<:varilx_directory:1139957175972810812> davon Kategorien : **" + kategorien + "**\n" +
                        "<:varilx_textchannel:1139957022696157294> davon Text : **" + text + "**\n" +
                        "<:varilx_voicestate:1139957544761176188> davon Sprache : **" + sprache + "**\n" +
                        "<:varilx_forum:1139957579867496499> davon Threads : **" + threads + "**\n" +
                        "<:varilx_forum:1139957497336172584> davon Foren : **" + Foren + "**\n\n" +
                        "<:varilx_clendar:1139956980576960653> Erstellt am : **" + formattedGuildCreationTime + "**\n" +
                        "<:varilx_clock:1139957097522528257> Erstellt vor : ** " + years + " Jahr, " + months + " Monate und " + days + " Tage**\n");

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();


    }
}
