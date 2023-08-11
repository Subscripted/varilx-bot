package de.subscripted.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.List;

public class GuildMusicManager {
    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;
    public final AudioPlayerSendHandler sendHandler;


    public GuildMusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.scheduler = new TrackScheduler(audioPlayer);
        this.audioPlayer.addListener((AudioEventListener) this.scheduler);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);;
    }


    private final List<AudioTrack> queue = new ArrayList<>();

    public void addToQueue(AudioTrack track) {
        queue.add(track);
    }

    public AudioTrack getNextTrack() {
        return queue.isEmpty() ? null : queue.remove(0);
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }

    public AudioPlayer getPlayer() {
        return audioPlayer;
    }

    public TrackScheduler getScheduler() {
        return scheduler;
    }

    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }

}
