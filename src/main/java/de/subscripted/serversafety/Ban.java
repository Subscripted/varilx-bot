package de.subscripted.serversafety;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Ban extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("ban"))
            return;




        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
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

        if (target.getId().equals("809536993972584449")){
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du hast keine Berechtigung Subscripted zu Bannen du fogggggel!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if (target == event.getMember()) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Varilx System")
                    .setColor(Color.RED)
                    .setDescription("Du kannst dich nicht selber Bannen!")
                    .setFooter("Varilx Safety Feature | Update 2023 © ", Main.redfooter);
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }


        String reason = event.getOption("grund").getAsString();


        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Ban")
                .setColor(Color.RED)
                .setDescription("Du wurdest auf Varilx.de gebannt!")
                .addField("Grund:", reason, false)
                .addField("Entbannungsantra: ", "https://forum.varilx.de/forum/view/9-entbannungsantrag/", false)
                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.redfooter);
        target.getUser().openPrivateChannel().complete()
                .sendMessageEmbeds(embedBuilder.build()).queue();


        target.ban(0, TimeUnit.MICROSECONDS).reason(reason).queue();
        EmbedBuilder embedBuilder1 = new EmbedBuilder()
                .setTitle("Varilx Ban")
                .setColor(Color.RED)
                .setDescription(target.getEffectiveName() + " wurde von " + event.getMember().getAsMention() + " auf Varilx.de gebannt!")
                .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png")
                .addField("Grund:", reason, false)
                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.redfooter);

        EmbedBuilder embedBuilder2 = new EmbedBuilder()
                .setTitle("Varilx Ban")
                .setColor(Color.GREEN)
                .setDescription("Du hast erfolgreich " + target.getEffectiveName() + " gebannt!")
                .addField("Grund:", reason, false)
                .setThumbnail("https://cdn.discordapp.com/attachments/1104111151937245284/1131975861801857074/Unbedsddsdsadsanannt-1.png")
                .setFooter("Varilx Safety Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        event.replyEmbeds(embedBuilder2.build()).setEphemeral(true).queue();

        TextChannel tc = event.getGuild().getTextChannelById("1134461790818947122");

        tc.sendMessageEmbeds(embedBuilder1.build()).queue();




    }
}