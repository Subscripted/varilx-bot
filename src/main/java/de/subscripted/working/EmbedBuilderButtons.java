package de.subscripted.working;

import de.subscripted.backend.ButtonInteraction;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmbedBuilderButtons extends ListenerAdapter {
    public void onButtonInteraction(ButtonInteractionEvent event){
        switch (event.getButton().getId()){
            case "":

                break;


        }
    }
}
