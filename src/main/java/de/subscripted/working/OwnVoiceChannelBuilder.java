package de.subscripted.working;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.EnumSet;

public class OwnVoiceChannelBuilder extends ListenerAdapter {
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if (event.getChannelJoined() == null)
            return;

        Member owner = event.getMember();
        VoiceChannel voiceChannel = (VoiceChannel) event.getChannelJoined();

        if (voiceChannel != null && voiceChannel.getId().equals("1132261803280306258")) {
            Category category = event.getGuild().getCategoryById("1132261801195753522");
        }
    }
}
