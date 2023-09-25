package de.subscripted.backend;

import de.subscripted.Main;
import de.subscripted.sql.TicketCountSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.modals.Modal;
import de.subscripted.sql.TicketSQLManager; // Import der TicketSQLManager-Klasse

import java.awt.*;
import java.time.Instant;
import java.util.EnumSet;

public class ModalInteractionsHandler extends ListenerAdapter {
    TicketCountSQLManager ticketCountSQLManager;

    private final int maxTicketsPerUser = 3;
    private static final int COOLDOWN_SECONDS = 30 * 60;

    public ModalInteractionsHandler(TicketCountSQLManager ticketCountSQLManager) {
        this.ticketCountSQLManager = ticketCountSQLManager;
    }

    public void onModalInteraction(ModalInteractionEvent event) {
        String userId = event.getUser().getId();
        switch (event.getModalId()) {
            case "ticket":
                String problem = event.getValue("problem").getAsString();
                String member = event.getUser().getName();
                Guild guild = event.getGuild();
                Category category = event.getGuild().getCategoryById("928337279376850975");
                guild.createTextChannel(member + "s Ticket", category)
                        .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(guild.getRoleById("1095297340715319356"), EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL)).queue(channel -> {
                            String ticketChannelMention = channel.getAsMention();
                            Button button = Button.danger("ticket_closed", "Ticket Schließen -> 🗑️");
                            Button claim = Button.primary("claim", "Claimen -> 🔒");

                            EmbedBuilder embedBuilderuwu = new EmbedBuilder()
                                    .setColor(Color.GREEN)
                                    .setTitle("Varilx Tickets")
                                    .addField("Beschreibung des Problems", problem, false)
                                    .setDescription(" · Bitte gedulde dich ein bisschen, es wird sich bald jemand um dich kümmern.\n" +
                                            " · Sollten wir nicht erreichbar sein, melde dich bitte im Forum! \n" +
                                            "https://forum.varilx.de/forum/view/8-support/")
                                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                                    .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                            channel.sendMessage(event.getMember().getAsMention() + " " + guild.getRoleById("1095297340715319356").getAsMention()).addEmbeds(embedBuilderuwu.build()).setActionRow(button, claim).queue();
                            ticketCountSQLManager.addCount(userId);
                        });

                break;
        }
    }
}
