package de.subscripted.admin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Clear extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
        return;
        Member member = event.getMember();

        if (!member.hasPermission(Permission.ADMINISTRATOR))
            return;

        String[] command = event.getMessage().getContentRaw().split("\\s+");

        if (command[0].equalsIgnoreCase("!clear") && command.length == 2) {
            try {
                int numToDelete = Integer.parseInt(command[1]);

                if (numToDelete < 1 || numToDelete > 100) {
                    event.getChannel().sendMessage("Bitte gib eine Zahl zwischen 1 und 100 ein.").queue();
                    return;
                }

                TextChannel channel = event.getChannel().asTextChannel();
                List<Message> sortedMessages = new ArrayList<>();
                for (Message message : channel.getHistory().retrievePast(numToDelete + 2 - 1 ).complete()) {

                    if(message.getId() != null){
                        sortedMessages.add(message);
                    }
                }

                channel.deleteMessages(sortedMessages).queue();

                event.getMessage().delete().queueAfter(1, TimeUnit.SECONDS);



            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("Bitte gib eine Zahl zwischen 1 und 100 ein.").queue();
            }
        }
    }
}