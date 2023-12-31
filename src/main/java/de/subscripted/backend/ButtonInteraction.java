package de.subscripted.backend;

import de.subscripted.Main;
import de.subscripted.admin.Giveaway;
import de.subscripted.admin.GiveawayManager;
import de.subscripted.admin.GiveawayRunnable;
import de.subscripted.sql.TicketCountSQLManager;
import de.subscripted.sql.TicketSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ButtonInteraction extends ListenerAdapter {

    ///
    private Map<String, Map<String, Instant>> userButtonCooldowns = new HashMap<>();
    private final int maxTicketsPerUser = 3;
    private final Map<String, Boolean> closeButtonCooldownMap = new HashMap<>();
    private boolean claimed = false;
    private Member claimer = null;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
    TicketSQLManager ticketSQLManager;
    TicketCountSQLManager ticketCountSQLManager;
    ///

    ///
    public ButtonInteraction(TicketSQLManager ticketSQLManager, TicketCountSQLManager ticketCountSQLManager) {

        this.ticketSQLManager = ticketSQLManager;
        this.ticketCountSQLManager = ticketCountSQLManager;
    }
    ///

    public void onButtonInteraction(ButtonInteractionEvent event) {


        Member member = event.getMember();
        TextChannel channel = event.getChannel().asTextChannel();
        String userId = event.getUser().getId();

        Role Event = event.getGuild().getRoleById("1098654469710954728");
        Role Changelog = event.getGuild().getRoleById("1098654811030831144");
        Role Info = event.getGuild().getRoleById("1098654568658776124");
        Role bedrock = event.getGuild().getRoleById("1098652243609272361");
        Role java = event.getGuild().getRoleById("1098652312307769375");
        Guild guild = event.getGuild();


        switch (event.getButton().getId()) {
            case "accepted_vorschlag":
                event.deferEdit().queue();
                event.getHook().editOriginal(event.getMember().getAsMention() + " hat den Vorschlag angenommen!").queue();
                List<MessageEmbed> originalEmbeds = event.getMessage().getEmbeds();
                if (!originalEmbeds.isEmpty()) {
                    MessageEmbed originalEmbed = originalEmbeds.get(0);
                    List<MessageEmbed.Field> updatedFields = new ArrayList<>();
                    for (MessageEmbed.Field field : originalEmbed.getFields()) {
                        if (field.getName().equals("AntiSpam!")) {
                            updatedFields.add(new MessageEmbed.Field("Vorschlag:", field.getValue(), field.isInline()));
                        } else {
                            updatedFields.add(field);
                        }
                    }
                    EmbedBuilder newEmbed = new EmbedBuilder(originalEmbed)
                            .clearFields()
                            .setColor(Color.GREEN)
                            .setTitle("Vorschlag angenommen   :white_check_mark:");
                    for (MessageEmbed.Field field : updatedFields) {
                        newEmbed.addField(field);
                    }
                    TextChannel targetChannel = event.getGuild().getTextChannelById("1062410932267003954");
                    if (targetChannel != null) {
                        targetChannel.sendMessage(event.getGuild().getRoleById("1098654811030831144").getAsMention()).addEmbeds(newEmbed.build()).queue();
                    }
                }
                break;

            case "denied_vorschlag":
                event.deferEdit().queue();
                event.getHook().editOriginal(event.getMember().getAsMention() + " hat den Vorschlag abgelehnt!").queue();
                List<MessageEmbed> originalEmbeds2 = event.getMessage().getEmbeds();
                if (!originalEmbeds2.isEmpty()) {
                    MessageEmbed originalEmbed = originalEmbeds2.get(0);
                    List<MessageEmbed.Field> updatedFields = new ArrayList<>();
                    for (MessageEmbed.Field field : originalEmbed.getFields()) {
                        if (field.getName().equals("AntiSpam!")) {
                            updatedFields.add(new MessageEmbed.Field("Vorschlag:", field.getValue(), field.isInline()));
                        } else {
                            updatedFields.add(field);
                        }
                    }
                    EmbedBuilder newEmbed = new EmbedBuilder(originalEmbed)
                            .clearFields()
                            .setColor(Color.RED)
                            .setTitle("Vorschlag abgelehnt!   :no_entry_sign: ");
                    for (MessageEmbed.Field field : updatedFields) {
                        newEmbed.addField(field);
                    }
                    TextChannel targetChannel = event.getGuild().getTextChannelById("1062410932267003954");
                    if (targetChannel != null) {
                        targetChannel.sendMessageEmbeds(newEmbed.build()).queue();
                    }
                }
                break;
            case "delete_vorschlag":
                event.getHook().deleteOriginal().queue();
                event.deferEdit().queue();
                break;
            case "vorschlag":
                Instant lastVorschlagTime = userButtonCooldowns
                        .computeIfAbsent(userId, k -> new HashMap<>())
                        .getOrDefault("vorschlag", null);

                if (lastVorschlagTime == null || lastVorschlagTime.isBefore(Instant.now().minusSeconds(1800))) {
                    Role role = event.getGuild().getRoleById("1134159388190462042");
                    if (event.getMember().getRoles().contains(role)) {
                        EmbedBuilder embedBuilderac = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("Varilx Support")
                                .setDescription("Du bist gemutet. Um dich entmuten zu lassen, erstelle ein neues Thema auf unserem Forum!")
                                .setFooter("Varilx Support Feature | Update 2023 ©", Main.redfooter);
                        event.replyEmbeds(embedBuilderac.build()).setEphemeral(true).queue();
                    }


                    TextInput message = TextInput.create("vorschlag", "Was ist dein Vorschlag / deine Idee?", TextInputStyle.PARAGRAPH).setPlaceholder("z.B. ''Neue Items''")
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
                    userButtonCooldowns
                            .computeIfAbsent(userId, k -> new HashMap<>())
                            .put("vorschlag", Instant.now());
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
                if (member.getRoles().contains(Changelog)) {
                    assert Changelog != null;
                    guild.removeRoleFromMember(member, Changelog).queue();
                } else {
                    assert Changelog != null;
                    guild.addRoleToMember(member, Changelog).queue();
                }
                break;
            case "event":
                if (member.getRoles().contains(Event)) {
                    assert Event != null;
                    guild.removeRoleFromMember(member, Event).queue();
                } else {
                    assert Event != null;
                    guild.addRoleToMember(member, Event).queue();
                }
                break;

            case "info":
                if (member.getRoles().contains(Info)) {
                    assert Info != null;
                    guild.removeRoleFromMember(member, Info).queue();
                } else {
                    assert Info != null;
                    guild.addRoleToMember(member, Info).queue();
                }
                break;

            case "bedrock":
                if (member.getRoles().contains(bedrock)) {
                    assert bedrock != null;
                    guild.removeRoleFromMember(member, bedrock).queue();
                } else {
                    assert bedrock != null;
                    guild.addRoleToMember(member, bedrock).queue();
                }
                break;

            case "java":
                if (member.getRoles().contains(java)) {
                    assert java != null;
                    guild.removeRoleFromMember(member, java).queue();
                } else {
                    assert java != null;
                    guild.addRoleToMember(member, java).queue();
                }
                break;

            case "feedbackbutton":
                Instant lastFeedBackTime = userButtonCooldowns
                        .computeIfAbsent(userId, k -> new HashMap<>())
                        .getOrDefault("feedbackbutton", null);

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

                    TextInput message = TextInput.create("feedback", "Dein Feedback", TextInputStyle.PARAGRAPH).setPlaceholder("z.B. ''Varilx ist der Beste Server''")
                            .setMinLength(5)
                            .setMaxLength(4000)
                            .setRequired(true)
                            .build();

                    Modal modal = Modal.create("vaxfeedback", "Varilx Feedback")
                            .addActionRow(message)
                            .build();

                    event.replyModal(modal).queue();
                    userButtonCooldowns
                            .computeIfAbsent(userId, k -> new HashMap<>())
                            .put("feedbackbutton", Instant.now());
                } else {
                    event.reply("Du kannst nur alle 30 Minuten einen Vorschlag machen.").setEphemeral(true).queue();
                }
                break;
            case "report_closed":
                event.getChannel().deleteMessageById(event.getMessageId()).queue();
                break;

            case "create":

                String userId2;
                try {
                    userId2 = TicketCountSQLManager.getUserID();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                int ticketCount = ticketCountSQLManager.getCount(userId2);
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
                int ticketCount2 = TicketCountSQLManager.getCount(userId);

                if (ticketCount2 != 0) {
                    String userId3 = null;
                    try {
                        userId3 = TicketCountSQLManager.getUserID();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    TicketCountSQLManager.removeCount(userId3);
                }

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
                                .setAuthor("Varilx.de | Ticket")
                                .addField("<:varilx_textchannel:1139957022696157294> Ticket Name ", ticketName, false)
                                .addField("<:varilx_clendar:1139956980576960653> Geschlossen von : ", member.getAsMention(), true)
                                .addField("<:varilx_user:1139957321196376107> Claimer : ", claimer != null ? claimer.getAsMention() : "- **Nicht geclaimt**", true)
                                .addField("<:varilx_user:1139957321196376107> Supporter :", contributingUsersText != null ? contributingUsersText.toString() : "\uD83D\uDFE5", true)
                                .setColor(Color.GREEN)
                                .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                                .setDescription(ticketContent.toString() + "\n**~~---»-----------------------------------------«---~~**");
                        targetChannel.sendMessageEmbeds(ticketInfoEmbed.build()).queue();
                        TicketSQLManager.deleteClaimInfo(channelId);
                    }
                    event.deferEdit().queue();
                }
                break;

            case "claim":
                Role teamRole = event.getGuild().getRoleById("1095297340715319356");
                Role SrSupporter = event.getGuild().getRoleById("1081305944652255292");
                TextChannel textChannel = event.getChannel().asTextChannel();
                if (!member.getRoles().contains(teamRole)) {
                    event.reply("Du kannst keine Tickets beanspruchen.").setEphemeral(true).queue();
                    return;
                }
                if (textChannel.getPermissionOverride(teamRole) != null) {
                    textChannel.getManager()
                            .removePermissionOverride(teamRole)
                            .putPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL), null)
                            .putPermissionOverride(SrSupporter, EnumSet.of(Permission.VIEW_CHANNEL), null)
                            .queue();
                    claimed = true;
                    claimer = member;
                    EmbedBuilder builder = new EmbedBuilder()
                            .setDescription("Das Ticket wurde von " + member.getAsMention() + " beansprucht!")
                            .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                            .setColor(Color.CYAN);
                    event.replyEmbeds(builder.build()).queue();
                    TicketSQLManager.saveClaimInfo(textChannel.getId(), claimer.getId());
                } else {
                    event.reply("Das Ticket wurde bereits geclaimt.").setEphemeral(true).queue();
                }
                break;

            case "erledigt_support":
                VoiceChannel voiceChannel1 = guild.getVoiceChannelById("1078320408199168131");
                Member interactionMember = event.getMember();
                if (interactionMember.getVoiceState().getChannel() == null || !interactionMember.getVoiceState().getChannel().equals(voiceChannel1)) {
                    EmbedBuilder embedBuilder1 = new EmbedBuilder()
                            .setTitle("Varilx Support")
                            .setColor(Color.YELLOW)
                            .setDescription("Du musst dich in " + voiceChannel1.getAsMention() + " befinden, um den Support dieses Users als abgeschlossen zu aktzeptieren!")
                            .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                    event.replyEmbeds(embedBuilder1.build()).setEphemeral(true).queue();
                    return;
                }
                event.getInteraction().getMessage().delete().queue();

                EmbedBuilder embedBuilder1 = new EmbedBuilder()
                        .setTitle("Varilx Support")
                        .setColor(Color.GREEN)
                        .setDescription("Du hast dich um den Support gekümmert? Dann bestätige dies doch bitte mit dem Knopf unter dieser Nachricht!")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

                Button button = Button.success("erledigterdmsupport", "Support abgeschlossen!");

                event.getInteraction().getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder1.build()).addActionRow(button).queue();
                break;


            case "erledigterdmsupport":
                Member member1 = event.getInteraction().getMember();
                EmbedBuilder embedBuilder2 = new EmbedBuilder()
                        .setTitle("Varilx Support")
                        .setColor(Color.GREEN)
                        .setDescription(member1.getAsMention() + " hat sich um einen VoiceSupport gekümmert!")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                Guild guild1 = event.getJDA().getGuildById("886262410489520168");
                guild1.getTextChannelById("1153965718275108914").sendMessageEmbeds(embedBuilder2.build()).queue();
                event.getInteraction().getMessage().delete().queue();
                break;

            case "giveaway_join":
                if (member == null) {
                    return;
                }


                String messageId = event.getMessageId();
                Giveaway giveaway = GiveawayManager.getGiveaway(messageId);

                if (!GiveawayManager.isInGiveaway(giveaway, member.getId())) {

                    giveaway.getUsers().add(member.getId());

                    GiveawayManager.updateGiveaway(giveaway);
                    GiveawayRunnable.updateRunnable();
                    GiveawayManager.updateEmbed(giveaway);
                    event.reply("Du bist jetzt im Giveaway!").setEphemeral(true).queue();
                } else {
                    EmbedBuilder embedBuilder4 = new EmbedBuilder()
                            .setTitle("Varilx Giveaway")
                            .setColor(Color.yellow)
                            .setDescription("Du nimmst bereits an diesem Giveway teil! ")
                            .setFooter("Varilx Giveaway Feature | Update 2023 ©", Main.getJda().getSelfUser().getAvatarUrl());
                    event.replyEmbeds(embedBuilder4.build()).setEphemeral(true).queue();
                }
                break;
        }
        if (!event.isAcknowledged())
            event.deferEdit().queue();
    }
}
