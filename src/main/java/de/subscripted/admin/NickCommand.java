package de.subscripted.admin;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NickCommand extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if (!event.getName().equals("nick"))
            return;
        Member user = event.getOption("nutzer").getAsMember();
        String newname = event.getOption("newname").getAsString();
        user.modifyNickname(newname).queue();

        event.reply(newname).setEphemeral(true).queue();
    }
}
