package de.subscripted.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();
    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    public PlayerManager() {
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return musicManagers.computeIfAbsent(guild.getIdLong(), guildId -> {
            GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        if (trackUrl.startsWith(":search:")) {
            // Wenn es sich um eine Suchanfrage handelt, den Suchbegriff extrahieren und die URL korrekt bilden
            String searchTerm = trackUrl.replace(":search:", "").trim();
            trackUrl = "ytsearch:" + searchTerm;
        }

        String finalTrackUrl = trackUrl;
        this.audioPlayerManager.loadItemOrdered(musicManager.getPlayer(), trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.getScheduler().queue(track);

                channel.sendMessage(
                        "Setze in der Warteschlange: `\n" +
                                track.getInfo().title + "\n" +
                                "` von `" +
                                track.getInfo().author + "\n" +
                                '`'
                ).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                List<AudioTrack> tracks = playlist.getTracks();

                if (tracks.isEmpty()) {
                    channel.sendMessage("Keine Ergebnisse gefunden für: `" + finalTrackUrl + "`").queue();
                    return;
                }

                channel.sendMessage(
                        "Setze in der Warteschlange: `\n" +
                                tracks.size() + "\n" +
                                "` Lieder von der Playlist `" +
                                playlist.getName() + "\n" +
                                '`'
                ).queue();

                for (AudioTrack track : tracks) {
                    musicManager.getScheduler().queue(track);
                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Keine Ergebnisse gefunden für: `" + finalTrackUrl + "`").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Fehler beim Laden des Tracks.").queue();
            }
        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
