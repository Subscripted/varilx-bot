package de.subscripted.working;

import de.subscripted.Main;
import de.subscripted.sql.TicketCountSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.util.List;

public class TicketDropDown extends ListenerAdapter {
    private final int maxTicketsPerUser = 3;
    TicketCountSQLManager ticketCountSQLManager;


    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().equalsIgnoreCase("!ticketsc"))
            return;


        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
            return;

        StringSelectMenu menu = StringSelectMenu.create("menu:class")
                .setPlaceholder("Such dir dein Ticket aus")
                .setRequiredRange(1, 1)
                .addOption("Support", "mage-arcane")
                .addOption("Bugreport", "mage-fire")
                .addOption("Generelles", "mage-frost")
                .build();

        event.getMessage().reply("Please pick your class below")
                .addActionRow(menu)
                .queue();
    }


    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (!event.getInteraction().getId().equalsIgnoreCase("menu:class"))
            return;
        String userId = event.getMember().getId();
        int ticketCount = TicketCountSQLManager.getCount(userId);
        List<SelectOption> selectedOptions = event.getSelectedOptions();
        if (selectedOptions.equals("mage-arcane")) {
            if (ticketCount == 3) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Support")
                        .setDescription("Du hast bereits das Maximum an Tickets erstellt! (" + maxTicketsPerUser + "). Bitte schließe ein paar deiner Tickets.")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            Role role4 = event.getGuild().getRoleById("1134159388190462042");
            if (event.getMember().getRoles().contains(role4)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Support")
                        .setDescription("Du bist gemutet. Um dich entmuten zu lassen, erstelle ein neues Thema auf unserem Forum!")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            TextInput problem = TextInput.create("problem", "Kurze Vorbeschreibung deines Problems", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Ich kann kein /help machen''")
                    .setMinLength(5)
                    .setMaxLength(999)
                    .setRequired(true)
                    .build();
            TextInput ign = TextInput.create("ign", "Dein Ingame Name", TextInputStyle.SHORT).setPlaceholder("z.b. ''Subscripted''")
                    .setMinLength(5)
                    .setMaxLength(999)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("ticket", "Ticket")
                    .addActionRow(problem).addActionRow(ign)
                    .build();

            event.replyModal(modal).queue();
        } else if (selectedOptions.equals("mage-fire")) {
            if (ticketCount == 3) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Support")
                        .setDescription("Du hast bereits das Maximum an Tickets erstellt! (" + maxTicketsPerUser + "). Bitte schließe ein paar deiner Tickets.")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            Role role5 = event.getGuild().getRoleById("1134159388190462042");
            if (event.getMember().getRoles().contains(role5)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Support")
                        .setDescription("Du bist gemutet. Um dich entmuten zu lassen, erstelle ein neues Thema auf unserem Forum!")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            TextInput problem2 = TextInput.create("problem", "Kurze Vorbeschreibung deines Problems", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Ich kann kein /help machen''")
                    .setMinLength(5)
                    .setMaxLength(999)
                    .setRequired(true)
                    .build();
            TextInput ign2 = TextInput.create("ign", "Dein Ingame Name", TextInputStyle.SHORT).setPlaceholder("z.b. ''Subscripted''")
                    .setMinLength(5)
                    .setMaxLength(999)
                    .setRequired(true)
                    .build();

            Modal modal2 = Modal.create("ticket", "Ticket")
                    .addActionRow(problem2).addActionRow(ign2)
                    .build();

            event.replyModal(modal2).queue();
        } else if (selectedOptions.equals("mage-frost")) {
            if (ticketCount == 3) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Support")
                        .setDescription("Du hast bereits das Maximum an Tickets erstellt! (" + maxTicketsPerUser + "). Bitte schließe ein paar deiner Tickets.")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            Role role6 = event.getGuild().getRoleById("1134159388190462042");
            if (event.getMember().getRoles().contains(role6)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Support")
                        .setDescription("Du bist gemutet. Um dich entmuten zu lassen, erstelle ein neues Thema auf unserem Forum!")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            TextInput problem3 = TextInput.create("problem", "Kurze Vorbeschreibung deines Problems", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Ich kann kein /help machen''")
                    .setMinLength(5)
                    .setMaxLength(999)
                    .setRequired(true)
                    .build();
            TextInput ign3 = TextInput.create("ign", "Dein Ingame Name", TextInputStyle.SHORT).setPlaceholder("z.b. ''Subscripted''")
                    .setMinLength(5)
                    .setMaxLength(999)
                    .setRequired(true)
                    .build();

            Modal modal3 = Modal.create("ticket", "Ticket")
                    .addActionRow(problem3).addActionRow(ign3)
                    .build();

            event.replyModal(modal3).queue();
        }
    }
}
