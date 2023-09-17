package de.subscripted.serversafety;

import de.subscripted.Main;
import de.subscripted.sql.WarnSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;

public class GetWarns extends ListenerAdapter {

    WarnSQLManager sqlManager;

    public GetWarns(WarnSQLManager sqlManager){
        this.sqlManager = sqlManager;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if (!event.getName().equals("getwarn"))
            return;
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }
        Member target = event.getOption("nutzer").getAsMember();
        try {
           String reason = sqlManager.getReasons(target.getId()).toString();
            event.reply( target.getAsMention() + reason).setEphemeral(true).queue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
