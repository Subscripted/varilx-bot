package de.subscripted.working;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class OwnChannelButtonBuilder extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event){
        if (!event.getMessage().equals("!wonchannel"))
            return;

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
            return;

            Button changename = Button.secondary("changename", " ").withEmoji(Emoji.fromFormatted("<:vax_pen:1140659710836621342>"));

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Privat-Channel")
                .setColor(Color.green)
                .setDescription("Dieses Interface kann verwendet werden, um deinen temporären Kanal zu bearbeiten.\n\n" +
                        "<:vax_pen:1140659710836621342> ``Umbenennen`` ``Test``  ")
                .setFooter("Varilx Voice Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).addActionRow(changename).queue();
        }
    }

