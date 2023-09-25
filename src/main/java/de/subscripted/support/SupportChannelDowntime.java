package de.subscripted.support;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.LocalTime;

public class SupportChannelDowntime extends ListenerAdapter {

    private boolean kickEnabled = true;

    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Guild guild = event.getGuild();
        VoiceChannel targetChannel = guild.getVoiceChannelById("970663709573783553");
        LocalTime currentTime = LocalTime.now().plusHours(2);
        LocalTime startTime = LocalTime.of(13, 30); // 8 Uhr
        LocalTime endTime = LocalTime.of(0, 0); // 21 Uhr
        System.out.println(currentTime);

        if (currentTime.isAfter(endTime)) {
            kickEnabled = false;
        } else {
            if (currentTime.isBefore(endTime) && startTime.isBefore(endTime)) {
                kickEnabled = true;
            }
        }

        if (targetChannel != null && kickEnabled == true && event.getChannelJoined().equals(targetChannel)) {
            System.out.println("Benutzer wurde gekickt.");
            Member member =  event.getMember() ;
            targetChannel.getGuild().kickVoiceMember(member).queue();
        }
    }
}
