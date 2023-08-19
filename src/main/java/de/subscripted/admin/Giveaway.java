package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

public class Giveaway extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("giveaway"))
            return;

        Member m = event.getMember();
        if (!m.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }
        TextChannel channel = event.getChannel().asTextChannel();

        TextInput giveawayItemInput = TextInput.create("wasverlost", "Was wird verlost?", TextInputStyle.PARAGRAPH)
                .setMinLength(5)
                .setRequired(true)
                .build();

        TextInput giveawayDurationInput = TextInput.create("zahl", "Dauer des Giveaways.", TextInputStyle.SHORT)
                .setRequired(true)
                .build();

        ActionRow actionRow1 = ActionRow.of(giveawayItemInput);
        ActionRow actionRow2 = ActionRow.of(giveawayDurationInput);

        Modal modal = Modal.create("giveaway", "Giveaway")
                .addActionRows(actionRow1, actionRow2)
                .build();

        event.replyModal(modal).queue();

    }


    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String giveawayItemInput = event.getValue("wasverlost").getAsString();
        String giveawayDurationInput = event.getValue("zahl").getAsString();

        if (event.getModalId().equals("giveaway")) {
            Member member = event.getMember();
            String dcusername = member.getAsMention();
            giveawayItemInput = event.getValue("wasverlost").getAsString();
            giveawayDurationInput = event.getValue("zahl").getAsString();
            long durationInSeconds;
            try {
                durationInSeconds = parseDuration(giveawayDurationInput);
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Giveaway");
                TextChannel channel = event.getGuild().getTextChannelById("970658541478227989");
                channel.sendMessage("<@&1098654469710954728>").queue();
                event.deferEdit().queue();
                String finalGiveawayItemInput = giveawayItemInput;
                channel.sendMessageEmbeds(embedBuilder.build()).queue(message -> {
                    message.addReaction(Emoji.fromUnicode("\uD83C\uDF81")).queue();
                    new GiveawayThread(channel, message.getIdLong(), durationInSeconds, embedBuilder, dcusername, finalGiveawayItemInput).start();

                });
            } catch (NumberFormatException e) {
                event.reply("Ungültige Zahl angegeben.").setEphemeral(true).queue();
            }

        }
    }

    private long parseDuration(String durationInput) {
        long durationInSeconds = 0;
        String[] parts = durationInput.split("\\s+");
        for (String part : parts) {
            part = part.trim();
            char unit = part.charAt(part.length() - 1);
            int value = Integer.parseInt(part.substring(0, part.length() - 1));
            switch (unit) {
                case 'd':
                    durationInSeconds += value * 24 * 60 * 60;
                    break;
                case 'h':
                    durationInSeconds += value * 60 * 60;
                    break;
                case 'm':
                    durationInSeconds += value * 60;
                    break;
                default:
                    throw new IllegalArgumentException("Ungültige Einheit: " + unit);
            }
        }
        return durationInSeconds;
    }
}