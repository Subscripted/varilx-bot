package de.subscripted.admin;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;


public class TeamAdd extends ListenerAdapter {


    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equalsIgnoreCase("team")) {

            Member member = event.getMember();
            assert member != null;

            if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Varilx System")
                        .setColor(Color.RED)
                        .setDescription("Du hast keine Berechtigung auf diesen Command!")
                        .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                return;
            }

            String subcommand = event.getSubcommandName();
            assert subcommand != null;


            if (subcommand.equalsIgnoreCase("add")) {
                Member targetMember = event.getOption("member").getAsMember();
                TextChannel tc = event.getGuild().getTextChannelById("1086316495405068308");
                Role role = event.getOption("role").getAsRole();
                Role teamrole = event.getGuild().getRoleById("1003618027037786205");
                Role testphase = event.getGuild().getRoleById("1065943390937677855");
                if (targetMember.getRoles().contains(role) || targetMember.getRoles().contains(teamrole)) {
                    event.reply("Der Nutzer hat diese Rolle bereits!").setEphemeral(true).queue();
                    return;
                }
                if (role == event.getGuild().getRoleById("1085914226310271126")
                        || role == event.getGuild().getRoleById("1062409445470113883")
                        || role == event.getGuild().getRoleById("1065942174539202590")
                        || role == event.getGuild().getRoleById("1067186701736349818")
                        || role == event.getGuild().getRoleById("1068989467261665282")) {
                    event.getGuild().addRoleToMember(targetMember, testphase).queue();
                    event.getGuild().addRoleToMember(targetMember, role).queue();
                    event.getGuild().addRoleToMember(targetMember, teamrole).queue();
                    tc.sendMessage(teamrole.getAsMention() + "\n**Team Neuzugang**\n"
                            + " \n"
                            + "Wir begrüßen " + targetMember.getAsMention() + " im Bereich " + role.getAsMention() + " und wünschen eine lange und gute Zusammenarbeit!\n" +
                            "\n" +
                            "Mit freundlichen Grüßen,\n"
                            + Main.getJda().getSelfUser().getAsMention()).queue();
                    event.reply("Du hast " + targetMember.getAsMention() + " in das " + role.getAsMention() + " Team aufgenommen!").setEphemeral(true).queue();
                }else {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Varilx System")
                            .setColor(Color.RED)
                            .setDescription("Du kannst nur Jr-Teamrollen in das Team adden!")
                            .setFooter("Varilx Team Feature | Update 2023 © ", Main.redfooter);
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                    event.replyEmbeds(embedBuilder.build()).queue();
                }
            }
            if (subcommand.equalsIgnoreCase("remove")) {
                Member targetMember = event.getOption("member").getAsMember();
                Role role = event.getOption("role").getAsRole();
                if (!member.getRoles().contains(role)) {
                    event.reply("This user does not have the role.").setEphemeral(true).queue();
                    return;
                }
                event.getGuild().removeRoleFromMember(member, role).queue();
                TextChannel botLogsChannel = event.getGuild().getTextChannelById("1135323930811432990");
                botLogsChannel.sendMessage(member.getAsMention() + " Left the " + role.getAsMention() + " team.").queue();
                event.reply("You have remove the role " + role.getAsMention() + " from " + member.getAsMention() + ".").setEphemeral(true).queue();
            }
        }
    }

    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        TextChannel staffListChannel = event.getGuild().getTextChannelById("1135323930811432990");
        staffListChannel.getHistory().retrievePast(1).queue(messages -> {
            messages.get(0).editMessageEmbeds(StaffList.sendStaffEmbed(event.getGuild())).queue();
        });
    }

    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        TextChannel staffListChannel = event.getGuild().getTextChannelById("1135323930811432990");
        staffListChannel.getHistory().retrievePast(1).queue(messages -> {
            messages.get(0).editMessageEmbeds(StaffList.sendStaffEmbed(event.getGuild())).queue();
        });
    }
}
