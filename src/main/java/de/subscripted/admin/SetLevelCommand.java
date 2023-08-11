package de.subscripted.admin;

import de.subscripted.Main;
import de.subscripted.sql.XpSQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;

public class SetLevelCommand extends ListenerAdapter {

    private XpSQLManager xpSqlManager;

    public SetLevelCommand() {
        xpSqlManager = Main.xpSqlManager;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("setlevel"))
            return;

        Member member = event.getMember();

        Member targetMember = event.getOption("nutzer").getAsMember();

        String targetUserId = targetMember.getId();

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx XP Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        int level = (int) event.getOption("level").getAsLong();

        try {
            xpSqlManager.setLevel(targetUserId, level);
            event.reply("Das Level für " + targetMember.getAsMention() + " wurde auf " + level + " gesetzt.").setEphemeral(true).queue();
            xpSqlManager.setXP(targetUserId, 0);
        } catch (SQLException e) {
            event.reply("Fehler beim Setzen des Levels.").setEphemeral(true).queue();
            e.printStackTrace();
        }
    }
}
