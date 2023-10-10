package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class StaffList {
    public static MessageEmbed sendStaffEmbed(Guild guild) {

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
        builder.setTitle("Varilx Team Liste");
        builder.setThumbnail(Main.getThumbnail());
        String[][] roles = {
                {"888391247365046272", "<@&888391247365046272>"},
                {"888504288198918185", "<@&888504288198918185>"},
                {"1085908394092793946", "<@&1085908394092793946>"},
                {"1074767531509809202", "<@&1074767531509809202>"},
                {"888509168330358805", "<@&888509168330358805>"},
                {"1076856927088300093", "<@&1076856927088300093>"},
                {"1064863997062561803", "<@&1064863997062561803>"},
                {"1157964477858320545", "<@&1157964477858320545>"},
                {"1082387802215424130", "<@&1082387802215424130>"},
                {"1131233998379700274", "<@&1131233998379700274>"},
                {"935576902826078209", "<@&935576902826078209>"},
                {"1066702576025866260", "<@&1066702576025866260>"},
                {"1066702211217887232", "<@&1066702211217887232>"},
                {"1065942174539202590", "<@&1065942174539202590>"},
                {"1081305694755639376", "<@&1081305694755639376>"},
                {"888503993117073420", "<@&888503993117073420>"},
                {"904032740394041364", "<@&904032740394041364>"},
                {"1145069370821849209", "<@&1145069370821849209>"},
                {"1085914333160165417", "<@&1085914333160165417>"},
                {"1085914226310271126", "<@&1085914226310271126>"},
                {"1081305944652255292", "<@&1081305944652255292>"},
                {"906598926688321578", "<@&906598926688321578>"},
                {"1062409445470113883", "<@&1062409445470113883>"},
                {"1084469016707481630", "<@&1084469016707481630>"},
                {"1068989280225083412", "<@&1068989280225083412>"},
                {"1068989467261665282", "<@&1068989467261665282>"},
                {"1067186701736349818", "<@&1067186701736349818>"}

                /*
                     Besitzer: 888391247365046272

                     Mitbesitzer: 888504288198918185

                     Management: 1085908394092793946

                     SrAdmin: 1074767531509809202

                     Admin: 888509168330358805



                     Team-Verwaltung: 1064863997062561803

                      Eventverwaltung: 1082387802215424130

                     Technick-Verwaltung : 1131233998379700274

                     Discord-Verwaltung: 935576902826078209

                     SrDev: 1066702576025866260

                     Dev: 1066702211217887232

                     JrDev: 1065942174539202590

                     SrModerator: 1081305694755639376

                     Moderator: 888503993117073420

                     JrMod: 904032740394041364

                     Content: 1085914333160165417

                     jrContent: 1085914226310271126

                     SrSup: 1081305944652255292

                     Sup: 906598926688321578

                     JrSup: 1062409445470113883

                     SrBuilder: 1084469016707481630

                     Builder: 1068989280225083412

                     JrBuilder: 1068989467261665282

                     Guide: 1067186701736349818

                      */};

        StringBuilder descriptionBuilder = new StringBuilder();

        for (String[] roleData : roles) {
            String roleId = roleData[0];
            String roleMention = roleData[1];

            Role role = guild.getRoleById(roleId);
            if (role != null) {
                List<String> membersWithRole = new ArrayList<>();
                for (Member member : guild.getMembersWithRoles(role)) {
                    membersWithRole.add(member.getAsMention());
                }

                descriptionBuilder.append("➥ " + roleMention).append("  ⸨").append("**" + membersWithRole.size() + "**").append("⸩\n");
                if (!membersWithRole.isEmpty()) {
                    descriptionBuilder.append(String.join(", ", membersWithRole)).append("\n");
                } else {
                    descriptionBuilder.append(" ");
                }
                descriptionBuilder.append("\n");
            }
        }
        builder.setDescription(descriptionBuilder.toString());
        builder.setFooter("Letztes Update: " + last_update + " um " + formattedTime + " Uhr", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        return builder.build();
    }
}
