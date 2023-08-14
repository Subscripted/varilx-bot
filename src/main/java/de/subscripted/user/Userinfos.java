package de.subscripted.user;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class Userinfos extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("userinfo"))
            return;

        Member user = event.getOption("nutzer").getAsMember();

        String name = user.getEffectiveName();
        String anzeigename = user.getNickname();

        OffsetDateTime creationDate = user.getTimeCreated();
        String creationTimestamp = "<t:" + creationDate.toEpochSecond() + ":D>";
        String timeAgoTimestamp = "<t:" + creationDate.toEpochSecond() + ":R>";

        long badges = user.getFlagsRaw();


        boolean hasNitro = (badges & 1 << 0) != 0;
        boolean hasHypeSquad = (badges & 1 << 1) != 0;
        boolean userIsBot = user.getUser().isBot();

        String badgesText = "Nitro: " + (hasNitro ? "Ja" : "Nein") + "\n" +
                "HypeSquad: " + (hasHypeSquad ? "Ja" : "Nein");

        OffsetDateTime joinDate = user.getTimeJoined();
        String joinedTimestamp = "<t:" + joinDate.toEpochSecond() + ":D>";
        String joinedTimeAgo = "<t:" + joinDate.toEpochSecond() + ":R>";

        String botStatusText = userIsBot ? "Ja" : "Nein";

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setAuthor("Varilx.de | Bot");
        embedBuilder.setTitle("Informationen zu " + name);
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription("Anzeigename: **" + anzeigename + "**\n" +
                "Name: **" + name + "**\n\n" +
                "Account erstellt am: **" + creationTimestamp + "**\n" +
                "Account erstellt vor: **" + timeAgoTimestamp + "**\n\n" +
                "Server betreten am: **" + joinedTimestamp + "**\n"+
                "Server betreten vor: **" + joinedTimeAgo + "**\n\n" +
                "Nickname: **" + anzeigename + "**\n" +
                "Bot: **" + botStatusText + "**\n\n" +
                "Badges: " + badgesText );

        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
