package de.subscripted.lavaplayer;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Stop extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("stop"))
            return;

        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du musst in einem Voice Channel sein!")
                    .setFooter("Varilx Musik Feature | Update 2023 © ",Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Ich bin in keinem Voice Channel!")
                    .setFooter("Varilx Musik Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du bist nicht in dem gleichen Channel wie ich!")
                    .setFooter("Varilx Musik Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        guildMusicManager.getScheduler().getPlayer().stopTrack();
        guildMusicManager.getScheduler().getQueue().clear();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Musik")
                .setColor(Color.RED)
                .setDescription("Du hast den aktuellen Song gestoppt und die Warteschlange geleert.")
                .setFooter("Varilx Musik Feature | Update 2023 © ", Main.redfooter);
        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
