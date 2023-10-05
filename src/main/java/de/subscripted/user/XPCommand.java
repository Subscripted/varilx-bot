package de.subscripted.user;

import de.subscripted.Main;
import de.subscripted.backend.XPSystem;
import de.subscripted.sql.XpSQLManager;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class XPCommand extends ListenerAdapter {
    private XpSQLManager xpSqlManager;

    public XPCommand() {
        xpSqlManager = Main.xpSqlManager;
    }

    @SneakyThrows
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("level"))
            return;

        Member user = event.getMember();
        Member target = event.getOption("nutzer").getAsMember();

        String userId = user.getId();
        String targetId = target.getId();

        int xp = xpSqlManager.getXP(targetId);
        int level = xpSqlManager.getLevel(targetId);
        int requiredXP = calculateRequiredXPForLevel(level + 1);
        int xpProgress = xp - calculateRequiredXPForLevel(level);

        System.out.println(xp + " / " + XPSystem.getRequiredXP(level));

        EmbedBuilder embedBuilder;
        if (user == target) {
            embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx XP")
                    .setColor(Color.GREEN)
                    .setDescription("<:0V:1122453179553026078><:1V:1122453182702952528><:2V:1122453184351318016><:3V:1122453186591068210><:4V:1122453187991969835>\n"
                            + "- Deine XP: " + xp + "\n"
                            + "- Dein Level: " + level + "\n"
                            + "- Dein Fortschritt: " + printUserXPState(xp, XPSystem.getRequiredXP(level + 1)) + " / " + Math.round(((double) xp / XPSystem.getRequiredXP(level + 1)) * 100) * 1 + "%")
                    .setFooter("Varilx XP Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setThumbnail(Main.getEmbedBild());
            event.replyEmbeds(embedBuilder.build()).queue();
        } else {
            embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx XP")
                    .setColor(Color.GREEN)
                    .setDescription("- XP von " + target.getAsMention() + ": " + xp + "\n"
                            + "- Level von " + target.getAsMention() + ": " + level + "\n"
                            + "- Vortschritt : " + printUserXPState(xp, XPSystem.getRequiredXP(level + 1)) + " / " + Math.round(((double) xp / XPSystem.getRequiredXP(level + 1)) * 100) * 1 + "%")
                    .setThumbnail(Main.getEmbedBild())
                    .setFooter("Varilx XP Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                    .setAuthor(target.getEffectiveName());
            event.replyEmbeds(embedBuilder.build()).queue();
        }
    }

    private int calculateRequiredXPForLevel(int level) {
        return 100 * level;
    }


    public static String printUserXPState(int exp, int expMax) {
        int maxLength = 10;

        int reachedXP = (int) Math.ceil(((double) exp / expMax) * maxLength);
        reachedXP = Math.min(reachedXP, maxLength);

        int stillRequiredXP = maxLength - reachedXP;

        StringBuilder stateBar = new StringBuilder();
        for (int i = 0; i < reachedXP; i++) {
            stateBar.append("🟩");
        }
        for (int i = 0; i < stillRequiredXP; i++) {
            stateBar.append(" ◾");
        }

        return stateBar.toString();
    }
}
