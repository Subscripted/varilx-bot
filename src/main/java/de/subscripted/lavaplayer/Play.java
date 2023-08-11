package de.subscripted.lavaplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import de.subscripted.Main;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Play extends ListenerAdapter {

    @SneakyThrows
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("play"))
            return;

        String input = event.getOption("name").getAsString();

        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du befindest dich nicht in einem Voice Channel!")
                    .setFooter("Varilx Musik Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        } else {
            if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx System")
                        .setColor(Color.RED)
                        .setDescription("Du musst im gleichen Voice Channel wie ich sein!")
                        .setFooter("Varilx Musik Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }
        }

        input = ":search:" + input; // Behandelt die Eingabe als Suchanfrage

        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getMusicManager(event.getGuild());

        // Wiedergabe des Tracks hinzufügen, ohne die Warteschlange zu löschen
        playerManager.loadAndPlay(event.getChannel().asTextChannel(), input);

        // Warte einen Moment, um dem Track Zeit zum Starten zu geben
        Thread.sleep(2000);

        AudioTrack currentlyPlaying = guildMusicManager.getPlayer().getPlayingTrack();

        if (currentlyPlaying != null) {
            AudioTrackInfo info = currentlyPlaying.getInfo();
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Musik")
                    .setColor(Color.GREEN)
                    .setDescription("Ich spiele jetzt " + info.title)
                    .setFooter("Varilx Musik Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(false).queue();
        } else {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Musik")
                    .setColor(Color.RED)
                    .setDescription("Es ist ein Fehler beim Abspielen aufgetreten.")
                    .setFooter("Varilx Musik Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }
    }
}
