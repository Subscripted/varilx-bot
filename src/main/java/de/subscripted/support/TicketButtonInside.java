package de.subscripted.support;


import de.subscripted.Main;
import de.subscripted.sql.TicketSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TicketButtonInside extends ListenerAdapter {
    TicketSQLManager ticketSQLManager;

    public TicketButtonInside(TicketSQLManager ticketSQLManager) {
        this.ticketSQLManager = ticketSQLManager;
    }

    private boolean claimed = false;
    private Member claimer = null;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("unclaim")) return;
        Member member = event.getMember();
        assert member != null;
        TextChannel textChannel = event.getChannel().asTextChannel();
        Role teamRole = event.getGuild().getRoleById("1095297340715319356");
        if (!member.getRoles().contains(teamRole)) {
            event.reply("Du kannst keine Tickets freigeben.").setEphemeral(true).queue();
            return;
        }
        if (!textChannel.getName().endsWith("ticket")) {
            event.reply("Dies ist kein Ticket-Kanal.").setEphemeral(true).queue();
            return;
        }
        if (textChannel.getPermissionOverride(member) != null) {
            textChannel.getManager()
                    .putPermissionOverride(teamRole, EnumSet.of(Permission.VIEW_CHANNEL), null)
                    .removePermissionOverride(member)
                    .queue();
            claimed = false;
            claimer = null;
            EmbedBuilder builder = new EmbedBuilder()
                    .setDescription("Das Ticket wurde von " + member.getAsMention() + " freigegeben!")
                    .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setColor(Color.GREEN);
            event.replyEmbeds(builder.build()).queue();
        } else
            event.reply("Das Ticket ist nicht geclaimt.").setEphemeral(true).queue();
    }
}

