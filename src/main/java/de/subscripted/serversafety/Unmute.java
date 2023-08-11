package de.subscripted.serversafety;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Unmute extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("unmute")) {
            return;
        }

        Member member = event.getMember();
        Role role = event.getGuild().getRoleById("1134159388190462042");

        if (!member.hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        Member target = event.getOption("nutzer").getAsMember();
        assert target != null;

        if (target == member) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du kannst dich nicht selber Unmuten!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if (!target.getRoles().contains(role)){
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx Mute")
                    .setColor(Color.RED)
                    .setDescription("Der Nutzer " + target.getAsMention() +" ist nicht gemutet!")
                    .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png")
                    .setFooter("Varilx Safety Feature | Update 2023 ©", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }
        if (target != null) {
            event.getGuild().removeRoleFromMember(target, role).queue(
                    success -> {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setTitle("Varilx Mute")
                                .setColor(Color.GREEN)
                                .setDescription("Du wurdest auf Varilx.de unmutet!")
                                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());

                        EmbedBuilder embedBuilder1 = new EmbedBuilder()
                                .setTitle("Varilx Mute")
                                .setColor(Color.GREEN)
                                .setDescription(target.getAsMention() + " wurde von " + event.getMember().getAsMention() + " auf Varilx.de Unmuted!")
                                .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png")
                                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                        TextChannel tc = event.getGuild().getTextChannelById("1134461790818947122");

                        EmbedBuilder embedBuilder2 = new EmbedBuilder()
                                .setTitle("Varilx Mute")
                                .setColor(Color.GREEN)
                                .setDescription("Du hast erfolgreich " + target.getEffectiveName() + " unmutet!")
                                .setThumbnail("https://cdn.discordapp.com/attachments/1104111151937245284/1131975861801857074/Unbedsddsdsadsanannt-1.png")
                                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
                        event.replyEmbeds(embedBuilder2.build()).setEphemeral(true).queue();

                        assert tc != null;
                        tc.sendMessageEmbeds(embedBuilder1.build()).queue();

                        target.getUser().openPrivateChannel().complete()
                                .sendMessageEmbeds(embedBuilder.build()).queue(
                                        error -> failed(target.getUser())
                                );
                    },
                    error -> {
                        System.out.println("Failed the remove statement - role to member: " + error.getMessage());
                    }
            );
        } else {
            System.out.println("ne");
        }
    }

    private void failed(User user) {
        System.out.println("Es konnte keine Unmute Nachricht an den Nutzer " + user.getName() + " gesendet werden.");
    }
}
