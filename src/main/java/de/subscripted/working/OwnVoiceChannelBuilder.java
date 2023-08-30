package de.subscripted.working;
import net.dv8tion.jda.api.Permission;
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

        Member member = event.getMember();
        VoiceChannel joinedChannel = (VoiceChannel) event.getChannelJoined();
        VoiceChannel targetChannel = event.getGuild().getVoiceChannelById("1132261803280306258");

        if (joinedChannel != null && joinedChannel.equals(targetChannel)) {
            Category category = event.getGuild().getCategoryById("1132261801195753522");
            if (category != null) {
                VoiceChannel newChannel = (VoiceChannel) category.createVoiceChannel(member.getEffectiveName()).complete().getManager().putPermissionOverride(member, EnumSet.of(Permission.MANAGE_CHANNEL), null);
                event.getGuild().moveVoiceMember(member, newChannel).queue();

            }
        }
    }
}
