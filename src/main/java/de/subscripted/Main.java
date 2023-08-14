package de.subscripted;

import de.subscripted.admin.*;
import de.subscripted.backend.XPSystem;
import de.subscripted.lavaplayer.*;
import de.subscripted.serversafety.*;
import de.subscripted.sql.MoneySQLManager;
import de.subscripted.sql.TicketSQLManager;
import de.subscripted.sql.XpSQLManager;
import de.subscripted.support.*;
import de.subscripted.updated.OnReadyUpdate;
import de.subscripted.user.*;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Main {

    public static XpSQLManager xpSqlManager;

    public static MoneySQLManager moneysqlManager;

    public static TicketSQLManager ticketSQLManager;


    public static String redfooter = "https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png";
    private static final List<String> statusMessages = new ArrayList<>();
    private static final List<Activity.ActivityType> activityTypes = new ArrayList<>();
    private static final List<String> activityDetails = new ArrayList<>();
    private static int currentStatusIndex = 0;

    @Getter
    private static JDA jda;


    public static void main(String[] args) throws LoginException, InterruptedException {

        xpSqlManager = new XpSQLManager();
        moneysqlManager = new MoneySQLManager();
        ticketSQLManager = new TicketSQLManager();
        TicketSQLManager.initializeDatabase();


        jda = JDABuilder.createDefault("OTUxMTI2NDAxNzA3Mjc4MzU2.GuBAYH.KVdQEUWB1JzxkR_XGV91ebKus24zFqoQt4DkVc")
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .setStatus(OnlineStatus.ONLINE)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(
                        new TicketButtonInteraction(),
                        new TicketButtonInside(ticketSQLManager),
                        new Builder(),
                        new SupportVoiceJoin("970663709573783553", "970410291630333963"),
                        new Giveaway(),
                        new OnJoin(),
                        new Mute(),
                        new Unmute(),
                        new Ban(),
                        new Unban(),
                        new Help(),
                        new Kick(),
                        new About(),
                        new Clear(),
                        new Send(),
                        new Embedbuilder(),
                        new Antiraid(),
                        new AddRemoveTicket(),
                        new Boost(),
                        new SetLevelCommand(),
                        new XPCommand(),
                        new Stop(),
                        new Play(),
                        new NowPlaying(),
                        new Skip(),
                        new Repeat(),
                        new Vorschläge(),
                        new Chatfilter(),
                        new ReactionRoles(),
                        new VorschlägeListener(),
                        new TeamAdd(),
                        new OnLoad(),
                        new Promote(),
                        new Demote(),
                        new Feedback(),
                        new OnReadyUpdate(),
                        new Ping(),
                        new XPCommand(),
                        new SetLevelCommand(),
                        new XPSystem(),
                        new WorkCommand(),
                        new CoinsCommand(),
                        new MoveallTo(),
                        new Move(),
                        new SetCoins(moneysqlManager),
                        new PayCommand(moneysqlManager),
                        new SetTop10Money(),
                        new BewerbungsBuilder(),
                        new NudeCMD(),
                        new RulesBuilder(),
                        new onLeave(),
                        new Serverinfo(),
                        new Bugreport(),
                        new Userinfos()
                ).build().awaitReady();


        statusMessages.add("Varilx Botsystem");
        statusMessages.add("Tube-Hosting.DE");
        statusMessages.add("Bedrock & Java");
        statusMessages.add("LabyMod is better!");

        activityTypes.add(Activity.ActivityType.STREAMING);
        activityTypes.add(Activity.ActivityType.WATCHING);
        activityTypes.add(Activity.ActivityType.PLAYING);
        activityTypes.add(Activity.ActivityType.WATCHING);

        activityDetails.add("Varilx Botsystem");
        activityDetails.add("Tube-Hosting.DE");
        activityDetails.add("Bedrock & Java");
        activityDetails.add("LabyMod is better!");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentStatusIndex++;
                if (currentStatusIndex >= statusMessages.size()) {
                    currentStatusIndex = 0;
                }
                String newStatusMessage = statusMessages.get(currentStatusIndex);
                Activity.ActivityType newActivityType = activityTypes.get(currentStatusIndex);
                String newActivityDetail = activityDetails.get(currentStatusIndex);
                Activity newActivity = Activity.of(newActivityType, newActivityDetail);
                jda.getPresence().setActivity(newActivity);
                System.out.println("Status changed to: " + newStatusMessage);
            }
        }, 0, 10000);


        Guild guild = jda.getGuildById("886262410489520168");

        jda.getGatewayIntents().add(GatewayIntent.GUILD_MEMBERS);

        jda.updateCommands().addCommands(
                Commands.slash("unclaim", "Unclaime ein Ticket"),
                Commands.slash("giveaway", "Starte ein Giveaway"),
                Commands.slash("hilfe", "Hilfe"),
                Commands.slash("mute", "Mute einen Nutzer").addOption(OptionType.USER, "nutzer", "Nutzer den du muten willst", true).addOption(OptionType.STRING, "grund", "Nenne den Grund für den Mute.", true),
                Commands.slash("unmute", "Unmute einen Nutzer").addOption(OptionType.USER, "nutzer", "Nutzer den du Unmuten willst", true),
                Commands.slash("ban", "Banne einen Nutzer").addOption(OptionType.USER, "nutzer", "Nutzer den du Bannen willst", true).addOption(OptionType.STRING, "grund", "Nenne den Grund für den Ban.", true),
                Commands.slash("unban", "Entbanne einen Nutzer").addOption(OptionType.USER, "nutzer", "Nutzer den du Entbannen willst", true),
                Commands.slash("kick", "Kicke einen Nutzer").addOption(OptionType.USER, "nutzer", "Nutzer den du kicken willst", true).addOption(OptionType.STRING, "grund", "Nenne den Grund für den Kick.", true),
                Commands.slash("level", "Zeigt dir dein Level an!").addOption(OptionType.USER, "nutzer", "Nutzer von dem du die XP ansehen willst", true),
                Commands.slash("setlevel", "Setze das Level eines Nutzers").addOption(OptionType.USER, "nutzer", "Der Nutzer, dessen Level du ändern möchtest", true).addOption(OptionType.INTEGER, "level", "Das neue Level, das du setzen möchtest", true),
                Commands.slash("about", "Infos über den Bot"),
                Commands.slash("send", "Sende eine Message mit dem Bot").addOption(OptionType.STRING, "nachricht", "Die Nachricht!", true),
                Commands.slash("removeuserfromticket", "Entferne einen User von einem Ticket").addOption(OptionType.USER, "user", "Nutzer den de entfernen willst!", true),
                Commands.slash("play", "Spiele ein Lied").addOption(OptionType.STRING, "name", "Lied das du spielen willst", true),
                Commands.slash("stop", "Stoppe ein lied ein Lied"),
                Commands.slash("nowplaying", "Welches lied spielt gerade!"),
                Commands.slash("queue", "Was ist in der queue"),
                Commands.slash("work", "work"),
                Commands.slash("corn", "uwu"),
                Commands.slash("userinfo", "user").addOption(OptionType.USER, "nutzer", "nutzer", true),
                Commands.slash("bugreport", "bugreport"),
                Commands.slash("serverinfo", "serverinfo"),
                Commands.slash("varonx", "Varonx").addOption(OptionType.USER, "nutzer", "nutzer", true),
                Commands.slash("repeat", "Repeat"),
                Commands.slash("promote", "Promote einen Teamler").addOption(OptionType.USER, "nutzer", "Nutzer den du promoten willst!", true).addOption(OptionType.STRING, "message", "message", true),
                Commands.slash("demote", "Demote einen Teamler").addOption(OptionType.USER, "nutzer", "Nutzer den du demoten willst!", true).addOption(OptionType.STRING, "message", "message", true),
                Commands.slash("skip", "Skippe ein Lied"),
                Commands.slash("vorschlag", "Erstelle einen Vorschlag"),
                Commands.slash("nuke", "nuke diesen server"),
                Commands.slash("ping", "Ping"),
                Commands.slash("pay", "pay").addOption(OptionType.USER, "nutzer", "nutzer", true).addOption(OptionType.STRING, "coins", "coins", true),
                Commands.slash("setcoins", "setcoins").addOption(OptionType.USER, "nutzer", "nutzer", true).addOption(OptionType.STRING, "coins", "coins", true),
                Commands.slash("move", "move").addOption(OptionType.STRING, "nutzer", "nutzer", true),
                Commands.slash("timeout", "Timeoute einen User").addOption(OptionType.USER, "nutzer", "nutzer", true).addOption(OptionType.STRING, "zeit", "zeit", true),
                Commands.slash("addusertoticket", "Adde einen Nutzer zu einem Ticket").addOption(OptionType.USER, "user", "Nutzer den de entfernen willst!", true),
                Commands.slash("embedbuilder", "Baue deinen embed").addOption(OptionType.STRING, "title", "title", false).addOption(OptionType.STRING, "field1", "field1", false).addOption(OptionType.STRING, "value1", "value1", false).addOption(OptionType.STRING, "field2", "field2", false).addOption(OptionType.STRING, "value2", "value2", false).addOption(OptionType.STRING, "field3", "field3", false).addOption(OptionType.STRING, "value3", "value3", false).addOption(OptionType.STRING, "field4", "flied4", false).addOption(OptionType.STRING, "value4", "value4", false).addOption(OptionType.STRING, "description", "description", false).addOption(OptionType.STRING, "footer", "footer", false).addOption(OptionType.STRING, "image", "image", false).addOption(OptionType.STRING, "thumbnail", "thumbnail", false).addOption(OptionType.STRING, "color", "color", false).addOption(OptionType.STRING, "author", "author", false).addOption(OptionType.STRING, "footerimage", "footerimage", false)
        ).queue();
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("team", "Staff Command")
                .addSubcommands(new SubcommandData[]{(new SubcommandData("add", "add staff member")).addOption(OptionType.USER, "member", "enter a user.", true).addOption(OptionType.ROLE, "role", "enter a role.", true)}));
        commandData.add(Commands.slash("moveall", "move")
                .addSubcommands(new SubcommandData[]{(new SubcommandData("to", "to").addOption(OptionType.CHANNEL, "channel", "channel", true))}));


    }

    private static EmbedBuilder embed = new EmbedBuilder()
            .setTitle("Error")
            .setColor(Color.RED);

    public static MessageEmbed getErrorEmbed(String msg) {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription(msg)
                .build();
    }

    public static MessageEmbed getSuccessEmbed(String msg) {
        return new EmbedBuilder()
                .setColor(Color.GREEN)
                .setDescription(msg)
                .build();
    }
}
