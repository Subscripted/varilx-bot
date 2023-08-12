package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends ListenerAdapter {

    private Map<String, Instant> userCooldowns = new HashMap<>();

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().equalsIgnoreCase("!feedback"))
            return;

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
            return;

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Vorschläge")
                .setColor(Color.GREEN)
                .setDescription("Du willst dein Feedback über den Server da lassen? \n" +
                        "Dann drück einfach auf den Button unter dieser Nachricht. \n" +
                        "Wir wollen nicht, das dieses Feature ausgenutzt wird, daher bitten wir dich ein ordentliches und konstruktives Feedback zu verfassen!")
                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                .setFooter("Varilx Feedback Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

        Button button = Button.secondary("feedback", "Feedback").withEmoji(Emoji.fromFormatted("<:varilxChatbox:1136013753301868555>"));

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(button).queue();
    }



    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!event.getButton().getId().equalsIgnoreCase("feedback"))
            return;

        String userId = event.getUser().getId();
        Instant lastFeedBackTime = userCooldowns.get(userId);

        if (lastFeedBackTime == null || lastFeedBackTime.isBefore(Instant.now().minusSeconds(1800))) {
            Role role = event.getGuild().getRoleById("1134159388190462042");
            if (event.getMember().getRoles().contains(role)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Varilx Support")
                        .setDescription("Du bist gemutet. Um dich entmuten zu lassen erstell ein neues Thema auf unserem Forum!")
                        .setFooter("Varilx Support Feature | Update 2023 ©", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            TextInput message = TextInput.create("feedback", "Dein Feedback", TextInputStyle.PARAGRAPH)
                    .setMinLength(5)
                    .setMaxLength(999)
                    .setRequired(true)
                    .build();

            Modal modall = Modal.create("vaxfeedback", "Varilx Feedback")
                    .addActionRow(message)
                    .build();


            event.replyModal(modall).queue();
            System.out.println("geht");
        } else {
            event.reply("Du kannst nur alle 30 Minuten einen Vorschlag machen.").setEphemeral(true).queue();
        }
    }


    public void onModalInteraction(ModalInteractionEvent event) {
        String feedback = event.getValue("feedback").getAsString();

        if (event.getModalId().equals("vaxfeedback")) {
            Member member = event.getMember();
            String dcusername = member.getAsMention();


            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Varilx Feedback")
                    .setDescription(member.getAsMention() + " hat uns Feedback gegeben: " + feedback)
                    .addField("AntiSpam!", "Geschrieben von: " + dcusername, false)
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                    .setFooter("Varilx Feedback Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

            EmbedBuilder DM = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Varilx Feedback")
                    .setDescription("Danke für dein Feedback, " + dcusername + ". Wir werden uns dein Feedback zu herzen nehmen.")
                    .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                    .setFooter("Varilx Feedback Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());


            MessageChannel channel = event.getGuild().getTextChannelById("1135973506773962882");

            channel.sendMessageEmbeds(embedBuilder.build()).queue();

            userCooldowns.put(event.getUser().getId(), Instant.now());

            event.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessageEmbeds(DM.build()).queue();
            });
        }
    }
}