package de.subscripted.working;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.EnumSet;

public class OwnVoiceChannelBuilder extends ListenerAdapter {
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if (event.getChannelJoined() == null)
            return;

        Member owner = event.getMember();
        VoiceChannel voiceChannel = event.getGuild().getVoiceChannelById("YOUR_VOICE_CHANNEL_ID");

        if (voiceChannel != null && voiceChannel.getId().equals("1132261803280306258")) {
            if (event.getChannelJoined().equals(voiceChannel) && owner != null) {
                EnumSet<Permission> allPermissions = Permission.getPermissions(Permission.ALL_PERMISSIONS);
                voiceChannel.getManager().putPermissionOverride(owner, allPermissions, null).queue();
            }
        }
    }
}
