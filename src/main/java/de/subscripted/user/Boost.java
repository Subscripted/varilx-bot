package de.subscripted.user;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Boost extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Role role = event.getGuild().getRoleById("888392409673793576");
        Role addedRole = event.getRoles().get(0);
        if (role == null){
            return;
        }
        if (!addedRole.equals(role)) {
            return;
        }
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Varilx Boost")
                .setColor(Color.MAGENTA)
                .setDescription("Danke an " + member.getAsMention() + " das du **\n" + guild.getName() + "** geboosted hast!")
                .setFooter("Varilx Boost Feature | Update 2023 ©", "https://cdn.discordapp.com/attachments/1113525397750042656/1132754639125741629/624057848f3f2.png")
                .setThumbnail("https://cdn.discordapp.com/attachments/1055223755909111808/1132753302182961222/Undassdabedsddsdsadsanannt-1.png");
        event.getGuild().getTextChannelById("888513332305363025").sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
