package de.subscripted.core;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;

public record AudioInfo(AudioTrack track, Member author) {

}
