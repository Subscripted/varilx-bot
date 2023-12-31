package de.subscripted.user;

import java.awt.*;
        import java.util.Arrays;
        import java.util.List;
        import java.util.regex.Pattern;
        import java.util.stream.Collectors;
        import de.subscripted.Main;
        import net.dv8tion.jda.api.EmbedBuilder;
        import net.dv8tion.jda.api.Permission;
        import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
        import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Chatfilter extends ListenerAdapter {

    private final List<String> BAD_WORDS = Arrays.asList(        "Assi",
            "NIGGER",
            "nigger",
            "Nigger",
            "Neger",
            "NEGER",
            "neger",
            "Heil Hitler",
            "Arschloch",
            "arschloch",
            "Arsch",
            "arsch",
            "Bastard",
            "bastard",
            "Bummsfehler",
            "bummsfehler",
            "Ballsack",
            "ballsack",
            "Dildo",
            "dildo",
            "Fuck You",
            "fuck You",
            "FuckYou",
            "fuckYou",
            "Giganigga",
            "giganigga",
            "Geplatzteskondom",
            "geplatzteskondom",
            "Hure",
            "hure",
            "Hurensohn",
            "Mongolischeraffenzchtverein",
            "mongolischeraffenzchtverein",
            "nigger",
            "NIGGER",
            "Nigger",
            "Niger",
            "NIGER",
            "dreckssau",
            "für AFD",
            "Karbonaterol",
            "hurensohn",
            "Karbonat erol",
            "karbonat erol",
            "Leck mich",
            "leck mich",
            "Opfer",
            "opfer",
            "Titten",
            "titten",
            "Wixe",
            "wixe",
            "Wixer",
            "wixer",
            "wixxer",
            "Wixxer",
            "WIXXER",
            "sex",
            "SEX",
            "Sex",
            "✡",
            "☭",
            "✯",
            "☮",
            "Ⓐ",
            "卐",
            "卍",
            "✙",
            "ᛋᛋ",
            "ꖦ",
            "DeRio");

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor() == null) {
            return;
        }

        Member member = event.getMember();
        if (member == null) {
            return;
        }

        if (member.hasPermission(Permission.ADMINISTRATOR)) {
            return;
        }

        String messageContent = event.getMessage().getContentRaw().toLowerCase();

        List<String> triggeredBadWords = BAD_WORDS.stream()
                .filter(badWord -> {
                    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(badWord) + "\\b");
                    return pattern.matcher(messageContent).find();
                })
                .collect(Collectors.toList());

        if (!triggeredBadWords.isEmpty()) {
            EmbedBuilder dmembed = new EmbedBuilder()
                    .setTitle("Varilx Warnung!")
                    .setColor(Color.RED)
                    .setDescription("Deine Letzte Nachricht auf " + event.getGuild().getName() + " enthielt dieses ungewollte Wort: **" + triggeredBadWords.get(0) + "**")
                    .addField("Gesamte Message: ", " " + messageContent, false)
                    .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png")
                    .addField("Weiteres: ", "Diese Message wird geloggt, und kann später gegen dich verwendet werden!", false)
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);

            EmbedBuilder channelem = new EmbedBuilder()
                    .setTitle("Varilx Warnung!")
                    .setColor(Color.RED)
                    .setDescription(event.getMember().getAsMention() + " letzte message enthielt dieses ungewollte Wort: **" + triggeredBadWords.get(0) + "**")
                    .addField("Gesamte Message: ", " " + messageContent, false)
                    .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);

            event.getMessage().delete().queue();

            event.getAuthor().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessageEmbeds(dmembed.build()).queue();
                TextChannel channel = event.getGuild().getTextChannelById("1134461790818947122");
                if (channel != null) {
                    channel.sendMessageEmbeds(channelem.build()).queue();
                }
            });
        }
    }
}