package de.subscripted.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter implements AudioEventListener {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private boolean repeat = false;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return queue;
    }

    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public void nextTrack() {
        if (repeat) {
            AudioTrack track = player.getPlayingTrack();
            player.startTrack(track.makeClone(), false);
            return;
        }

        // Stopp den aktuellen Track im Player
        player.stopTrack();

        // Entferne den aktuellen Track aus der Warteschlange
        AudioTrack currentTrack = player.getPlayingTrack();
        if (currentTrack != null) {
            queue.remove(currentTrack);
        }

        // Starte den nächsten Track in der Warteschlange (falls vorhanden)
        AudioTrack nextTrack = queue.poll();
        if (nextTrack != null) {
            player.startTrack(nextTrack, false);
        }
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    // Implement other methods from AudioEventListener if needed

    // For example:
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        // Handle track start event if needed
    }
}
