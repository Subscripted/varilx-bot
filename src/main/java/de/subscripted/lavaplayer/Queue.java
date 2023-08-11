package de.subscripted.lavaplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class Queue extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("queue"))
            return;

        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du musst in einem Voice Channel sein!")
                    .setFooter("Varilx Musik Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        List<AudioTrack> queue = (List<AudioTrack>) guildMusicManager.getScheduler().getQueue();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Varilx Musik Queue");
        embedBuilder.setColor(Color.GREEN);

        if (queue.isEmpty()) {
            embedBuilder.setDescription("Es existiert keine Warteschlange.");
        } else {
            for (int i = 0; i < queue.size(); i++) {
                AudioTrackInfo info = queue.get(i).getInfo();
                embedBuilder.addField(i + 1 + ":", info.title, false);
            }
        }

        embedBuilder.setFooter("Varilx Musik Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
