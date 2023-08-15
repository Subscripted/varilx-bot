package de.subscripted.admin;

import de.subscripted.admin.StaffList;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class OnLoad extends ListenerAdapter {
    public void onGuildReady(GuildReadyEvent event) {

        /*Guild guild = event.getGuild();
        guild.getTextChannelById("1135323930811432990").getHistory().retrievePast(1).queue(messages -> {
            if (messages.size() == 0) {
                guild.getTextChannelById("1135323930811432990").sendMessageEmbeds(StaffList.sendStaffEmbed(guild)).queue();
*/
        TextChannel staffListChannel = event.getGuild().getTextChannelById("1135323930811432990");
        if (staffListChannel == null)
            return;
        staffListChannel.getHistory().retrievePast(1).queue(messages2 -> {
            messages2.get(0).editMessageEmbeds(StaffList.sendStaffEmbed(event.getGuild())).queue();
        });
    }
}




