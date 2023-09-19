package de.subscripted.backend;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ModalInteractionsHandler extends ListenerAdapter {

    public void onModalInteraction(ModalInteractionEvent event){
        assert event.getModalId() != null;
        switch (event.getModalId()){
            case "":

                break;
        }
    }
}
