package de.subscripted.user;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.cassandra.transport.Server;
import org.apache.cassandra.transport.ServerConnection;

import java.awt.*;
import java.time.Instant;

public class Ping extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("ping"))
            return;

        Instant before = Instant.now();
        Instant after = Instant.now();
        long ping = after.toEpochMilli() - before.toEpochMilli();

        EmbedBuilder embedbuilder = new EmbedBuilder()
                .setTitle("Ping")
                .setColor(Color.darkGray)
                .setDescription("Ping: " + ping + " ms");

        event.replyEmbeds(embedbuilder.build()).setEphemeral(true).queue();

    }
}



