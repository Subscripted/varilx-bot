package de.subscripted.support;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class TicketButtonInteraction extends ListenerAdapter {
    private Map<String, Integer> userTicketCount = new HashMap<>();

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String problem = event.getValue("problem").getAsString();
        String userId = event.getUser().getId();
        int ticketCount = userTicketCount.getOrDefault(userId, 0);

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

                Guild guild = event.getGuild();
                guild.createTextChannel(member + "s Ticket", category)
                        .addPermissionOverride(member2, EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(teamRole, EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(everyoneRole, null, EnumSet.of(Permission.VIEW_CHANNEL)).queue(channel -> {
                            String user = event.getMember().getUser().getAsMention();
                            channel.sendMessage(user).queue();
                            channel.sendMessage(teamRole.getAsMention()).queue();
                            channel.sendMessageEmbeds(embedBuilderuwu.build()).setActionRow(button, claim).queue();
                        });
                userTicketCount.put(userId, ticketCount + 1);
            }
            interactionHook.editOriginal("Ticket erstellt.").queue();
        });

    }
}
