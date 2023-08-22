package de.subscripted.admin;


import de.subscripted.Main;
import de.subscripted.backend.TimeStampMaker;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.sql.Time;
import java.time.Clock;
import java.time.OffsetDateTime;

public class GiveawayCommand extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("giveaway"))
            return;

        Member member = event.getMember();
        assert member != null;

        Guild guild = event.getGuild();
        assert guild != null;

        OptionMapping optionMappingPrize = event.getOption("preis");
        OptionMapping optionMappingWinners = event.getOption("gewinner");
        OptionMapping optionMappingDuration = event.getOption("dauer");
        assert optionMappingPrize != null;
        assert optionMappingWinners != null;
        assert optionMappingDuration != null;

        String prize = optionMappingPrize.getAsString();
        int winners = optionMappingWinners.getAsInt();
        int duration = (int) parseDuration(optionMappingDuration.getAsString());

        event.reply(guild.getPublicRole().getAsMention()).addEmbeds(
                        new EmbedBuilder()
                                .setTitle("<a:Vaxparty:1137213809128382474> Giveaway <a:Vaxparty:1137213809128382474>")
                                .setFooter("Varilx Giveway | Updated 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                                .setColor(Color.GREEN)
                                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                                .setDescription("- Gestartet von: " + member.getAsMention() + "\n" + "- Was wurde verlost: **" + prize + "** \n"  + "- Restzeit: <t:" + TimeStampMaker.getTime(duration) + ":R>")

                                .setTimestamp(OffsetDateTime.now(Clock.systemUTC()))
                                .build())
                .addActionRow(Button.primary("giveaway_join", Emoji.fromUnicode("U+1F389")))
                .complete().retrieveOriginal().queue(message -> {
                    GiveawayManager.create(member, prize, winners, TimeStampMaker.getTime(duration), message.getChannel().getId(), message.getId());
                    GiveawayRunnable.updateRunnable();
                });
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
                case 's':
                    durationInSeconds += value;
                    break;
                default:
                    throw new IllegalArgumentException("Ungültige Einheit: " + unit);
            }
        }
        return durationInSeconds;
    }
}
