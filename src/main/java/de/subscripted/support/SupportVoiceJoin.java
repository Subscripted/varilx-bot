package de.subscripted.support;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class SupportVoiceJoin extends ListenerAdapter {

    private final String targetChannelId;
    private final String destinationChannelId;

    public SupportVoiceJoin(String targetChannelId, String destinationChannelId) {
        this.targetChannelId = targetChannelId;
        this.destinationChannelId = destinationChannelId;
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {

        if (event.getChannelJoined() == null) return;

        VoiceChannel audioChannel = (VoiceChannel) event.getChannelJoined();
        EmbedBuilder embedBuilder = getBuilder(event, audioChannel);

        Role role = event.getGuild().getRoleById("1003618027037786205");



        if (audioChannel.getId().equals(targetChannelId)) {
            sendEmbedToTextChannel(event, embedBuilder);
            sendEmbedToPrivateChannels(event, embedBuilder);


        }
    }

    private void sendEmbedToTextChannel(GuildVoiceUpdateEvent event, EmbedBuilder embedBuilder) {

        Role role1 = event.getGuild().getRoleById("906598926688321578");
        Role role2 = event.getGuild().getRoleById("1062409445470113883");
        Role role3 = event.getGuild().getRoleById("1081305944652255292");
        Role role4 = event.getGuild().getRoleById("904032740394041364");
        Role role5 = event.getGuild().getRoleById("888503993117073420");
        Role role6 = event.getGuild().getRoleById("1081305694755639376");


        Button button = Button.secondary("erledigt_support", "Erledigt").withEmoji(Emoji.fromFormatted("<:varilx_voicestate:1139957544761176188>"));

        event.getGuild().getTextChannelById(destinationChannelId).sendMessage(role1.getAsMention() + role2.getAsMention() + role3.getAsMention() + "\n" + role4.getAsMention() + role5.getAsMention() + role6.getAsMention()).addEmbeds(embedBuilder.build()).addActionRow(button).queue();
    }


    private void sendEmbedToPrivateChannels(GuildVoiceUpdateEvent event, EmbedBuilder embedBuilder) {
        Role role1 = event.getGuild().getRoleById("906598926688321578");
        Role role2 = event.getGuild().getRoleById("1062409445470113883");
        Role role3 = event.getGuild().getRoleById("1081305944652255292");
        Role role4 = event.getGuild().getRoleById("904032740394041364");
        Role role5 = event.getGuild().getRoleById("888503993117073420");
        Role role6 = event.getGuild().getRoleById("1081305694755639376");

        List<Role> roleList = Arrays.asList(role1, role2, role3, role4, role5, role6);

        for (Role role : roleList) {
            event.getGuild().getMembersWithRoles(role).forEach(member -> {
                member.getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).queue();
            });
        }
    }


    @NotNull
    private static EmbedBuilder getBuilder(GuildVoiceUpdateEvent event, VoiceChannel audioChannel) {
        return new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Varilx Support")
                .setDescription(event.getMember().getAsMention() + " ist in den " + audioChannel.getAsMention() + " Channel gejoint")
                .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
    }
}
