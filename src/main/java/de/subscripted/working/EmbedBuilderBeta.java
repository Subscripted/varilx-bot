package de.subscripted.working;

import de.subscripted.Main;
import de.subscripted.sql.EmbedSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.sql.SQLException;
import java.util.UUID;



public class EmbedBuilderBeta extends ListenerAdapter {
    EmbedSQLManager sqlManager;

    public EmbedBuilderBeta (){
        sqlManager = new EmbedSQLManager();

    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        Member member = event.getMember();

        if (!event.getName().equals("embedbuilderbeta"))
            return;

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        String embedCode = generateUniqueCode();
        try {
            sqlManager.saveEmbedCode(embedCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Button titleButton = Button.secondary("title", "Title");
        Button colorButton = Button.secondary("color", "Color");
        Button descriptionButton = Button.secondary("dsc", "Description");
        Button footerButton = Button.secondary("footer", "Footer");
        Button authorButton = Button.secondary("author", "Author");
        Button fieldButton = Button.secondary("field", "Field");
        Button imageButton = Button.secondary("image", "Image");
        Button thumbnailButton = Button.secondary("thmb", "Thumbnail");

        ActionRow firstRow = ActionRow.of(titleButton, colorButton, descriptionButton, footerButton, authorButton);
        ActionRow secondRow = ActionRow.of(fieldButton, imageButton, thumbnailButton);

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(java.awt.Color.GREEN)
                .setTitle("Varilx Embed-Builder v1.0.0")
                .setDescription("Interagiere mit den Buttons unter der Nachricht um dein Embed zusammen zustellen!")
                .setFooter("Varilx Embed Feature | Update 2023 © ", Main.getJda().getSelfUser().getAvatarUrl());

        event.replyEmbeds(embedBuilder.build())
                .addActionRow(titleButton, colorButton, descriptionButton, footerButton, authorButton)
                .addActionRow(fieldButton, imageButton, thumbnailButton)
                .setEphemeral(true)
                .queue();
        event.getChannel().sendMessage(embedCode).queue();
    }
    private String generateUniqueCode() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}

