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
    public ModalInteractionsHandler(TicketCountSQLManager ticketCountSQLManager){
        this.ticketCountSQLManager = ticketCountSQLManager;
    }

    public void onModalInteraction(ModalInteractionEvent event) {
        String userId = event.getUser().getId();
        switch (event.getModalId()) {
            case "ticket":
                String problem = event.getValue("problem").getAsString();
                Guild guild = event.getGuild();

                event.deferReply().setEphemeral(true).queue(interactionHook -> {
                    if (event.getModalId().equals("ticket")) {
                        String member = event.getUser().getName();
                        Member member2 = event.getMember();

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

                        Category category = event.getGuild().getCategoryById("928337279376850975");

                        Role teamRole = event.getGuild().getRoleById("1095297340715319356");

                        Role everyoneRole = event.getGuild().getRolesByName("@everyone", true).get(0);

                        guild.createTextChannel(member + "s Ticket", category)
                                .addPermissionOverride(member2, EnumSet.of(Permission.VIEW_CHANNEL), null)
                                .addPermissionOverride(teamRole, EnumSet.of(Permission.VIEW_CHANNEL), null)
                                .addPermissionOverride(everyoneRole, null, EnumSet.of(Permission.VIEW_CHANNEL)).queue(channel -> {
                                    String user = event.getMember().getUser().getAsMention();
                                    channel.sendMessage(user + teamRole.getAsMention()).addEmbeds(embedBuilderuwu.build()).setActionRow(button, claim).queue();
                                    ticketCountSQLManager.addCount(userId);
                                });
                    }
                    event.reply("Dein Ticket wurde erstellt!").setEphemeral(true).queue();
                });
                break;

        }
    }
}



