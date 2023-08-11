package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class NudeCMD extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if (!event.getName().equals("corn"))
            return;


        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Sussybussy")
                .setDescription("Corn Bild:")
                .setImage("https://cdn.discordapp.com/attachments/811253351798145044/1139177998793322558/ayontUWU-min.png")
                .setColor(Color.PINK)
                .setFooter("Varilx Corn Feature | Update 2023 ©", Main.getJda().getSelfUser().getAvatarUrl());

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();

    }
}
