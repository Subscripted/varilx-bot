package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Demote extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("demote")) {
            Member member = event.getMember();
            Member targetmember = event.getOption("nutzer").getAsMember();
            Role teamrole = event.getGuild().getRoleById("1003618027037786205");
            Guild guild = event.getGuild();
            TextChannel tc = event.getGuild().getTextChannelById("1086316495405068308");
            assert member != null;

            Role testphase = event.getGuild().getRoleById("1065943390937677855");
            Role teamRole = event.getGuild().getRoleById("1003618027037786205");
            Role JrContent = event.getGuild().getRoleById("1085914226310271126");
            Role Content = event.getGuild().getRoleById("1085914333160165417");
            Role SrContent = event.getGuild().getRoleById("1145069370821849209");
            Role JrSup = event.getGuild().getRoleById("1062409445470113883");
            Role Sup = event.getGuild().getRoleById("906598926688321578");
            Role SrSup = event.getGuild().getRoleById("1081305944652255292");
            Role JrMod = event.getGuild().getRoleById("904032740394041364");
            Role Mod = event.getGuild().getRoleById("888503993117073420");
            Role SrMod = event.getGuild().getRoleById("1081305694755639376");
            Role JrDev = event.getGuild().getRoleById("1065942174539202590");
            Role Dev = event.getGuild().getRoleById("1066702211217887232");
            Role SrDev = event.getGuild().getRoleById("1066702576025866260");
            Role JrBuilder = event.getGuild().getRoleById("1068989467261665282");
            Role Builder = event.getGuild().getRoleById("1068989280225083412");
            Role SrBuilder = event.getGuild().getRoleById("1084469016707481630");

            if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx System")
                        .setColor(Color.RED)
                        .setDescription("Du hast keine Berechtigung auf diesen Command!")
                        .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }
            if (targetmember.getRoles().contains(JrDev) || targetmember.getRoles().contains(JrBuilder) || targetmember.getRoles().contains(JrSup) || targetmember.getRoles().contains(JrContent)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx Team")
                        .setColor(Color.RED)
                        .setDescription("Du kannst Mitglieder die auf der 'Jr' Rolle sind nicht weiter demoten!")
                        .setFooter("Varilx Team Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            }

            if (!targetmember.getRoles().contains(teamRole)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx System")
                        .setColor(Color.RED)
                        .setDescription(targetmember.getAsMention() + " ist nicht im Team!")
                        .setFooter("Varilx Team Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }


            switch (targetmember.getRoles().get(0).getId()) {
                case "1145069370821849209":
                    targetmember.modifyNickname("Content | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, SrContent).queue();
                    guild.addRoleToMember(targetmember, Content).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Content.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                    + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + Content.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;

                case "1085914333160165417":
                    targetmember.modifyNickname("JrContent | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, Content).queue();
                    guild.addRoleToMember(targetmember, JrContent).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrContent.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + JrContent.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "906598926688321578":
                    targetmember.modifyNickname("JrSup | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, Sup).queue();
                    guild.addRoleToMember(targetmember, JrSup).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrSup.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + JrSup.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "1081305944652255292":
                    targetmember.modifyNickname("Sup | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, SrSup).queue();
                    guild.addRoleToMember(targetmember, Sup).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Sup.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + Sup.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "904032740394041364":
                    targetmember.modifyNickname("SrSup | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, JrMod).queue();
                    guild.removeRoleFromMember(targetmember, testphase).queue();
                    guild.addRoleToMember(targetmember, SrSup).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + SrSup.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + SrSup.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "888503993117073420":
                    targetmember.modifyNickname("JrMod | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, Mod).queue();
                    guild.addRoleToMember(targetmember, JrMod).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrMod.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + JrMod.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "1081305694755639376":
                    targetmember.modifyNickname("Mod | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, SrMod).queue();
                    guild.addRoleToMember(targetmember, Mod).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Mod.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + Mod.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "1066702211217887232":
                    targetmember.modifyNickname("JrDev | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, Dev).queue();
                    guild.addRoleToMember(targetmember, JrDev).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrDev.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + JrDev.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "1066702576025866260":
                    targetmember.modifyNickname("Dev | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, SrDev).queue();
                    guild.addRoleToMember(targetmember, Dev).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Dev.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + Dev.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "1068989280225083412":
                    targetmember.modifyNickname("JrBuilder | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, Builder).queue();
                    guild.addRoleToMember(targetmember, JrBuilder).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrBuilder.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + JrBuilder.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;
                case "1084469016707481630":
                    targetmember.modifyNickname("Builder | " + targetmember.getEffectiveName()).queue();
                    guild.removeRoleFromMember(targetmember, SrBuilder).queue();
                    guild.addRoleToMember(targetmember, Builder).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Builder.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + "**Team - Demote** \n\n" + targetmember.getAsMention() + "Wurde auf " + Builder.getAsMention() + " zurück gestuft.\n\n **Mit freundlichen Grüßen,**\n" + Main.getJda().getSelfUser().getAsMention()).queue();
                    break;

                default:
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Varilx Demote")
                            .setColor(Color.RED)
                            .setDescription("Der Nutzer " + targetmember.getAsMention() + " hat keine zu überprüfende Rolle!")
                            .setFooter("Varilx Demote Feature | Update 2023 © ", Main.redfooter);
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                    break;
            }
        }
    }
}


