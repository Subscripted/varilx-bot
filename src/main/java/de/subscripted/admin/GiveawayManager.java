package de.subscripted.admin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.subscripted.Main;
import de.subscripted.backend.TimeStampMaker;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class GiveawayManager {

    @SneakyThrows
    public static void create(Member member, String prize, int winners, int endTime, String channelId, String messageId) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("channelId", channelId);
        jsonObject.addProperty("messageId", messageId);
        jsonObject.addProperty("prize", prize);
        jsonObject.addProperty("creatorId", member.getId());
        jsonObject.addProperty("winners", winners);
        jsonObject.addProperty("endTime", endTime);

        File file = new File("giveaways", messageId + ".json");
        file.getParentFile().mkdirs();
        file.createNewFile();

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toString());
        }
    }

    @SneakyThrows
    public static Giveaway getGiveaway(String id) {

        File file = new File("giveaways", id + ".json");
        FileReader fileReader = new FileReader(file);

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(fileReader);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        fileReader.close();

        User user = Main.getJda().getUserById(jsonObject.get("creatorId").getAsString());
        String prize = jsonObject.get("prize").getAsString();
        String channelId = jsonObject.get("channelId").getAsString();
        int winners = jsonObject.get("winners").getAsInt();
        int endTime = jsonObject.get("endTime").getAsInt();

        List<String> users = new ArrayList<>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("joinGiveaway");
        if (jsonArray != null) {
            for (JsonElement joinGiveaway : jsonArray) {
                users.add(joinGiveaway.getAsString());
            }
        }

        return new Giveaway(user, prize, channelId, id, winners, endTime, users);
    }

    @SneakyThrows
    public static List<Giveaway> getGiveaways() {

        List<Giveaway> giveawayList = new ArrayList<>();

        File file = new File("giveaways");
        File[] files = file.listFiles();
        if (files == null) {
            return giveawayList;
        }
        for (File listFile : files) {

            FileReader fileReader = new FileReader(listFile);

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(fileReader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            fileReader.close();

            User user = Main.getJda().retrieveUserById(jsonObject.get("creatorId").getAsString()).complete();
            String prize = jsonObject.get("prize").getAsString();
            String channelId = jsonObject.get("channelId").getAsString();
            String messageId = listFile.getName().replace(".json", "");
            int winners = jsonObject.get("winners").getAsInt();
            int endTime = jsonObject.get("endTime").getAsInt();

            List<String> users = new ArrayList<>();
            JsonArray jsonArray = jsonObject.getAsJsonArray("joinGiveaway");
            if (jsonArray != null) {
                for (JsonElement joinGiveaway : jsonArray) {
                    users.add(joinGiveaway.getAsString());
                }
            }

            giveawayList.add(new Giveaway(user, prize, channelId, messageId, winners, endTime, users));
        }

        return giveawayList;
    }

    @SneakyThrows
    public static void updateGiveaway(Giveaway giveaway) {

        File file = new File("giveaways", giveaway.getMessageId() + ".json");

        FileWriter fileWriter = new FileWriter(file);

        User user = giveaway.getCreator();
        String channelId = giveaway.getChannelId();
        String prize = giveaway.getPrize();
        int winners = giveaway.getWinners();
        int endTime = giveaway.getEndTime();

        JsonArray jsonArray = new JsonArray();
        for (String user1 : giveaway.getUsers()) {
            jsonArray.add(user1);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("channelId", channelId);
        jsonObject.addProperty("prize", prize);
        jsonObject.addProperty("creatorId", user.getId());
        jsonObject.addProperty("winners", winners);
        jsonObject.addProperty("endTime", endTime);
        jsonObject.add("joinGiveaway", jsonArray);

        fileWriter.write("");
        fileWriter.write(jsonObject.toString());
        fileWriter.close();
    }

    public static void endGiveaway(Giveaway giveaway) {
        String prize = giveaway.getPrize();
        User user = giveaway.getCreator();
        List<String> memberIds = giveaway.getUsers();

        int winners = giveaway.getWinners();

        List<String> giveawayWinners = getWinners(memberIds, winners);
        StringBuilder winner = new StringBuilder();
        if (giveawayWinners.isEmpty()) {
            winner = new StringBuilder("Niemand");
        } else {
            for (String giveawayWinner : giveawayWinners) {
                winner.append("<@").append(giveawayWinner).append(">").append(" ");
            }
        }

        StringBuilder finalWinner = winner;

        TextChannel textChannel = Main.getJda().getTextChannelById(giveaway.getChannelId());
        if (textChannel == null) {
            return;
        }
        textChannel.retrieveMessageById(giveaway.getMessageId()).queue(message -> message.editMessageEmbeds(
                        new EmbedBuilder()
                                .setTitle("<a:Vaxparty:1137213809128382474> Giveaway <a:Vaxparty:1137213809128382474>")
                                .setFooter("Varilx Giveway | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                                .setColor(Color.DARK_GRAY)
                                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                                .setDescription("- Gestartet von: " + user.getAsMention() + "\n" + "- Was wurde verlost: **" + prize + "** \n" + "- Gewinner: " + finalWinner + "\n- Restzeit: **ABGELAUFEN**" + "\n" + "- Wurde beendet am: <t:" + TimeStampMaker.getTime(0) + ":R>\n" + "- Teilnehmer: " + giveaway.getUsers().size())
                                .setTimestamp(OffsetDateTime.now(Clock.systemUTC()))
                                .build())
                .setComponents(message.getActionRows().get(0).asDisabled())
                .queue());

        File file = new File("giveaways", giveaway.getMessageId() + ".json");
        if (file.delete()) {
            GiveawayRunnable.updateRunnable();
        }
    }

    private static List<String> getWinners(List<String> users, int winners) {

        List<String> winner = new ArrayList<>();

        if (users.isEmpty()) {
            return winner;
        }

        Random random = new Random();

        for (int x = 0; x < winners; x++) {
            int randomInt = random.nextInt(users.size());
            if (!winner.contains(users.get(randomInt))) {
                winner.add(users.get(randomInt));
            }
        }

        return winner;
    }

    public static boolean isInGiveaway(Giveaway giveaway, String userId) {
        List<String> users = giveaway.getUsers();
        return users.contains(userId);
    }

    public static void updateEmbed(Giveaway giveaway) {
        Main.getJda().getTextChannelById(giveaway.getChannelId()).retrieveMessageById(giveaway.getMessageId()).queue(message -> {
            message.editMessageEmbeds(
                    new EmbedBuilder()
                    .setTitle("<a:Vaxparty:1137213809128382474> Giveaway <a:Vaxparty:1137213809128382474>")
                    .setFooter("Varilx Giveway | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setColor(Color.GREEN)
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                    .setDescription("- Gestartet von: " + giveaway.getCreator().getAsMention() + "\n" + "- Was wird verlost: **" + giveaway.getPrize() + "** \n"  + "- Restzeit: <t:" + giveaway.getEndTime() + ":R> \n" + "- Teilnehmer: " + giveaway.getUsers().size())
                    .setTimestamp(OffsetDateTime.now(Clock.systemUTC()))
                    .build()).queue();
        });
    }
}
