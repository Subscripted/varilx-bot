
package de.subscripted.admin;

import de.subscripted.Main;
import de.subscripted.sql.MoneySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TopMoneyList {
    public static MessageEmbed sendTopMoneyList(Guild guild) {
        MoneySQLManager moneySQLManager = new MoneySQLManager();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String last_update = simpleDateFormat.format(new Date());

        LocalTime currentTime = LocalTime.now(ZoneId.systemDefault());

        int hoursToAdd = 3;
        ZonedDateTime zonedDateTime = currentTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).plusHours(2);
        LocalTime newTime = zonedDateTime.toLocalTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = formatter.format(newTime);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        builder.setTitle("Varilx Top 10 Varonx ");
        builder.setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png");

        List<Member> members = guild.getMembers();
        List<MemberCoins> memberCoinsList = new ArrayList<>();

        for (Member member : members) {
            try {
                int coins = moneySQLManager.getCoins(member.getId());
                memberCoinsList.add(new MemberCoins(member, coins));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        memberCoinsList.sort(Comparator.reverseOrder());

        StringBuilder descriptionBuilder = new StringBuilder();

        for (int rank = 0; rank < Math.min(10, memberCoinsList.size()); rank++) {
            MemberCoins memberCoins = memberCoinsList.get(rank);
            String emoji = getRankEmoji(rank);
            String formattedCoins = formatCoins(memberCoins.getCoins());
            descriptionBuilder.append(emoji).append(" »  ").append(memberCoins.getMember().getAsMention()).append(" - **").append(formattedCoins).append("** Varonx\n~~---»-----------------------------------------«---~~\n");
        }

        builder.setDescription(descriptionBuilder.toString());
        builder.setFooter("Last updated " + last_update + " " + formattedTime + " Uhr", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        return builder.build();
    }

    private static String getRankEmoji(int rank) {
        if (rank == 0) {
            return "🥇";
        } else if (rank == 1) {
            return "🥈";
        } else if (rank == 2) {
            return "🥉";
        } else {
            return "#" + (rank + 1);
        }
    }

    private static String formatCoins(int coins) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(coins);
    }

    private static class MemberCoins implements Comparable<MemberCoins> {
        private final Member member;
        private final int coins;

        public MemberCoins(Member member, int coins) {
            this.member = member;
            this.coins = coins;
        }

        public Member getMember() {
            return member;
        }

        public int getCoins() {
            return coins;
        }

        @Override
        public int compareTo(MemberCoins other) {
            return Integer.compare(this.coins, other.coins);
        }
    }
}
