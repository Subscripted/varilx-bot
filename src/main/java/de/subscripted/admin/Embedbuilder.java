package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Embedbuilder extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("embedbuilder"))
            return;
        Member member = event.getMember();
        if (member == null) {
            event.reply("Es ist ein Fehler aufgetreten. Bitte versuche es erneut.").setEphemeral(true).queue();
            return;
        }

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung für diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        String title = getOptionValue(event, "title");
        String field1 = getOptionValue(event, "field1");
        String value1 = getOptionValue(event, "value1");
        String field2 = getOptionValue(event, "field2");
        String value2 = getOptionValue(event, "value2");
        String field3 = getOptionValue(event, "field3");
        String value3 = getOptionValue(event, "value3");
        String field4 = getOptionValue(event, "field4");
        String value4 = getOptionValue(event, "value4");
        String description = getOptionValue(event, "description");
        String footer = getOptionValue(event, "footer");
        String image = getOptionValue(event, "image");
        String thumbnail = getOptionValue(event, "thumbnail");
        String color = getOptionValue(event, "color");
        String author = getOptionValue(event, "author");
        String footerimage = getOptionValue(event, "footerimage");

        Color embedColor = color != null ? getColorFromString(color) : Color.GREEN;

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(embedColor);

        if (field1 != null && value1 != null) {
            embedBuilder.addField(field1, value1, false);
        }
        if (field2 != null && value2 != null) {
            embedBuilder.addField(field2, value2, false);
        }
        if (field3 != null && value3 != null) {
            embedBuilder.addField(field3, value3, false);
        }
        if (field4 != null && value4 != null) {
            embedBuilder.addField(field4, value4, false);
        }

        if (image != null) {
            embedBuilder.setImage(image);
        }
        if (thumbnail != null) {
            embedBuilder.setThumbnail(thumbnail);
        }
        if (author != null) {
            embedBuilder.setAuthor(author);
        }
        if (footer != null) {
            embedBuilder.setFooter(footer);
        }

        if (embedColor == Color.RED && footerimage == null){
            embedBuilder.setFooter(footer, Main.redfooter);
        }

        if (footerimage == null) {
            embedBuilder.setFooter(footer, Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        }

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    private String getOptionValue(SlashCommandInteractionEvent event, String optionName) {
        if (event.getOption(optionName) != null) {
            return event.getOption(optionName).getAsString();
        }
        return null;
    }

    private Color getColorFromString(String colorString) {
        String colorLower = colorString.toLowerCase();
        switch (colorLower) {
            case "rot":
                return Color.RED;
            case "blau":
                return Color.BLUE;
            case "grün":
                return Color.GREEN;
            case "gelb":
                return Color.YELLOW;
            case "orange":
                return Color.ORANGE;
            case "hellblau":
                return Color.CYAN;
            case "pink":
                return Color.PINK;
            case "lila":
                return Color.MAGENTA;
            case "weiß":
                return Color.WHITE;
            default:
                return Color.GREEN;
        }
    }
}
