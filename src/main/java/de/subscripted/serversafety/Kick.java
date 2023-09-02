package de.subscripted.serversafety;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Kick extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if (!event.getName().equals("kick"))
            return;


        if (!event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung auf diesen Command!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        Member target = event.getOption("nutzer").getAsMember();
        Member member = event.getMember();

        if (target.getId().equals("809536993972584449")){
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung Subscripted zu kicken du fogggggel!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if (target == null){
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Dieser Nutzer existiert nicht!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }

        if (target == member){
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du kannst dich nicht selber Kicken!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        String reason = event.getOption("grund").getAsString();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Kick")
                .setColor(Color.RED)
                .setDescription("Du wurdest von Varilx.de gekickt!")
                .addField("Grund:", reason, false)
                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.redfooter);

        EmbedBuilder embedBuilder1 = new EmbedBuilder()
                .setTitle("Varilx Kick")
                .setColor(Color.RED)
                .setDescription(target.getEffectiveName() + " wurde von " + event.getMember().getAsMention() + " von Varilx.de gekickt!")
                .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png")
                .addField("Grund:", reason, false)
                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.redfooter);

        EmbedBuilder embedBuilder2 = new EmbedBuilder()
                .setTitle("Varilx Kick")
                .setColor(Color.GREEN)
                .setDescription("Du hast erfolgreich " + target.getEffectiveName() + " gekickt!")
                .addField("Grund:", reason, false)
                .setThumbnail("https://cdn.discordapp.com/attachments/1104111151937245284/1131975861801857074/Unbedsddsdsadsanannt-1.png")
                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        event.replyEmbeds(embedBuilder2.build()).setEphemeral(true).queue();

        TextChannel tc = event.getGuild().getTextChannelById("1134461790818947122");

        assert tc != null;

        tc.sendMessageEmbeds(embedBuilder1.build()).queue();

        target.getUser().openPrivateChannel().complete()
                .sendMessageEmbeds(embedBuilder.build()).queue(
                        error -> failed(target.getUser())
                );
        target.kick().reason(reason).queue();
    }

    private void failed(User user) {
        System.out.println("Es konnte keine Mute Nachricht an den Nutzer " + user.getName() + " gesendet werden.");
    }
}
