package de.subscripted.user;

import de.subscripted.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class ReactionRoles extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().equals("!reactionroles"))
            return;
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
            return;
        TextChannel tc = event.getChannel().asTextChannel();
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("<:Varilx:1128364477335748698>  Varilx Reaction Roles")
                .setColor(Color.GREEN)
                .setThumbnail("https://cdn.discordapp.com/attachments/915633823675449344/1134431444526190592/Unbenadasadsasnnt.png")
                .setDescription("In diesem **Interface** kannst du **dir** Rollen aussuchen, die mit den Informationen verbunden sind. \n" +
                        "Anstatt das wir **everyone** pingen, kannst du dir selber aussuchen zu welchen Themen du einen Ping bekommen willst.\n"
                        + " \n"
                        + "~~---»-----------------------------------------«---~~"
                        + " \n" +
                        " - <@&1098654469710954728> <:VarilxLogoLila:1128364900205469849> -> Verpasse keine Events!\n" +
                        " \n" +
                        "~~---»-----------------------------------------«---~~"
                        + " \n" +
                        " - <@&1098654811030831144> <:VarilxLogoBlau:1128364932111548416> -> Verpasse keine Changelogs!\n" +
                        " \n" +
                        "~~---»-----------------------------------------«---~~"
                        + " \n" +
                        " - <@&1098654568658776124> <:Varilx:1128364477335748698> -> Verpasse keine generellen Infos\n" +
                        " \n" +
                        "~~---»-----------------------------------------«---~~"
                        + " \n" +
                        " - <@&1098652243609272361> <:VarilxBedrock:1128365267530035250> -> Verpasse keine Infos für Minecraft BE\n" +
                        " \n" +
                        "~~---»-----------------------------------------«---~~"
                        + " \n" +
                        " - <@&1098652312307769375> <:VarilxLogoOrange:1128364998096338954> -> Verpasse keine Infos zu Minecraft Java\n" +
                        " \n" +
                        "~~---»-----------------------------------------«---~~")
                .setFooter("Varilx Roles Feature | Update 2023 ©", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        Button Event = Button.secondary("event", " ").withEmoji(Emoji.fromFormatted("<:VarilxLogoLila:1128364900205469849>"));
        Button Changelog = Button.secondary("changelog", " ").withEmoji(Emoji.fromFormatted("<:VarilxLogoBlau:1128364932111548416>"));
        Button Info = Button.secondary("info", " ").withEmoji(Emoji.fromFormatted("<:Varilx:1128364477335748698>"));
        Button bedrock = Button.secondary("bedrock", " ").withEmoji(Emoji.fromFormatted("<:VarilxBedrock:1128365267530035250>"));
        Button java = Button.secondary("java", " ").withEmoji(Emoji.fromFormatted("<:VarilxLogoOrange:1128364998096338954>"));
        Button partner = Button.link("https://tube-hosting.com/pricing", "Partner").withEmoji(Emoji.fromFormatted("<:TubehostingVarilx:1101657813794693120>"));


        tc.sendMessageEmbeds(embedBuilder.build()).addActionRow(Event, Changelog, Info, bedrock, java).addActionRow(partner).queue();
        event.getMessage().delete().queue();

    }
}

