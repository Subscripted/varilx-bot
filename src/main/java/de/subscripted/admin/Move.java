package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Move extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        Member member = event.getMember();

        String string = event.getOption("nutzer").getAsString().replace("<@", "").replace(">", "").replace(" ", "");
        String[] stringList = string.split(",");
        List<Member> memberList = new ArrayList<>();
        for (String s : stringList) {
            memberList.add(event.getGuild().getMemberById(s));
        }


        if (!event.getName().equals("move"))
            return;

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        VoiceChannel memberVoiceChannel = (VoiceChannel) member.getVoiceState().getChannel();
        if (memberVoiceChannel == null) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Voice")
                    .setColor(Color.RED)
                    .setDescription("DU bist in keinem Channel!")
                    .setFooter("Varilx Voice Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }
        List<String> memberMentions = new ArrayList<>();
        for (Member targetMember : memberList) {
            memberMentions.add(targetMember.getAsMention());
            VoiceChannel targetVoiceChannel = (VoiceChannel) targetMember.getVoiceState().getChannel();
            if (targetVoiceChannel == null) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx Voice")
                        .setColor(Color.RED)
                        .setDescription("Der Zielbenutzer ist in keinem Voice-Channel!")
                        .setFooter("Varilx Voice Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }


            if (memberVoiceChannel.equals(targetVoiceChannel)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx Voice")
                        .setColor(Color.RED)
                        .setDescription("Der Zielbenutzer befindet sich bereits im gleichen Voice-Channel wie du!")
                        .setFooter("Varilx Voice Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }
            targetMember.getGuild().moveVoiceMember(targetMember, memberVoiceChannel).queue();
        }
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Voice")
                .setColor(Color.GREEN)
                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                .setDescription("Du hast " + memberMentions +" in " + memberVoiceChannel.getAsMention() + " gemoved!")
                .setFooter("Varilx Voice Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
