package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

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
                .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1160508092757319740/Unbedasasdasdasddsasdasdadsdadasadnannt.png?ex=6534ea62&is=65227562&hm=68a973ca3f32dc868c74248d5937616d87283e88372d67128e36e5e0a97588ed&")
                .setImage(Main.getUpdateimage());

        textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        event.getGuild().addRoleToMember(member, role).queue();

        VoiceChannel voiceChannel = event.getGuild().getVoiceChannelById("1139724927155576954");
        if (voiceChannel != null) {
            voiceChannel.getManager().setName("\uD83D\uDCCC・ | User: " + event.getGuild().getMemberCount()).queue();

        }
    }
}

