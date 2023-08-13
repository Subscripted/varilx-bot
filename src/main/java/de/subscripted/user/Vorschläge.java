package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.w3c.dom.Text;

import java.awt.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Vorschläge extends ListenerAdapter {

    private Map<String, Instant> userCooldowns = new HashMap<>();

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().equalsIgnoreCase("!umfrage"))
            return;

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
            return;

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Vorschläge")
                .setColor(Color.GREEN)
                .setDescription("Du hast eine Idee oder ein Verbesserungsvorschlag für den Server? \n" +
                        "Dann drück einfach auf den Button unter dieser Nachricht. \n" +
                        "bitte beachte dabei, dass dein Vorschlag mit sofortiger Wirkung in eine Umfrage umgewandelt wird, und jeder sie lesen kann!")
                .setImage("https://cdn-longterm.mee6.xyz/plugins/welcome/images/886262410489520168/616250b3031b7010a2815e64e86581a02d011f396d5ab951717109abd2002829.png")
                .setFooter("Varilx Umfrage Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

        Button button = Button.success("vorschlag", "Vorschlag -> ⭐");

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(button).queue();
    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!event.getButton().getId().equalsIgnoreCase("vorschlag"))
            return;

        Role role = event.getGuild().getRoleById("1134159388190462042");
        if (event.getMember().getRoles().contains(role)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle("Varilx Support")
                    .setDescription("Du bist gemutet. Um dich entmuten zu lassen, erstelle ein neues Thema auf unserem Forum!")
                    .setFooter("Varilx Support Feature | Update 2023 ©", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }
        String userId = event.getUser().getId();
        Instant lastVorschlagTime = userCooldowns.get(userId);

        if (lastVorschlagTime == null || lastVorschlagTime.isBefore(Instant.now().minusSeconds(1800))) {
            TextInput message = TextInput.create("vorschlag", "Was ist dein Vorschlag / deine Idee?", TextInputStyle.PARAGRAPH)
                    .setMinLength(5)
                    .setRequired(true)
                    .build();

            TextInput url = TextInput.create("url", "Bild URL (Optional)", TextInputStyle.SHORT)
                    .setMinLength(5)
                    .setRequired(false)  // Set to optional
                    .build();

            Modal modal = Modal.create("vorschläge", "Vorschläge / Ideen")
                    .addActionRows(ActionRow.of(message), ActionRow.of(url))
                    .build();

            event.replyModal(modal).queue();
        } else {
            event.reply("Du kannst nur alle 30 Minuten einen Vorschlag machen.").setEphemeral(true).queue();
        }
    }


        public void onModalInteraction(ModalInteractionEvent event) {
            String vorschlag = event.getValue("vorschlag").getAsString();
            String image = event.getValue("url").getAsString();

            if (event.getModalId().equals("vorschläge")) {
            Member member = event.getMember();
            String dcusername = member.getAsMention();

            Button button = Button.success("accepted_vorschlag", "Angenommen");
            Button button2 = Button.danger("denied_vorschlag", "Abgelehnt");
            Button button3 = Button.secondary("delete_vorschlag", "Löschen");

            ActionRow actionRow = ActionRow.of(button, button2, button3);


            if (image == null)
                return;

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Varilx Vorschläge")
                    .addField("```Vorschlag / Idee: ```", " " + vorschlag, false)
                    .addField("AntiSpam!", "Geschrieben von: " + dcusername, false)
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                    .setFooter("Varilx Vorschlag Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());


            EmbedBuilder DM = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Varilx Vorschläge")
                    .addField("```Dein Vorschlag / Idee: ```", " " + vorschlag, false)
                    .setDescription("Danke für deinen Vorschlag / deine Idee, " + dcusername)
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                    .setFooter("Varilx Vorschlag Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                EmbedBuilder umfragemessage = new EmbedBuilder();
                umfragemessage.setColor(Color.GREEN);
                umfragemessage.setTitle("Varilx Vorschläge");
                umfragemessage.addField("```Vorschlag / Idee: ```", " " + vorschlag, false);
                umfragemessage.addField("Idee kommt von:", " " + dcusername, false);
                umfragemessage.setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.pngg");

                if (image != null && !image.isEmpty()) {
                    umfragemessage.setImage(image);
                }

                umfragemessage.setFooter("Varilx Vorschlag Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

            MessageChannel channel = event.getGuild().getTextChannelById("1133344570135019570");
            TextChannel umfrage = event.getGuild().getTextChannelById("1062410932267003954");

            event.deferEdit().queue();
            channel.sendMessageEmbeds(embedBuilder.build()).setActionRow(button, button2, button3).queue();

            userCooldowns.put(event.getUser().getId(), Instant.now());

            event.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessageEmbeds(DM.build()).queue();
                umfrage.sendMessageEmbeds(umfragemessage.build()).queue(message -> {
                    message.addReaction(Emoji.fromUnicode("\uD83D\uDC4D\uD83C\uDFFD")).queue();
                    message.addReaction(Emoji.fromUnicode("\uD83D\uDC4E\uD83C\uDFFD")).queue();
                });
            });
        }
    }
}
