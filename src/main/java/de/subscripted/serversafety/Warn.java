package de.subscripted.serversafety;

import de.subscripted.Main;
import de.subscripted.sql.WarnSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.sql.SQLException;

public class Warn extends ListenerAdapter {
    WarnSQLManager sqlManager;
    Member target;

    public Warn(WarnSQLManager sqlManager){
        this.sqlManager = sqlManager;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("warn"))
            return;

        Member member = event.getMember();
        target = event.getOption("nutzer").getAsMember();

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        TextInput reason = TextInput.create("reason", "Grund", TextInputStyle.PARAGRAPH).build();
        Modal modal = Modal.create("warnmodal", "Warn-Grund").addActionRow(reason).build();
        event.replyModal(modal).queue();
    }

    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().equalsIgnoreCase("warnmodal"))
            return;
        if (target != null) {
            String reason = event.getValue("reason").getAsString();
            try {
                sqlManager.setReason(target.getId(), reason);
            } catch (SQLException e) {
                throw new RuntimeException(e);

            }
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.GREEN)
                    .setDescription("Du hast " + target.getAsMention() + " für " + reason + " verwarn!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }
    }
}
