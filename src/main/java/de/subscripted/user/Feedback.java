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
                .setTitle("Varilx Feedback")
                .setColor(Color.GREEN)
                .setDescription("Du willst dein Feedback über den Server da lassen? \n" +
                        "Dann drück einfach auf den Button unter dieser Nachricht. \n" +
                        "Wir wollen nicht, das dieses Feature ausgenutzt wird, daher bitten wir dich ein ordentliches und konstruktives Feedback zu verfassen!")
                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                .setFooter("Varilx Feedback Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

        Button button = Button.secondary("feedbackbutton", "Feedback").withEmoji(Emoji.fromFormatted("<:varilxChatbox:1136013753301868555>"));

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(button).queue();

        event.getMessage().delete().queue();
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


            event.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessageEmbeds(DM.build()).queue();
            });
        }
    }
}