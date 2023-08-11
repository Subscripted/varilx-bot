package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class OnJoin extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        TextChannel textChannel = event.getGuild().getTextChannelById("915680130528059463");
        assert textChannel != null;
        Role role = event.getGuild().getRoleById("888528416146612276");
        Member member = event.getMember();

        String emojiURL = event.getGuild().getEmojiById("1101657813794693120").getAsMention();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx x Hosted by Tubehosting")
                .setColor(Color.GREEN)
                .setDescription("Hey " + event.getMember().getAsMention() + " willkommen auf " + "\n" + "**Varilx.DE × Hosted by Tube-Hosting!**\n" +
                        "\n" +
                        "Ich Bitte dich das Regelwerk durchzulesen damit keine Unannehmlichkeiten entstehen. Wir Danke dir und viel Spaß!\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Unser Host Partner »\n" +
                        "https://discord.gg/tube-hosting ->" + emojiURL)
                .setFooter("Varilx.DE Netzwerk | Wilkommenshalle", Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                .setThumbnail("https://cdn-longterm.mee6.xyz/plugins/welcome/images/886262410489520168/a760d820d08b59ec044028c51483dbc8f196b48330314e7f317015f24fc66422.png")
                .setImage("https://cdn-longterm.mee6.xyz/plugins/welcome/images/886262410489520168/616250b3031b7010a2815e64e86581a02d011f396d5ab951717109abd2002829.png");

        textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        event.getGuild().addRoleToMember(member, role).queue();
    }
}