package de.subscripted.games;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.Random;

public class EightBall extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("8ball"))
            return;

        String message = event.getOption("message").getAsString();

        String[] replies = {"Ja", "Nein", "Vielleicht...", "Sehr wahrscheinlich", "Wahrscheinlich nicht...", "Auf jeden Fall!", "Nein, niemals!"};
        String randomReply = replies[(int) (Math.random() * replies.length)];

        Random random = new Random();
        Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(randomColor)
                .setFooter("Varilx Games Feature | Update 2023 © ", Main.getJda().getSelfUser().getAvatarUrl())
                .setAuthor(event.getMember().getEffectiveName())
                .setDescription("Frage: " + message + "\n\nAntwort: **" + randomReply + "**");

        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
