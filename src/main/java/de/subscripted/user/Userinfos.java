package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.List;

public class Userinfos extends ListenerAdapter {

    private static final Map<User.UserFlag, String> BADGE_EMOJIS = new HashMap<>();

    static {
        BADGE_EMOJIS.put(User.UserFlag.HYPESQUAD_BALANCE, "<:hype1:1140656206747287603>");
        BADGE_EMOJIS.put(User.UserFlag.HYPESQUAD_BRILLIANCE, "<:hype2:1140656191886872646>");
        BADGE_EMOJIS.put(User.UserFlag.HYPESQUAD_BRAVERY, "<:hype3:1140656174140756049>");
        BADGE_EMOJIS.put(User.UserFlag.ACTIVE_DEVELOPER, "<:active_dev:1140656159662030888>");
        BADGE_EMOJIS.put(User.UserFlag.VERIFIED_DEVELOPER, "<:dev:1140656146584178770>");
        BADGE_EMOJIS.put(User.UserFlag.PARTNER, "<:partner:1140656134504599552>");

    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("userinfo"))
            return;

        Member user = event.getOption("nutzer").getAsMember();

        String name = user.getUser().getName();
        String anzeigename = user.getNickname();

        OffsetDateTime creationDate = user.getTimeCreated();
        String creationTimestamp = "<t:" + creationDate.toEpochSecond() + ":D>";
        String timeAgoTimestamp = "<t:" + creationDate.toEpochSecond() + ":R>";

        User discordUser = user.getUser();
        EnumSet<User.UserFlag> userFlags = discordUser.getFlags();
        List<String> badges = getFormattedBadges(userFlags);

        boolean userIsBot = discordUser.isBot();

        OffsetDateTime joinDate = user.getTimeJoined();
        String joinedTimestamp = "<t:" + joinDate.toEpochSecond() + ":D>";
        String joinedTimeAgo = "<t:" + joinDate.toEpochSecond() + ":R>";

        String botStatusText = userIsBot ? "Ja" : "Nein";

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setAuthor("Varilx.de | Bot");
        embedBuilder.setTitle("<:vax_info:1140659735150997624> Informationen zu " + name);
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription("<:varilx_user:1139957321196376107> Anzeigename: **" + anzeigename + "**\n" +
                "<:varilx_user:1139957321196376107> Name: **" + name + "**\n\n" +
                "<:varilx_clendar:1139956980576960653> Account erstellt am: **" + creationTimestamp + "**\n" +
                "<:varilx_clock:1139957097522528257> Account erstellt vor: **" + timeAgoTimestamp + "**\n\n" +
                "<:varilx_clendar:1139956980576960653> Server betreten am: **" + joinedTimestamp + "**\n" +
                "<:varilx_clock:1139957097522528257> Server betreten vor: **" + joinedTimeAgo + "**\n\n" +
                "<:vax_pen:1140659710836621342>  Nickname: **" + anzeigename + "**\n" +
                "<:vax_bot:1140659750724435979> Bot: **" + botStatusText + "**\n\n" +
                "<:varilx_star:1139957135707484290> Badges:\n" + String.join("\n", badges))
                .setFooter("Varilx.DE | Userinfos 2023 ©", Main.getJda().getSelfUser().getAvatarUrl())
                .setThumbnail(Main.getThumbnail());

        event.replyEmbeds(embedBuilder.build()).queue();
    }

    private List<String> getFormattedBadges(EnumSet<User.UserFlag> userFlags) {
        List<String> badges = new ArrayList<>();

        for (User.UserFlag flag : userFlags) {
            if (BADGE_EMOJIS.containsKey(flag)) {
                badges.add(BADGE_EMOJIS.get(flag) + " " + getBadgeName(flag));
            }
        }

        if (badges.isEmpty()) {
            badges.add("Keine Badges");
        }

        return badges;
    }

    private String getBadgeName(User.UserFlag flag) {
        switch (flag) {
            case HYPESQUAD_BALANCE:
                return "**Balance**";
            case HYPESQUAD_BRILLIANCE:
                return "**Brilliance**";
            case HYPESQUAD_BRAVERY:
                return "**Bravery**";
            case ACTIVE_DEVELOPER:
                return "**Aktive Entwickler**";
            case VERIFIED_DEVELOPER:
                return "**Verifizierter Developer**";
            case PARTNER:
                return "**Besitzer eines Partnerservers**";
            default:
                return "**Unbekannte Badge**";
        }
    }
}
