package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Bugreport extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("bugreport")) {
            TextInput nameig = TextInput.create("name/ign", "Dein Discord/Ingame Name (nicht Pflicht)!", TextInputStyle.SHORT)
                    .setMinLength(1)
                    .setRequired(false)
                    .build();

            TextInput bug = TextInput.create("bug", "Beschreibung des Bugs / Fehler!", TextInputStyle.PARAGRAPH)
                    .setMinLength(5)
                    .setMaxLength(999)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("bugreport", "Bugreport")
                    .addActionRows(ActionRow.of(nameig), ActionRow.of(bug))
                    .build();

            event.replyModal(modal).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String nameig = event.getValue("name/ign").getAsString();
        String bug = event.getValue("bug").getAsString();

        if (event.getModalId().equals("bugreport")) {

            Member member = event.getMember();
            String username = member.getAsMention();

            if (nameig.isEmpty()) {
                nameig = "||/////////////////////////||";
            }

            Button button = Button.secondary("report_closed", "Report Bearbeitet").withEmoji(Emoji.fromFormatted("<:zcorrect:1079435799289942106>"));

            ActionRow actionRow = ActionRow.of(button);
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.CYAN)
                    .setTitle("Bugreport")
                    .setDescription("- <:varilx_discord:1139957449516924959> Discord / IGN Name : **" + nameig + "**\n" +
                            "- <:varilx_forum:1139957497336172584> Beschreibung des Bugs : **" + bug + "**\n\n" +
                            "- <:varilx_id:1139957240963539054> AntiSpam : **" + username + "**")
                    .setFooter("Varilx Support Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setThumbnail(Main.getEmbedBild());

            EmbedBuilder embedBuilderDM = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Bugreport")
                    .setDescription("- <:varilx_discord:1139957449516924959> Discord / IGN Name : **" + nameig + "**\n" +
                            "- <:varilx_forum:1139957497336172584> Beschreibung des Bugs : **" + bug + "**\n\n" +
                            "- <:varilx_star:1139957135707484290> Danke für die Meldung **" + username + "**")
                    .setFooter("Varilx Support Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setThumbnail(Main.getEmbedBild());


            MessageChannel channel = event.getGuild().getTextChannelById("1140271093979037697");
            event.deferEdit().queue();
            channel.sendMessageEmbeds(embedBuilder.build()).setActionRow(button).queue();
            event.getUser().openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessageEmbeds(embedBuilderDM.build()).queue();
                    }
            );
        }
    }

}