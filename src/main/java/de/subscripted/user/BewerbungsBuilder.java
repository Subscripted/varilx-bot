package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class BewerbungsBuilder extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event){
        Message message = event.getMessage();
        Member member = event.getMember();

        if (!message.getContentRaw().equals("!bewerben"))
            return;

        if (!member.hasPermission(Permission.ADMINISTRATOR))
            return;

        message.delete();


        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx.DE | Wir suchen dich!")
                .setColor(Color.GREEN)
                .setFooter("Varilx.DE | Bewerbungsphase 2023 ©", Main.getJda().getSelfUser().getAvatarUrl())
                .setDescription("**Unser Netzwerk Varilx.DE braucht Helfende Hände!**\n" +
                        "Hallo liebe Varilx Community, Wir von **Varilx.DE** sind derzeit auf der Suche \nnach engagierten Bewerbern für unser Server-Team.\n Falls du Interesse daran hast, uns bei\n **Support, Entwicklung, Content und Architektur** zu unterstützen, \ndann bist du bei uns an der richtigen Stelle.\n Aufgrund von fehlendem Support, unzureichendem Content\n und vielen anderen Aufgabengebiete haben wir diese Bewerbungsphase ins Leben gerufen. \nZögere nicht und **bewirb dich jetzt!**\n" +
                        "**Wo finde ich alle Informationen?**\n" +
                        "Lese dir bitte unseren ganzen Forum beitrag zum Bewerben durch, bevor du dich bewerben willst (Erster Button unter dieser Nachricht)!\n" +
                        "Wo kann ich mich Bewerben?\n" +
                        "Unter https://forum.varilx.de/bewerben/")
                .setThumbnail(Main.getEmbedBild())
                .setImage("https://cdn.discordapp.com/attachments/1104111151937245284/1139017969444339722/ValuniaNET-Thumbddnail-Wiederhergestellt.png");

        Button button = Button.link("https://forum.varilx.de/bewerben/", "Bewerben").withEmoji(Emoji.fromFormatted("<:Varilx:1128364477335748698>"));
        Button button1 = Button.link("https://forum.varilx.de/forum/topic/197-wir-suchen-dich%21-%7C-varilxde-bewerbungsphase/", "Formusbeitrag").withEmoji(Emoji.fromFormatted("<:Link:1114557715218436207>"));

        event.getChannel().asTextChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(button1, button).queue();
        event.getMessage().delete().queue();
    }
}
