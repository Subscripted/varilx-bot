package de.subscripted.user;

import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onLeave extends ListenerAdapter {
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        VoiceChannel voiceChannel = event.getGuild().getVoiceChannelById("1139724927155576954");
        if (voiceChannel != null) {
            voiceChannel.getManager().setName("\uD83D\uDCCC・ | User: " + event.getGuild().getMemberCount()).queue();
        }
    }
}
