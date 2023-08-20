package de.subscripted.working;

import de.subscripted.sql.EmbedSQLManager;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class EmbedBuilderBetaModalInteractions extends ListenerAdapter {

    EmbedSQLManager sqlManager;

    public EmbedBuilderBetaModalInteractions() {
        sqlManager = new EmbedSQLManager();
    }

    public void onModalInteraction(ModalInteractionEvent event) {
        try {
            String code = sqlManager.getEmbedCode();

            switch (event.getModalId()) {
                case "titlemodal":
                    String title = event.getValue("title").getAsString();
                    sqlManager.saveEmbedData(code, "title", title);
                    break;
                case "colormodal":
                    String color = event.getValue("color").getAsString();
                    sqlManager.saveEmbedData(code, "color", color);
                    break;
                case "dscmodal":
                    String dsc = event.getValue("dsc").getAsString();
                    sqlManager.saveEmbedData(code, "dsc", dsc);
                    break;
                case "footermodal":
                    String footer = event.getValue("footer").getAsString();
                    sqlManager.saveEmbedData(code, "footer", footer);
                    break;
                case "authormodal":
                    String author = event.getValue("author").getAsString();
                    sqlManager.saveEmbedData(code, "author", author);
                    break;
                case "fieldmodal":
                    String field = event.getValue("field").getAsString();
                    sqlManager.saveEmbedData(code, "field", field);
                    String value = event.getValue("value").getAsString();
                    if (value == null)
                        return;
                    sqlManager.saveEmbedData(code, "value", value);
                    break;
                case "imagemodal":
                    String image = event.getValue("image").getAsString();
                    sqlManager.saveEmbedData(code, "image", image);
                    break;
                case "thmbmodal":
                    String thmb = event.getValue("thmb").getAsString();
                    sqlManager.saveEmbedData(code, "thmb", thmb);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        event.deferEdit().queue();
    }
}
