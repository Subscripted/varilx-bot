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

public class Mute extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("mute"))
            return;

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
                    .setDescription("Du kannst dich nicht selber Muten!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if (target.getId().equals("809536993972584449")){
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung Subscripted zu Muten du fogggggel!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if (target.getRoles().contains(role)){
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription(target.getAsMention() +" ist schon gemuted!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        String reason = event.getOption("grund").getAsString();

        if (target != null) {
            event.getGuild().addRoleToMember(target, role).queue(
                    success -> {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setTitle("Varilx Mute")
                                .setColor(Color.RED)
                                .setDescription("Du wurdest auf Varilx.de gemutet!")
                                .addField("Grund:", reason, false)
                                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.redfooter);

                        EmbedBuilder embedBuilder1 = new EmbedBuilder()
                                .setTitle("Varilx Mute")
                                .setColor(Color.RED)
                                .setDescription(target.getAsMention() + " wurde von " + event.getMember().getAsMention() + " auf Varilx.de gemutet!")
                                .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png")
                                .addField("Grund:", reason, false)
                                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.redfooter);
                        TextChannel tc = event.getGuild().getTextChannelById("1134461790818947122");

                        EmbedBuilder embedBuilder2 = new EmbedBuilder()
                                .setTitle("Varilx Mute")
                                .setColor(Color.GREEN)
                                .setDescription("Du hast erfolgreich " + target.getAsMention() + " gemutet!")
                                .addField("Grund:", reason, false)
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
                        // Handle errors if the role couldn't be added to the member
                        System.out.println("Failed to add role to the member: " + error.getMessage());
                    }
            );
        } else {
            System.out.println("ne");
        }
    }

    private void failed(User user) {
        System.out.println("Es konnte keine Mute Nachricht an den Nutzer " + user.getName() + " gesendet werden.");
    }
}
