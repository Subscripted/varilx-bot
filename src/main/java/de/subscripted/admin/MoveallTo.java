package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class MoveallTo extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("moveall"))
            return;

        if (!event.getMember().hasPermission(Permission.VOICE_MOVE_OTHERS)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        Member member = event.getMember();
        VoiceChannel voiceChannel = (VoiceChannel) member.getVoiceState().getChannel();
        VoiceChannel targetVoiceChannel = event.getOption("channel").getAsChannel().asVoiceChannel();

        if (voiceChannel == null) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Voice")
                    .setColor(Color.yellow)
                    .setDescription("Du musst in einem Voicechannel sein!")
                    .setFooter("Varilx Voice Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if (targetVoiceChannel == null) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Voice")
                    .setColor(Color.yellow)
                    .setDescription("Komischer Channel...")
                    .setFooter("Varilx Voice Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if (voiceChannel == targetVoiceChannel) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Voice")
                    .setColor(Color.yellow)
                    .setDescription("Du befindest dich doch schon in diesem Channel...")
                    .setFooter("Varilx Voice Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        int movedCount = 0;
        for (Member memberToMove : voiceChannel.getMembers()) {
            if (!memberToMove.getUser().isBot()) {
                voiceChannel.getGuild().moveVoiceMember(memberToMove, targetVoiceChannel).queue();
                movedCount++;
            }
        }

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Voice")
                .setColor(Color.GREEN)
                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                .setDescription("Du hast " + movedCount + " User in " + targetVoiceChannel.getAsMention() + " gemoved!")
                .setFooter("Varilx Voice Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
