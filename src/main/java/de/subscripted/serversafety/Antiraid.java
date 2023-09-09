package de.subscripted.serversafety;

import java.awt.*;
import java.time.Instant;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Antiraid extends ListenerAdapter {
    private static final long ACCOUNT_AGE_THRESHOLD = 172800000;

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        User user = member.getUser();
        Instant accountCreationTime = user.getTimeCreated().toInstant();
        TextChannel kickChannel = guild.getTextChannelById("1134461790818947122");

        EmbedBuilder antiraid = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Varilx Antiraid")
                .setDescription("Du wurdest gekickt!")
                .addField("Grund: ", "Dein account muss mindestens seid 2 Tagen existieren, bevor du joinen kannst!", false)
                .addField("Weitere Infos", "Du wurdest gekickt. Fals uns ein Fehler unterlaufen ist, melde dich bitte hier: https://discord.gg/w7UDkSevhF", false)
                .setFooter("Varilx Server Schutz Feature | Update 2023 © ", Main.redfooter);


        EmbedBuilder dcantiraid = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Varilx Antiraid")
                .setDescription("Varilx System hat (" + user.getEffectiveName() + ") / (" + event.getMember().getNickname() + ") gekickt")
                .addField("Grund", "Der Account des Users war jünger als 2 Tage!", false)
                .addField("Weitere Infos:" , "Erstellung des Account,s " + "```" + accountCreationTime + "```", false)
                .setFooter("Varilx Server Schutz Feature | Update 2023 © ", Main.redfooter);


        if (Instant.now().toEpochMilli() - accountCreationTime.toEpochMilli() < ACCOUNT_AGE_THRESHOLD) {
            user.openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessageEmbeds(antiraid.build()).queue();

                kickChannel.sendMessageEmbeds(dcantiraid.build()).queue();
                guild.kick(member).queue();
            });
        }
    }
}
