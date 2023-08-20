package de.subscripted.working;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class EmbedBuilderButtons extends ListenerAdapter {
    public void onButtonInteraction(ButtonInteractionEvent event) {
        switch (event.getButton().getId()) {
            case "title":
                TextInput title = TextInput.create("title", "Embed Title", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Varilx Embed''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                Modal titlemodal = Modal.create("titlemodal", "Varilx Embedbuilder")
                        .addActionRow(title)
                        .build();

                event.replyModal(titlemodal).queue();
                break;

            case "color":
                TextInput Color = TextInput.create("color", "Embed Color", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Grün''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                Modal colormodal = Modal.create("colormodal", "Varilx Embedbuilder")
                        .addActionRow(Color)
                        .build();

                event.replyModal(colormodal).queue();
                break;

            case "dsc":
                TextInput dsc = TextInput.create("dsc", "Embed Description", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Hallo, wie gehts euch?''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                Modal dscmodal = Modal.create("dscmodal", "Varilx Embedbuilder")
                        .addActionRow(dsc)
                        .build();

                event.replyModal(dscmodal).queue();
                break;

            case "footer":
                TextInput footer = TextInput.create("footer", "Embed Footer", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Varilx Bot | Update 2032''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                Modal footermodal = Modal.create("footermodal", "Varilx Embedbuilder")
                        .addActionRow(footer)
                        .build();

                event.replyModal(footermodal).queue();
                break;

            case "author":
                TextInput Author = TextInput.create("athor", "Embed Author", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Subscripted''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                Modal authormodal = Modal.create("authormodal", "Varilx Embedbuilder")
                        .addActionRow(Author)
                        .build();

                event.replyModal(authormodal).queue();
                break;

            case "field":
                TextInput Field = TextInput.create("field", "Embed Field", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Varilx Update''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                TextInput Value = TextInput.create("value", "Embed Field-Value", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''Wir sind jetzt besser als davor!''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                Modal fieldmodal = Modal.create("fieldmodal", "Varilx Embedbuilder")
                        .addActionRow(Field, Value)
                        .build();

                event.replyModal(fieldmodal).queue();
                break;

            case "image":
                TextInput Image = TextInput.create("image", "Embed Image", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''https://deine-bild-url.png / .jpg usw''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                Modal imagemodal = Modal.create("imagemodal", "Varilx Embedbuilder")
                        .addActionRow(Image)
                        .build();

                event.replyModal(imagemodal).queue();
                break;

            case "thmb":
                TextInput thmb = TextInput.create("thmb", "Embed Thumbnail", TextInputStyle.PARAGRAPH).setPlaceholder("z.b. ''https://deine-bild-url.png / .jpg usw''")
                        .setMinLength(5)
                        .setMaxLength(999)
                        .setRequired(true)
                        .build();

                Modal thmbmodal = Modal.create("thmbmodal", "Varilx Embedbuilder")
                        .addActionRow(thmb)
                        .build();

                event.replyModal(thmbmodal).queue();
                break;
        }
    }
}
