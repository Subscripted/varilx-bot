package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class RulesBuilder extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {

        Message message = event.getMessage();

        if (!message.getContentRaw().equals("!rules"))
            return;
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
            return;

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx.DE | Allgemeines Regelwerk")
                .setAuthor("Varilx | Regelwerk")
                .setDescription("**Discord | Richtlinen/Regelwerk**\n" +
                        "Da wir aber Natürlich ein gemütliches Miteinander haben" +
                        "bitten wir euch das Regelwerk durchzulesen damit es zu" +
                        " keinen Konflikten kommt ▸\n\n\n" +
                        "> - §1. Nicknames dürfen keine beleidigenden oder anderen verbotenen oder geschützen Namen oder Namensteile enthalten.\n\n" +
                        "> - §2. Das Konsumieren sowie das Verherrlichen von illegalen Substanzen / Drogen, darunter fallen ebenso: Alkohol; Zigaretten und Cannabis. Die Thematik sollte vermieden werden.\n\n" +
                        "> - §3. Jegliche Art von Werbung ist auf diesem Server untersagt. Ggf. kann sich an einen zuständigen Admin gewandt werden, um über eine Möglichkeit zur Werbung zu verhandeln.\n\n" +
                        "> - §4. Private Daten wie Telefonnummern, Adressen, Passwörter und ähnlichem dürfen nicht öffentlich ausgetauscht werden.\n\n" +
                        "> - §5. Alle Regeln sind einzuhalten ansonsten wird es mit Bannen, einem Kick oder anderweitig Bestraft!\n\n" +
                        "> - §6. Die Regeln dienen zur Verhaltensregeln und können jenach Situation vom den Teammitgliedern geändert werden.\n\n" +
                        "> - §7. Das Mitschneiden von Gesprächen ist auf dem gesamten Server nur nach Absprache mit den anwesenden Benutzern des entsprechenden Channels erlaubt. Willigt ein User nicht der Aufnahme ein, ist die Aufnahme des Gesprächs verboten.\n\n" +
                        "> - §8. Behandle alle mit Respekt. Belästigung, Hexenjagd, Sexismus, Rassismus oder Volksverhetzung werden absolut nicht toleriert.\n\n" +
                        "> - §9. Keine NSFW- oder obszönen Inhalte. Dazu zählen Texte, Bilder oder Links mit Nacktheit, Sex, schwerer Gewalt oder anderen grafisch verstörenden Inhalten.\n\n" +
                        "> - §10. Es dürfen keine Bots mit dem Discord Server verbunden werden. Bots dürfen nur in ausgewiesenen Channels verbunden werden und auch nur dann, wenn kein weiterer Bot in dem Channel aktiv ist.\n\n" +
                        "> - §11. Server Admins, Moderatoren oder anderweitig befugte Admins haben volles Weisungsrecht. Das Verweigern einer bestimmten Anweisung kann zu einem Kick oder Bann führen.\n\n" +
                        "> - §12. Avatare dürfen keine pornographischen, rassistischen oder beleidigenden Inhalte beinhalten.\n\n" +
                        "> - §13. Der Umgang mit anderen Discord Benutzern sollte stets freundlich sein. Verbale Angriffe gegen andere User sind strengstens untersagt.\n\n" +
                        "> - §14. Das Einspielen von eigener Musik, oder das Übertragen von anderen nicht erwünschten Tönen ist untersagt.\n\n" +
                        "Wir bitten auch die Discord-Terms/Guidelines Durchzulesen!\n> **https://discord.com/terms**\n" +
                        "> **https://discord.com/guidelines**\n\n" +
                        "**Discord | Minecraft Regelwerk**\n" +
                        "> - Wir besitzen natürlich auch für unseren Server ein Regelwerk, und bitten dieses auch einmal durchzulesen!\n"
                        + "> **https://regelwerk.varilx.de/** \n\n" +
                        "Ich hoffe du hast das **Discord Regelwerk/Richtlinien** gründlich durchgelesen, wenn du das hier liest klicke bitte auf den check Reaktions Button um die Regeln zu **Akzeptieren!**")
                .setFooter("Varilx Regelwerk | Update 2023 ©", Main.getJda().getSelfUser().getAvatarUrl())
                .setImage("https://cdn.discordapp.com/attachments/1055223755909111808/1139190110282334248/ValuniaNET-ddddThumbnail-Wiederhergestellt.png")
                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                .setColor(Color.GREEN);



        Button button = Button.secondary("rules", "Akzeptieren").withEmoji(Emoji.fromFormatted("<:ys:1139209857438863471>"));
        Button button1 = Button.link("https://tube-hosting.com/home", "Partner").withEmoji(Emoji.fromFormatted("<:TubehostingVarilx:1101657813794693120>"));
        Button button2 = Button.link("https://discord.com/terms", "TOS").withEmoji(Emoji.fromFormatted("<:discord:1048627445735096320>"));
        Button button3 = Button.link("https://discord.com/guidelines", "Guidelines").withEmoji(Emoji.fromFormatted("<:discordlogo11:1094722866903261205>"));
        Button button4 = Button.link("https://regelwerk.varilx.de/", "Forum").withEmoji(Emoji.fromFormatted("<:Share:1114840763608604802>"));


                message.delete().queue();
        event.getChannel().asTextChannel().sendMessageEmbeds(embedBuilder.build()).addActionRow(button2, button3, button4).addActionRow(button, button1).queue();


    }
}
