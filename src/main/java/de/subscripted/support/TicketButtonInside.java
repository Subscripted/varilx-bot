package de.subscripted.support;

// Import statements...

import de.subscripted.Main;
import de.subscripted.Unused.TicketSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

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

    private final Map<String, Boolean> closeButtonCooldownMap = new HashMap<>(); // Channel ID -> Cooldown status
    private boolean claimed = false;
    private Member claimer = null;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Member member = event.getMember();
        TextChannel channel = event.getChannel().asTextChannel();
        Guild guild = channel.getGuild();

        switch (event.getComponentId()) {
            case "ticket_closed":
                String channelId = channel.getId();
                if (closeButtonCooldownMap.containsKey(channelId) && closeButtonCooldownMap.get(channelId)) {
                    event.deferReply().setContent("Whoa, mach nicht so schnell, ich komm doch sonst garnicht hinterher :>.").setEphemeral(true).queue();
                    return;
                }

                String ticketName = channel.getName();
                String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
                String fileNamePrefix = ticketName + "_" + currentDate;

                File logFolder = new File("Ticket-Log");
                if (!logFolder.exists()) {
                    logFolder.mkdir();
                }

                List<Message> messages = channel.getHistory().retrievePast(100).complete();
                int totalMessages = messages.size();
                int fileCount = (int) Math.ceil((double) totalMessages / 100);

                for (int i = 0; i < fileCount; i++) {
                    String fileName = fileNamePrefix + "_" + (i + 1) + ".txt";
                    String filePath = logFolder.getPath() + File.separator + fileName;

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        int startIndex = i * 100;
                        int endIndex = Math.min(startIndex + 100, totalMessages);
                        List<Message> messagesSubset = messages.subList(startIndex, endIndex);

                        Collections.reverse(messagesSubset);

                        for (Message message : messagesSubset) {
                            String author = message.getAuthor().getAsTag();
                            StringBuilder content = new StringBuilder(message.getContentDisplay());

                            if (!message.getAttachments().isEmpty()) {
                                List<Message.Attachment> attachments = message.getAttachments();
                                for (Message.Attachment attachment : attachments) {
                                    content.append("\n").append(attachment.getUrl());

                                }
                            }

                            writer.write(author + " -> " + content.toString());
                            writer.newLine();
                        }

                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                executorService.schedule(() -> channel.delete().queue(), 5, TimeUnit.SECONDS);

                closeButtonCooldownMap.put(channelId, true);
                executorService.schedule(() -> closeButtonCooldownMap.put(channelId, false), 5, TimeUnit.SECONDS);

                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx Support")
                        .setDescription("Das Ticket wird in 5 Sekunden geschlossen!")
                        .setColor(Color.GREEN)
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder.build()).queue();

                TextChannel targetChannel = guild.getTextChannelById("1078246125217263697");
                if (targetChannel != null) {
                    for (int i = 0; i < fileCount; i++) {
                        String fileName = fileNamePrefix + "_" + (i + 1) + ".txt";
                        String filePath = logFolder.getPath() + File.separator + fileName;
                        File fileToSend = new File(filePath);

                        Set<User> contributingUsers = new HashSet<>();
                        for (Message message : messages) {
                            contributingUsers.add(message.getAuthor());
                        }

                        StringBuilder contributingUsersText = new StringBuilder();
                        for (User user : contributingUsers) {
                            if (!user.isBot()) {
                                contributingUsersText.append(user.getAsMention()).append("\n");
                            }
                        }
                        StringBuilder ticketContent = new StringBuilder();

                        for (Message message : messages) {
                            String author = message.getAuthor().getAsMention();
                            StringBuilder content = new StringBuilder(message.getContentDisplay());

                            if (!message.getAttachments().isEmpty()) {
                                List<Message.Attachment> attachments = message.getAttachments();
                                for (Message.Attachment attachment : attachments) {
                                    content.append("\n").append(attachment.getUrl());
                                }
                            }

                            ticketContent.append(author).append(" -> ").append(content.toString()).append("\n");
                        }

                        String claimerID = ticketSQLManager.getClaimerID(channel.getId());
                        Member claimer = null;
                        if (claimerID != null) {
                            claimer = guild.getMemberById(claimerID);
                        }


                        EmbedBuilder ticketInfoEmbed = new EmbedBuilder()
                                .setTitle("Ticket Information")
                                .addField("Ticket Name ", ticketName, false)
                                .addField("Geschlossen von ", member.getAsMention(), false)
                                .addField("Claimer", claimer != null ? claimer.getAsMention() : "\uD83D\uDFE5", false)
                                .addField("Mitglieder :", contributingUsersText != null ? contributingUsersText.toString() : "\uD83D\uDFE5", false)
                                .setColor(Color.GREEN)
                                .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                                .setDescription(ticketContent.toString());

                        targetChannel.sendMessageEmbeds(ticketInfoEmbed.build()).queue();
                        TicketSQLManager.deleteClaimInfo(channelId);

                    }
                    event.deferEdit().queue();
                }
                break;

            case "claim":
                Role teamRole = event.getGuild().getRoleById("1095297340715319356");
                TextChannel textChannel = event.getChannel().asTextChannel();
                if (!member.getRoles().contains(teamRole)) {
                    event.reply("Du kannst keine Tickets beanspruchen.").setEphemeral(true).queue();
                    return;
                }
                if (textChannel.getPermissionOverride(teamRole) != null) {
                    textChannel.getManager()
                            .removePermissionOverride(teamRole)
                            .putPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL), null)
                            .queue();
                    claimed = true;
                    claimer = member;
                    EmbedBuilder builder = new EmbedBuilder()
                            .setDescription("Das Ticket wurde von " + member.getAsMention() + " beansprucht!")
                            .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                            .setColor(Color.CYAN);
                    event.replyEmbeds(builder.build()).queue();
                    TicketSQLManager.saveClaimInfo(textChannel.getId() , claimer.getId());
                } else {
                    event.reply("Das Ticket wurde bereits geclaimt.").setEphemeral(true).queue();
                }
                break;
        }
    }


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
        } else if (claimed == true && member.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder builder = new EmbedBuilder()
                    .setDescription("Das Ticket wurde von " + member.getAsMention() + " freigegeben! : Staff Freigabe")
                    .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setColor(Color.WHITE);
            event.replyEmbeds(builder.build()).queue();
        } else
            event.reply("Das Ticket ist nicht geclaimt.").setEphemeral(true).queue();
    }
}

