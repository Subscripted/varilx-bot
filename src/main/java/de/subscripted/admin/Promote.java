package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Promote extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {


        if (event.getName().equalsIgnoreCase("promote")) {

            Member member = event.getMember();
            Member targetmember = event.getOption("nutzer").getAsMember();
            String message = event.getOption("message").getAsString();
            message = message.replace("\\n", System.lineSeparator());
            Role teamrole = event.getGuild().getRoleById("1003618027037786205");
            TextChannel tc = event.getGuild().getTextChannelById("1086316495405068308");
            assert member != null;


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
            Role SrContent = event.getGuild().getRoleById("1145069370821849209");

            if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx System")
                        .setColor(Color.RED)
                        .setDescription("Du hast keine Berechtigung auf diesen Command!")
                        .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
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
            if (targetmember.getRoles().contains(SrDev) || targetmember.getRoles().contains(SrBuilder) || targetmember.getRoles().contains(SrMod) || targetmember.getRoles().contains(SrContent)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx Promote")
                        .setColor(Color.YELLOW)
                        .setDescription("Der Nutzer " + targetmember.getAsMention() + " kann von seinem jetzigen Rang nicht mehr weiter Promoted werden. Bitte beachte jedoch, wenn ein User eine Rolle mit 'Sr' davor stehen hat, es auch daran liegen kann, dass er nicht weiter Promoted werden kann. Abfrage um Nutzer nicht weiter promoten zu können: ```js\nif (targetmember.getRoles().contains(SrDev) || targetmember.getRoles().contains(SrBuilder) || targetmember.getRoles().contains(SrMod)){```")
                        .setFooter("Varilx Team Feature | Update 2023 © ", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            }
            switch (targetmember.getRoles().get(0).getId()) {
                case "1085914226310271126": // Junior Content
                    event.getGuild().removeRoleFromMember(targetmember, JrContent).queue();
                    event.getGuild().removeRoleFromMember(targetmember, testphase).queue();
                    event.getGuild().addRoleToMember(targetmember, Content).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Content.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1085914333160165417":
                    event.getGuild().removeRoleFromMember(targetmember, Content).queue();
                    event.getGuild().addRoleToMember(targetmember, SrContent).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + SrContent.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1062409445470113883": // Junior Supporter
                    event.getGuild().removeRoleFromMember(targetmember, JrSup).queue();
                    event.getGuild().removeRoleFromMember(targetmember, testphase).queue();
                    event.getGuild().addRoleToMember(targetmember, Sup).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Sup.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "906598926688321578":
                    event.getGuild().removeRoleFromMember(targetmember, Sup).queue();
                    event.getGuild().addRoleToMember(targetmember, SrSup).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + SrSup.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1081305944652255292":
                    event.getGuild().removeRoleFromMember(targetmember, SrSup).queue();
                    event.getGuild().addRoleToMember(targetmember, JrMod).queue();
                    event.getGuild().addRoleToMember(targetmember, testphase).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + JrMod.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                    + message).queue();

                    break;
                case "904032740394041364":
                    event.getGuild().removeRoleFromMember(targetmember, JrMod).queue();
                    event.getGuild().removeRoleFromMember(targetmember, testphase).queue();
                    event.getGuild().addRoleToMember(targetmember, Mod).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Mod.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                        + message).queue();
                    break;
                case "888503993117073420":
                    event.getGuild().removeRoleFromMember(targetmember, Mod).queue();
                    event.getGuild().addRoleToMember(targetmember, SrMod).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + SrMod.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1065942174539202590":
                    event.getGuild().removeRoleFromMember(targetmember, JrDev).queue();
                    event.getGuild().removeRoleFromMember(targetmember, testphase).queue();
                    event.getGuild().addRoleToMember(targetmember, Dev).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Dev.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1066702211217887232":
                    event.getGuild().removeRoleFromMember(targetmember, Dev).queue();
                    event.getGuild().addRoleToMember(targetmember, SrDev).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + SrDev.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1068989467261665282":
                    event.getGuild().removeRoleFromMember(targetmember, JrBuilder).queue();
                    event.getGuild().addRoleToMember(targetmember, Builder).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + Builder.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();
                    break;
                case "1068989280225083412":
                    event.getGuild().removeRoleFromMember(targetmember, Builder).queue();
                    event.getGuild().addRoleToMember(targetmember, SrBuilder).queue();
                    event.reply("Du hast " + targetmember.getAsMention() + " auf " + SrBuilder.getAsMention() + " befördert!").setEphemeral(true).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n"
                            + message).queue();

                    break;

                default:
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Varilx Promote")
                            .setColor(Color.RED)
                            .setDescription("Der Nutzer " + targetmember.getAsMention() + " hat keine zu überprüfende Rolle!")
                            .setFooter("Varilx Promote Feature | Update 2023 © ", Main.redfooter);
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                    break;

            }
        }
    }
}

