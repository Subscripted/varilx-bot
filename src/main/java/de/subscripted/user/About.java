package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class About extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("about"))
            return;

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx.de Bot About")
                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                .setDescription("Dies ist der offizielle Bot von Varilx.de\n"
                        + "\n"
                        + "Developed by Lorenz (Subscripted)\n"
                        + "\n"
                        + "**Systeme:** \n"
                        + "- Giveaway\n"
                        + "- Ticket System\n"
                        + "- Level System\n"
                        + "- Join Message\n"
                        + "- Support Join\n"
                        + "- Ban\n"
                        + "- Unban\n"
                        + "- Mute\n"
                        + "- Unmute\n"
                        + "- Changing Activities\n"
                        + "- Help\n"
                        + "- Teamliste\n"
                        + "- Vorschläge\n"
                        + "- Geld System\n"
                        + "- Feedback\n"
                        + "- Send command\n"
                        + "- EmbedBuilder command Beta\n"
                        + "- Team add Command\n"
                        + "- Demote und Promote command\n"
                        + "- About\n").setColor(Color.GREEN)
                .setFooter("Varilx Bot About | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();


    }
}
