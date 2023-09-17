package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;

public class Send extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){

        if (!event.getName().equals("send"))
            return;
        Member member = event.getMember();

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }
        String message = event.getOption("nachricht").getAsString();
        event.getChannel().sendMessage(message).queue();
        event.reply("Erfolgreich gesendet!").setEphemeral(true).queue();
    }
}
