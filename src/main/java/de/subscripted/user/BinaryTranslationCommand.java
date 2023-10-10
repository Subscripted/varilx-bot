package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class BinaryTranslationCommand extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("binärtotext"))
            return;

        String input = event.getOption("code").getAsString().replaceAll(" ", "");
        String output;

        if (isBinary(input)) {
            output = binaryToText(input);
        } else {
            output = textToBinary(input);
        }

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Binär to Text")
                .setColor(Color.GREEN)
                .addField("Eingabe: ", "```" + input + "```", false)
                .addField("Ausgabe:", "```" + output + "```", false)
                .setFooter("Varilx Binär Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

        event.replyEmbeds(embedBuilder.build()).queue();
    }

    private boolean isBinary(String input) {
        return input.matches("^[01]+$");
    }

    private String binaryToText(String binary) {
        StringBuilder text = new StringBuilder();
        String[] binaryChunks = binary.split("  ");
        for (String chunk : binaryChunks) {
            int decimal = Integer.parseInt(chunk, 2);
            text.append((char) decimal);
        }
        return text.toString();
    }

    private String textToBinary(String text) {
        StringBuilder binary = new StringBuilder();
        for (char c : text.toCharArray()) {
            binary.append(Integer.toBinaryString(c)).append("  ");
        }
        return binary.toString().trim();
    }
}
