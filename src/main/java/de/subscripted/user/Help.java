package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Help extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {


        TextChannel tc = event.getChannel().asTextChannel();

        if (!event.getName().equals("hilfe")) {
            return;
        }
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Hilfe")
                .setDescription("**Generelle Infos über Varilx**\n"
                        + "Wir sind ein Aktives und Freundliches Minecraft Server Netzwerk,\n"
                        + "was sich zum Großteil auf Survival Spezialisiert. \n"
                        + "Wir bieten eine umfangreiche Auswahl an coolen neuen Features, die dir bestimmt gefallen. \n"
                        + "Unteranderem bieten wir einen Spielsupport für Java sowohl als auch für Bedrock.\n"
                        + "\n"
                        + "IP,s: \n"
                        + "Bedrock: Varilx.de (Port: 19132)\n"
                        + "Java: Varilx.de\n"
                        + "\n"
                        + "**Wie bekomme ich Hilfe?**\n"
                        + "Du kannst in <#992011313813401640> User oder Teamler um Hilfe bitten ohne ein Ticket zu öffnen.\n"
                        + "Ein Ticket kannst du in <#915654430144221214> öffnen, und in <#970663709573783553> kannst du im Voice-Channel mit einem Teamler reden.\n"
                        + "\n"
                        + "Du kannst dir auch gerne dieses Video anschauen da wird dich nochmal einiges erklärt: https://www.youtube.com/watch?v=jhvC2JJCIeo \n"
                        + "\n"
                        + "Forum: https://varilx.de")
                .setFooter("Varilx Support Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                .setColor(Color.green);

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
