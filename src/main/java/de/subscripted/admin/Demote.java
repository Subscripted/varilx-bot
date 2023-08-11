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
            String message = event.getOption("message").getAsString();
            message = message.replace("\\n", System.lineSeparator());
            Guild guild = event.getGuild();
            TextChannel tc = event.getGuild().getTextChannelById("1086316495405068308");
            assert member != null;

            // Überprüfung der zu überprüfenden Rollen
            Role testphase = event.getGuild().getRoleById("1065943390937677855");
            Role teamRole = event.getGuild().getRoleById("1003618027037786205");
            Role JrContent = event.getGuild().getRoleById("1085914226310271126");
            Role Content = event.getGuild().getRoleById("1085914333160165417");
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

            // Check if the member has the required permission
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

            // Check if the target member is in the team
            if (!targetmember.getRoles().contains(teamRole)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx System")
                        .setColor(Color.RED)
                        .setDescription(targetmember.getAsMention() + " ist nicht im Team!")
                        .setFooter("Varilx Team Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }


            // Switch-Case-Block für die Rollenüberprüfung
            switch (targetmember.getRoles().get(0).getId()) {
                case "1085914333160165417":
                    guild.removeRoleFromMember(targetmember, Content).queue();
                    guild.addRoleToMember(targetmember, JrContent).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrContent.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "906598926688321578":
                    guild.removeRoleFromMember(targetmember, Sup).queue();
                    guild.addRoleToMember(targetmember, JrSup).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrSup.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1081305944652255292":
                    guild.removeRoleFromMember(targetmember, SrSup).queue();
                    guild.addRoleToMember(targetmember, Sup).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Sup.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "904032740394041364":
                    guild.removeRoleFromMember(targetmember, JrMod).queue();
                    guild.removeRoleFromMember(targetmember, testphase).queue();
                    guild.addRoleToMember(targetmember, SrSup).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + SrSup.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "888503993117073420":
                    guild.removeRoleFromMember(targetmember, Mod).queue();
                    guild.addRoleToMember(targetmember, JrMod).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrMod.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1081305694755639376":
                    guild.removeRoleFromMember(targetmember, SrMod).queue();
                    guild.addRoleToMember(targetmember, Mod).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Mod.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1066702211217887232":
                    guild.removeRoleFromMember(targetmember, Dev).queue();
                    guild.addRoleToMember(targetmember, JrDev).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrDev.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1066702576025866260":
                    guild.removeRoleFromMember(targetmember, SrDev).queue();
                    guild.addRoleToMember(targetmember, Dev).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Dev.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1068989280225083412":
                    guild.removeRoleFromMember(targetmember, Builder).queue();
                    guild.addRoleToMember(targetmember, JrBuilder).queue();
                    guild.addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrBuilder.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1084469016707481630":
                    guild.removeRoleFromMember(targetmember, SrBuilder).queue();
                    guild.addRoleToMember(targetmember, Builder).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrBuilder.getAsMention() + " zurückgestuft!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
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


