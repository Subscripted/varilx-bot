package de.subscripted.core;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer player;
    private final Queue<AudioInfo> queue;

    public TrackManager(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void volume(int volume) {
        player.setVolume(volume);
    }

    public void queue(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);

        queue.add(info);

        if (player.getPlayingTrack() == null) {
            player.playTrack(track);
        }
    }

    public void remove(AudioInfo track) {
        queue.remove(track);
    }

    public Set<AudioInfo> getQueue() {
        return new LinkedHashSet<>(queue);
    }

    public AudioInfo getInfo(AudioTrack track) {
        return queue.stream()
                .filter(info -> info.track().equals(track))
                .findFirst().orElse(null);
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void shuffleQueue() {
        List<AudioInfo> currenQueue = new ArrayList<>(getQueue());
        AudioInfo current = currenQueue.get(0);

        currenQueue.remove(0);
        Collections.shuffle(currenQueue);
        currenQueue.add(0, current);

        purgeQueue();
        queue.addAll(currenQueue);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = queue.element();

        if (!info.author().getVoiceState().inAudioChannel()) {
            player.stopTrack();
            return;
        }
        VoiceChannel voiceChannel = (VoiceChannel) info.author().getVoiceState().getChannel();

        info.author().getGuild().getAudioManager().openAudioConnection(voiceChannel);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        Guild guild = queue.poll().author().getGuild();

        if (queue.isEmpty()) {
            guild.getAudioManager().closeAudioConnection();
            return;
        }

        player.playTrack(queue.element().track());
    }
}
