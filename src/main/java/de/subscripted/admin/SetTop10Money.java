package de.subscripted.admin;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SetTop10Money extends ListenerAdapter {
    public void onGuildReady(GuildReadyEvent event) {
        TextChannel moneyTopChannel = event.getGuild().getTextChannelById("1139090218385944636");
        moneyTopChannel.getHistory().retrievePast(1).queue(messages -> {
            messages.get(0).editMessageEmbeds(TopMoneyList.sendTopMoneyList(event.getGuild())).queue();
        });
    }
}


