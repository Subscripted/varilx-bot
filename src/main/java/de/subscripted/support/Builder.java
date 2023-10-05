package de.subscripted.support;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;

public class Builder extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.getMessage().getContentRaw().equals("!ticket"))
            return;

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            System.out.println("keine rechte");
            return;
        }



        Button create = Button.success("create", "Ticket -> ✉️");
        Button partner = Button.link("https://tube-hosting.com/pricing", "Partner").withEmoji(Emoji.fromFormatted("<:TubehostingVarilx:1101657813794693120>"));
        TextChannel channel = event.getChannel().asTextChannel();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Ticket-Support")
                .setDescription("Hast du eine Frage oder brauchst du Hilfe auf Varilx?\n" +
                        "Erstelle einfach ein Ticket, indem du auf den ✉️ Button klickst.\n" +
                        "Sobald ein Teamler Zeit hat, wird er sich mit dir in Verbindung setzen.\n" +
                        "· **Reaktionszeit**\n" +
                        "Die Teamler des Varilx-Supports sind keine Roboter, sondern freiwillige Helfer. Es kann etwas dauern, bis du eine Antwort erhältst, also habe bitte Geduld. Du wirst so schnell wie möglich eine Antwort erhalten!\n" +
                        "Kein \"Hallo\"\n" +
                        "https://nohello.net/ ➡\uFE0F Schreibe mehr als nur \"Hallo\" oder \"Ich habe ein Problem\". Wir helfen dir gerne und wissen, dass du offensichtlich ein Problem oder eine Frage hast, wenn du ein Ticket öffnest!\n" +
                        "· **Informationen bereitstellen**\n" +
                        "Bitte gib uns eine detaillierte Beschreibung deines Problems! Wir benötigen möglicherweise einen Minecraft-Namen, eine Transaktions-ID, einen Screenshot oder einen Crash-Log. Wir müssen und möchten dein Problem verstehen, also hilf uns bitte dabei!\n" +
                        "· **Discord Voice Support**\n" +
                        "Wir bieten auch direkte Unterstützung über den Sprachkanal von Discord an. Um von unserem Sprachsupport zu profitieren, öffne einfach ein Ticket, beschreibe dein Problem und frage deinen Supporter nach einem Treffen!\n" +
                        "· **Hilf dir selbst**\n" +
                        "Du kannst auch unsere Support-Website (https://forum.varilx.de/forum/view/8-support/) überprüfen, um zu sehen, ob das Problem bereits beantwortet wurde, oder unsere Community um Hilfe bitten.\n" +
                        "· **Unterstützte Sprachen**\n" +
                        "\uD83C\uDDEC\uD83C\uDDE7 Englisch\n" +
                        "\uD83C\uDDE9\uD83C\uDDEA Deutsch")
                .setFooter("Varilx Support Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                .setImage(Main.getEmbedBild())
                .setColor(Color.GREEN)
                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png");
        event.getMessage().delete().queue();

        channel.sendMessageEmbeds(embedBuilder.build()).setActionRow(create, partner).queue();
    }
}

