package de.subscripted.backend;

import de.subscripted.Main;
import de.subscripted.sql.TicketSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ButtonInteraction extends ListenerAdapter {
    private Map<String, Instant> userCooldowns = new HashMap<>();
    private Map<String, Integer> userTicketCount = new HashMap<>();
    private final int maxTicketsPerUser = 3;
    private final Map<String, Boolean> closeButtonCooldownMap = new HashMap<>();
    private boolean claimed = false;
    private Member claimer = null;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);

    TicketSQLManager ticketSQLManager;

    public ButtonInteraction(TicketSQLManager ticketSQLManager) {
        this.ticketSQLManager = ticketSQLManager;
    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
        Member member = event.getMember();
        TextChannel channel = event.getChannel().asTextChannel();

        Role Event = event.getGuild().getRoleById("1098654469710954728");
        Role Changelog = event.getGuild().getRoleById("1098654811030831144");
        Role Info = event.getGuild().getRoleById("1098654568658776124");
        Role bedrock = event.getGuild().getRoleById("1098652243609272361");
        Role java = event.getGuild().getRoleById("1098652312307769375");
        Member member3 = event.getMember();
        Guild guild = event.getGuild();


        switch (event.getButton().getId()) {
            case "accepted_vorschlag":
                event.deferEdit().queue();
                event.getHook().editOriginal(event.getMember().getAsMention() + " hat den Vorschlag angenommen!").queue();
                break;
            case "denied_vorschlag":
                Member applicant = event.getGuild().getMember(event.getUser());
                event.deferEdit().queue();
                event.getHook().editOriginal("**" + applicant.getAsMention() + "** hat den Vorschlag abgelehnt.").queue();
                break;
            case "delete_vorschlag":
                event.getHook().deleteOriginal().queue();
                event.deferEdit().queue();
                break;
            case "vorschlag":
                Role role = event.getGuild().getRoleById("1134159388190462042");
                if (event.getMember().getRoles().contains(role)) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("Varilx Support")
                            .setDescription("Du bist gemutet. Um dich entmuten zu lassen, erstelle ein neues Thema auf unserem Forum!")
                            .setFooter("Varilx Support Feature | Update 2023 ©", Main.redfooter);
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                }
                String userId = event.getUser().getId();
                Instant lastVorschlagTime = userCooldowns.get(userId);

                if (lastVorschlagTime == null || lastVorschlagTime.isBefore(Instant.now().minusSeconds(1800))) {
                    TextInput message = TextInput.create("vorschlag", "Was ist dein Vorschlag / deine Idee?", TextInputStyle.PARAGRAPH)
                            .setMinLength(5)
                            .setRequired(true)
                            .build();

                    TextInput url = TextInput.create("url", "Bild URL (Optional)", TextInputStyle.SHORT)
                            .setMinLength(5)
                            .setRequired(false)
                            .build();

                    Modal modal = Modal.create("vorschläge", "Vorschläge / Ideen")
                            .addActionRows(ActionRow.of(message), ActionRow.of(url))
                            .build();

                    event.replyModal(modal).queue();
                } else {
                    event.reply("Du kannst nur alle 30 Minuten einen Vorschlag machen.").setEphemeral(true).queue();
                }
                break;
            case "rules":
                Role role2 = event.getGuild().getRoleById("941719147883163759");
                Member member7 = event.getMember();
                if (!member7.getRoles().contains(role2)) {
                    event.getGuild().addRoleToMember(member7, role2).queue();
                    event.reply("Du hast die Regeln akzeptiert!").setEphemeral(true).queue();
                } else {
                    event.reply("Du hast die Regeln bereits akzeptiert!").setEphemeral(true).queue();
                }
                break;

            case "changelog":
                if (member3.getRoles().contains(Changelog)) {
                    assert Changelog != null;
                    guild.removeRoleFromMember(member3, Changelog).queue();
                } else {
                    assert Changelog != null;
                    guild.addRoleToMember(member3, Changelog).queue();
                    break;
                }
            case "event":
                if (member3.getRoles().contains(Event)) {
                    assert Event != null;
                    guild.removeRoleFromMember(member3, Event).queue();
                } else {
                    assert Event != null;
                    guild.addRoleToMember(member3, Event).queue();
                    break;
                }

            case "info":
                if (member3.getRoles().contains(Info)) {
                    assert Info != null;
                    guild.removeRoleFromMember(member3, Info).queue();
                } else {
                    assert Info != null;
                    guild.addRoleToMember(member3, Info).queue();
                    break;
                }

            case "bedrock":
                if (member3.getRoles().contains(bedrock)) {
                    assert bedrock != null;
                    guild.removeRoleFromMember(member3, bedrock).queue();
                } else {
                    assert bedrock != null;
                    guild.addRoleToMember(member3, bedrock).queue();
                    break;
                }

            case "java":
                if (member3.getRoles().contains(java)) {
                    assert java != null;
                    guild.removeRoleFromMember(member3, java).queue();
                } else {
                    assert java != null;
                    guild.addRoleToMember(member3, java).queue();
                    break;
                }
            case "feedbackbutton":
                String userId2 = event.getUser().getId();
                Instant lastFeedBackTime = userCooldowns.get(userId2);

                if (lastFeedBackTime == null || lastFeedBackTime.isBefore(Instant.now().minusSeconds(1800))) {
                    Role role3 = event.getGuild().getRoleById("1134159388190462042");
                    if (event.getMember().getRoles().contains(role3)) {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("Varilx Support")
                                .setDescription("Du bist gemutet. Um dich entmuten zu lassen erstell ein neues Thema auf unserem Forum!")
                                .setFooter("Varilx Support Feature | Update 2023 ©", Main.redfooter);
                        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                        return;
                    }

                    TextInput message = TextInput.create("feedback", "Dein Feedback", TextInputStyle.PARAGRAPH)
                            .setMinLength(5)
                            .setMaxLength(999)
                            .setRequired(true)
                            .build();

                    Modal modal = Modal.create("vaxfeedback", "Varilx Feedback")
                            .addActionRow(message)
                            .build();


                    event.replyModal(modal).queue();
                } else {
                    event.reply("Du kannst nur alle 30 Minuten einen Vorschlag machen.").setEphemeral(true).queue();
                }
                break;
            case "report_closed":
                    event.getChannel().deleteMessageById(event.getMessageId()).queue();
                break;

            case "create":
                    String userId4 = event.getUser().getId();
                    int ticketCount = userTicketCount.getOrDefault(userId4, 0);

                    if (ticketCount >= maxTicketsPerUser) {
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

                    TextInput problem = TextInput.create("problem", "Kurze Vorbeschreibung deines Problems", TextInputStyle.PARAGRAPH)
                            .setMinLength(5)
                            .setMaxLength(999)
                            .setRequired(true)
                            .build();

                    Modal modal = Modal.create("ticket", "Ticket")
                            .addActionRow(problem)
                            .build();

                    event.replyModal(modal).queue();
                break;
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

                java.util.List<Message> messages = channel.getHistory().retrievePast(100).complete();
                int totalMessages = messages.size();
                int fileCount = (int) Math.ceil((double) totalMessages / 100);

                for (int i = 0; i < fileCount; i++) {
                    String fileName = fileNamePrefix + "_" + (i + 1) + ".txt";
                    String filePath = logFolder.getPath() + File.separator + fileName;

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        int startIndex = i * 100;
                        int endIndex = Math.min(startIndex + 100, totalMessages);
                        java.util.List<Message> messagesSubset = messages.subList(startIndex, endIndex);

                        Collections.reverse(messagesSubset);

                        for (Message message : messagesSubset) {
                            String author = message.getAuthor().getAsTag();
                            StringBuilder content = new StringBuilder(message.getContentDisplay());

                            if (!message.getAttachments().isEmpty()) {
                                java.util.List<Message.Attachment> attachments = message.getAttachments();
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
                                .addField("Claimer : ", claimer != null ? claimer.getAsMention() : "\uD83D\uDFE5", false)
                                .addField("Mitglieder :", contributingUsersText != null ? contributingUsersText.toString() : "\uD83D\uDFE5", false)
                                .setColor(Color.GREEN)
                                .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                                .setDescription(ticketContent.toString()  +" \n\n **~~---»-----------------------------------------«---~~**");

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
        event.deferEdit().queue();

    }
}

