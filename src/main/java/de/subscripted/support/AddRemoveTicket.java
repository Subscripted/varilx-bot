package de.subscripted.support;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.EnumSet;


public class AddRemoveTicket extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("addusertoticket")) {
            Role teamRole = event.getGuild().getRoleById("1003618027037786205");
            TextChannel textChannel = event.getChannel().asTextChannel();

            if (!event.getMember().getRoles().contains(teamRole)) {
                event.reply("Du kannst keine Nutzer zu Tickets hinzufügen.").setEphemeral(true).queue();
                return;
            }

            if (!textChannel.getName().endsWith("ticket")) {
                event.reply("Dies ist kein Ticket-Kanal.").setEphemeral(true).queue();
                return;
            }

            Member mentionedMember = event.getOption("user").getAsMember();
            Member member = event.getMember();

            if (mentionedMember.hasPermission(textChannel, Permission.VIEW_CHANNEL)) {
                event.reply(mentionedMember.getAsMention() + " kann dieses Ticket bereits sehen!").setEphemeral(true).queue();
                return;
            }

            textChannel.getManager().putPermissionOverride(mentionedMember, EnumSet.of(Permission.VIEW_CHANNEL), null).queue();


            EmbedBuilder builder = new EmbedBuilder()
                    .setDescription("Folgende Nutzer wurde von " + member.getAsMention() + " zu dem Ticket hinzugefügt: " + mentionedMember.getAsMention())
                    .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setColor(Color.GREEN);

            EmbedBuilder builder2 = new EmbedBuilder()
                    .setDescription("Du wurdest zu " + event.getChannel().getName() + " hinzugefügt!")
                    .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setColor(Color.GREEN);

            event.reply(mentionedMember.getAsMention()).addEmbeds(builder.build()).queue();

            mentionedMember.getUser().openPrivateChannel().complete().sendMessageEmbeds(builder2.build());

        } else if (event.getName().equals("removeuserfromticket")) {
            Role teamRole = event.getGuild().getRoleById("1003618027037786205");
            TextChannel textChannel = event.getChannel().asTextChannel();

            if (!event.getMember().getRoles().contains(teamRole)) {
                event.reply("Du kannst keine Nutzer von Tickets entfernen.").setEphemeral(true).queue();
                return;
            }

            if (!textChannel.getName().endsWith("ticket")) {
                event.reply("Dies ist kein Ticket-Kanal.").setEphemeral(true).queue();
                return;
            }

            Member mentionedMember = event.getOption("user").getAsMember();


            textChannel.getManager().removePermissionOverride(mentionedMember).queue();
            Member member = event.getMember();


            EmbedBuilder builder = new EmbedBuilder()
                    .setDescription("Folgende Nutzer wurden von " + member.getAsMention() + " aus dem Ticket entfernt: " + mentionedMember.getAsMention())
                    .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setColor(Color.YELLOW);
            event.replyEmbeds(builder.build()).queue();
        }
    }
}


