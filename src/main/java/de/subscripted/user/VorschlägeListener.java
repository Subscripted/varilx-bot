package de.subscripted.user;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VorschlägeListener extends ListenerAdapter {
    public void inButtonInteracti(ButtonInteractionEvent event) {
        if (event.getButton().getId().equalsIgnoreCase("accepted_vorschlag")) {
            event.deferEdit().queue();
            event.getHook().editOriginal(event.getMember().getAsMention() + " hat den Vorschlag angenommen!").queue();
        } else if (event.getButton().getId().equalsIgnoreCase("denied_vorschlag")) {
            Member applicant = event.getGuild().getMember(event.getUser());
            event.deferEdit().queue();
            event.getHook().editOriginal("**" + applicant.getAsMention() + "** hat den Vorschlag abgelehnt.").queue();
        } else if (event.getButton().getId().equalsIgnoreCase("delete_vorschlag")) {
            event.getHook().deleteOriginal().queue();
            event.deferEdit().queue();
        }
    }
}
