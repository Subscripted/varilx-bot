package de.subscripted.serversafety;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Unban extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("unban")) {
            return;
        }


        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        String name = event.getOption("name").getAsString();

        for (Guild.Ban ban : event.getGuild().retrieveBanList().complete()) {
            User user = ban.getUser();

            if (user == null){
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Entbannung")
                    .setColor(Color.RED)
                    .setDescription(user + " ist nicht gebannt!")
                    .setFooter("Varilx Safety Feature | Update 2023 ©", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            }

            if (ban.getUser().getName().equalsIgnoreCase(name)) {
                event.getGuild().unban(user).queue();
                EmbedBuilder embedBuilder1 = new EmbedBuilder()
                        .setTitle("Varilx Entbannung")
                        .setColor(Color.green)
                        .setDescription(name + " wurde von " + event.getMember().getAsMention() + " auf Varilx.de Entbannt!")
                        .setFooter("Varilx Safety Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                TextChannel tc = event.getGuild().getTextChannelById("1134461790818947122");

                tc.sendMessageEmbeds(embedBuilder1.build()).queue();


                EmbedBuilder embedBuilder2 = new EmbedBuilder()
                        .setTitle("Varilx Unban")
                        .setColor(Color.GREEN)
                        .setDescription("Du hast erfolgreich " + name + " entbannt!")
                        .setFooter("Varilx Safety Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder2.build()).setEphemeral(true).queue();
            }
        }
    }
}
