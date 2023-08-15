package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GiveawayThread extends Thread {
    TextChannel channel;
    long msgId;
    long countdownInSeconds;
    EmbedBuilder builder;
    String dcusername;
    String finalGiveawayItemInput;

    public GiveawayThread(TextChannel channel, long msgId, long durationInSeconds, EmbedBuilder builder, String dcusername, String finalGiveawayItemInput) {
        this.channel = channel;
        this.msgId = msgId;
        this.countdownInSeconds = durationInSeconds;
        this.builder = builder;
        this.dcusername = dcusername;
        this.finalGiveawayItemInput = finalGiveawayItemInput;
    }

    @Override
    public void run() {
        try {
            Message msg = channel.retrieveMessageById(msgId).complete();
            Instant startTime = Instant.now();
            Instant endTime = startTime.plusSeconds(countdownInSeconds);
            String endTimeString = null;
            while (Instant.now().isBefore(endTime)) {
                Duration remainingDuration = Duration.between(Instant.now(), endTime);
                long days = remainingDuration.toDays();
                long hours = remainingDuration.toHours() % 24;
                long minutes = remainingDuration.toMinutes() % 60;
                long seconds = remainingDuration.getSeconds() % 60;

                String formattedTime = formatTime(days, hours, minutes, seconds);
                endTimeString = formatEndTime(endTime);

                EmbedBuilder updatedBuilder = new EmbedBuilder(builder)
                        .setTitle("<a:Vaxparty:1137213809128382474> Giveaway <a:Vaxparty:1137213809128382474>")
                        .setDescription("- Was wird verlost: **" + finalGiveawayItemInput + "**\n\n" + "- Gestartet von: " + dcusername + "\n" + "- Restzeit: " + formattedTime + "\n" + "- Endet am: " + endTimeString)
                        .setFooter("Varilx Giveway | Updated 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                        .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png");
                msg.editMessageEmbeds(updatedBuilder.build()).complete();
                Thread.sleep(60000);
            }
            List<MessageReaction> reactions = msg.getReactions();
            for (MessageReaction reaction : reactions) {
                if (reaction.getEmoji().getAsReactionCode().equals("\uD83C\uDF81")) {
                    List<User> users = reaction.retrieveUsers().complete();
                    users.removeIf(User::isBot);

                    if (users.size() > 0) {
                        int random = (int) (Math.random() * users.size());
                        User winner = users.get(random);

                        EmbedBuilder winnerBuilder = new EmbedBuilder(builder)
                                .setTitle("<a:Vaxparty:1137213809128382474> Giveaway <a:Vaxparty:1137213809128382474>")
                                .setFooter("Varilx Giveway | Updated 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                                .setColor(Color.DARK_GRAY)
                                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                                .setDescription("- Gestartet von: " + dcusername + "\n" + "- Was wurde verlost: **" + finalGiveawayItemInput + "** \n" + "- Gewinner: " + winner.getAsMention() + "\n- Restzeit: **ABGELAUFEN**" + "\n" + "- Wurde beendet am: " + endTimeString);
                        msg.editMessageEmbeds(winnerBuilder.build()).complete();
                        msg.reply("Herzlichen glückwunsch " + winner.getAsMention() + "! Du hast " + finalGiveawayItemInput + " gewonnen!").queue();
                        msg = channel.retrieveMessageById(msgId).complete();
                    } else {
                        EmbedBuilder noParticipantsBuilder = new EmbedBuilder(builder)
                                .setDescription("- Gestartet von: " + dcusername + "\n" + "- Was wurde verlost: **" + finalGiveawayItemInput + "**\n" + "- Gewinner: Es hat niemand teilgenommen!" + "\n- Restzeit: **ABGELAUFEN**" + "\n" + "- Wurde beendet am: " + endTimeString)
                                .setFooter("Varilx Giveway | Updated 2023 ©", Main.redfooter)
                                .setTitle("<a:Vaxparty:1137213809128382474> Giveaway <a:Vaxparty:1137213809128382474>")
                                .setColor(Color.RED);
                        msg.editMessageEmbeds(noParticipantsBuilder.build()).complete();
                        msg = channel.retrieveMessageById(msgId).complete();
                    }
                    break;
                }
            }
            msg.clearReactions().queue();
        } catch (Exception ignore) {
            channel.sendMessageEmbeds(Main.getErrorEmbed("Das Giveaway wurde abgebrochen!")).queue();
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }


    private String formatTime(long days, long hours, long minutes, long seconds) {
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append(" Tag");
            if (days > 1) {
                sb.append("e");
            }
            sb.append(" ");
        }
        if (hours > 0) {
            sb.append(hours).append(" Stunde");
            if (hours > 1) {
                sb.append("n");
            }
            sb.append(" ");
        }
        if (minutes > 0) {
            sb.append(minutes).append(" Minute");
            if (minutes > 1) {
                sb.append("n");
            }
            sb.append(" ");
        }
        if (sb.length() == 0) {
            sb.append("weniger als 1 Minute");

        }
        return sb.toString().trim();
    }


    private String formatEndTime(Instant endTimeInstant) {
        LocalDateTime endTime = LocalDateTime.ofInstant(endTimeInstant, ZoneId.systemDefault()).plusHours(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return endTime.format(formatter);
    }
}
