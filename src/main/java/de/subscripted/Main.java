package de.subscripted;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.subscripted.Music.core.MusicCommand;
import de.subscripted.admin.*;
import de.subscripted.backend.ButtonInteraction;
import de.subscripted.backend.ModalInteractionsHandler;
import de.subscripted.backend.XPSystem;
import de.subscripted.games.EightBall;
import de.subscripted.serversafety.*;
import de.subscripted.sql.TicketCountSQLManager;
import de.subscripted.sql.TicketSQLManager;
import de.subscripted.sql.WarnSQLManager;
import de.subscripted.sql.XpSQLManager;
import de.subscripted.support.*;
import de.subscripted.updated.OnReadyUpdate;
import de.subscripted.user.*;
import de.subscripted.working.EmbedBuilderBetaModalInteractions;
import de.subscripted.working.EmbedBuilderButtons;
import de.subscripted.working.SendEmbedCommand;
import de.subscripted.working.TicketDropDown;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Main {


    @Getter
    private static String token;
    @Getter
    private static String thumbnail = "https://cdn.discordapp.com/attachments/1055223755909111808/1160508079419424840/Unbenanntdsadasd-2.png?ex=6534ea5f&is=6522755f&hm=00ea7dd8a3fd0c5dfcfccfa6952527b679094abf07d22143fee44b0b7221aa4a&";
    @Getter
    private static String updateimage = "https://cdn.discordapp.com/attachments/1055223755909111808/1160507955507101736/Varilx_Tube-hosting_version.png?ex=6534ea41&is=65227541&hm=e2dad9d371a1f8a26f84ab29871fc2a754b0135ccbc76c557d9f7c30dbaf371f&";
    public static XpSQLManager xpSqlManager;


    public static TicketSQLManager ticketSQLManager;

    public static WarnSQLManager warnSQLManager;

    public static TicketCountSQLManager ticketCountSQLManager;


    public static String redfooter = "https://cdn.discordapp.com/attachments/1055223755909111808/1133836888449503262/Unbdassddasadsadssaasadedsddsdsadsanannt-1.png";
    private static final List<String> statusMessages = new ArrayList<>();
    private static final List<Activity.ActivityType> activityTypes = new ArrayList<>();
    private static final List<String> activityDetails = new ArrayList<>();
    private static int currentStatusIndex = 0;

    @Getter
    private static JDA jda;


    public static void main(String[] args) throws LoginException, InterruptedException, SQLException {

        loadFiles();


        xpSqlManager = new XpSQLManager();
        ticketSQLManager = new TicketSQLManager();
        warnSQLManager = new WarnSQLManager();
        ticketCountSQLManager = new TicketCountSQLManager();
        TicketSQLManager.initializeDatabase();
        TicketCountSQLManager.initializeDatabase();


        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .setStatus(OnlineStatus.ONLINE)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableCache(CacheFlag.VOICE_STATE)
                .setAutoReconnect(true)
                .setBulkDeleteSplittingEnabled(false)
                .addEventListeners(
                        new TicketButtonInside(ticketSQLManager),
                        new ModalInteractionsHandler(ticketCountSQLManager),
                        new Builder(),
                        new SupportVoiceJoin("970663709573783553", "1153687092803686471"),
                        new OnJoin(),
                        new Mute(),
                        new Unmute(),
                        new NickCommand(),
                        new Ban(),
                        new BinaryTranslationCommand(),
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
                        new Vorschläge(),
                        new TicketDropDown(),
                        new Chatfilter(),
                        new ReactionRoles(),
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
                        new MoveallTo(),
                        new Move(),
                        new BewerbungsBuilder(),
                        new RulesBuilder(),
                        new onLeave(),
                        new Serverinfo(),
                        new Bugreport(),
                        new Userinfos(),
                        new ButtonInteraction(ticketSQLManager, ticketCountSQLManager),
                        new EightBall(),
                        new GiveawayCommand(),
                        new MusicCommand(),
                        new SendEmbedCommand(),
                        new EmbedBuilderBetaModalInteractions(),
                        new EmbedBuilderButtons(),
                        new Warn(warnSQLManager),
                        new GetWarns(warnSQLManager),
                        new ClearWarns(warnSQLManager)
                ).build().awaitReady();

        getJda().getGuilds().forEach(guild -> guild.getAudioManager().setSelfDeafened(true));
        getJda().getGuilds().forEach(guild -> guild.getAudioManager().setSelfMuted(false));

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
            }
        }, 0, 3000);


        Guild guild = jda.getGuildById("886262410489520168");

        jda.getGatewayIntents().add(GatewayIntent.GUILD_MEMBERS);


        assert guild != null;
        guild.updateCommands().addCommands(
                Commands.slash("nick", "nick").addOption(OptionType.USER, "nutzer", "jaja", true).addOption(OptionType.STRING, "newname", "test", true),
                Commands.slash("unclaim", "Unclaime ein Ticket"),
                Commands.slash("hilfe", "Dieser Befehl gibt an, wie du hier Hilfe erhalten kannst."),
                Commands.slash("8ball", "Dem 8Ball kannst du verschiedene Fragen stellen").addOption(OptionType.STRING, "message", "Deine Frage bzw Nachricht", true),
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
                Commands.slash("sendembed", "uwu").addOption(OptionType.STRING, "code", "code", true).addOption(OptionType.CHANNEL, "channel", "channel", true),
                Commands.slash("userinfo", "Zeigt dir Infos über einen Nutzer an").addOption(OptionType.USER, "nutzer", "Nutzer", true),
                Commands.slash("bugreport", "Reporte einen Bug!"),
                Commands.slash("serverinfo", "Zeigt dir Infos über den Server an!"),
                Commands.slash("promote", "Promote einen Teamler").addOption(OptionType.USER, "nutzer", "Nutzer den du promoten willst!", true),
                Commands.slash("demote", "Demote einen Teamler").addOption(OptionType.USER, "nutzer", "Nutzer den du demoten willst!", true),
                Commands.slash("ping", "Ping"),
                Commands.slash("binärtotext", "Deine Binär- Zahlen").addOption(OptionType.STRING, "code", "Von Binär in Text oder Text in Binär", true),
                Commands.slash("embedbuilderbeta", "embeds"),
                Commands.slash("move", "Move einen oder mehrere Nutzer zu dir").addOption(OptionType.STRING, "nutzer", "Der / Die Nutzer", true),
                Commands.slash("timeout", "Timeoute einen User").addOption(OptionType.USER, "nutzer", "nutzer", true).addOption(OptionType.STRING, "zeit", "zeit", true),
                Commands.slash("addusertoticket", "Adde einen Nutzer zu einem Ticket").addOption(OptionType.USER, "user", "Nutzer den de entfernen willst!", true),
                Commands.slash("embedbuilder", "Baue deinen embed").addOption(OptionType.STRING, "title", "title", false).addOption(OptionType.STRING, "field1", "field1", false).addOption(OptionType.STRING, "value1", "value1", false).addOption(OptionType.STRING, "field2", "field2", false).addOption(OptionType.STRING, "value2", "value2", false).addOption(OptionType.STRING, "field3", "field3", false).addOption(OptionType.STRING, "value3", "value3", false).addOption(OptionType.STRING, "field4", "flied4", false).addOption(OptionType.STRING, "value4", "value4", false).addOption(OptionType.STRING, "description", "description", false).addOption(OptionType.STRING, "footer", "footer", false).addOption(OptionType.STRING, "image", "image", false).addOption(OptionType.STRING, "thumbnail", "thumbnail", false).addOption(OptionType.STRING, "color", "color", false).addOption(OptionType.STRING, "author", "author", false).addOption(OptionType.STRING, "footerimage", "footerimage", false),
                Commands.slash("giveaway", "Startet eine Verlosung")
                        .addOption(OptionType.STRING, "preis", "Gib hier den Preis ein.", true)
                        .addOption(OptionType.INTEGER, "gewinner", "Gib die Anzahl der Gewinner ein.", true)
                        .addOption(OptionType.STRING, "dauer", "Gib die Dauer des Giveaways in Sekunden ein.", true),
                Commands.slash("warn", "Verwarne einen Nutzer").addOption(OptionType.USER, "nutzer", "Nutzer den du verwarnen willst!"),
                Commands.slash("getwarn", "Reason für warns").addOption(OptionType.USER, "nutzer", "Nutzer halt", true),
                Commands.slash("team", "Staff Command")
                        .addSubcommands(new SubcommandData("add", "Füge jemanden in das Team hinzu")
                                .addOption(OptionType.USER, "member", "Nutzer den du hinzufügen willst", true)
                                .addOption(OptionType.ROLE, "role", "Die Rolle die der Nutzer bekommt", true)),
                Commands.slash("moveall", "Move alle die in deinem Channel sind wo anders hin!")
                        .addSubcommands(new SubcommandData("to", "Move alle die in deinem Channel sind wo anders hin!")
                                .addOption(OptionType.CHANNEL, "channel", "Channel in den alle Ggemoved werden sollen!", true)),
                Commands.slash("deletewarns", "Lösche alle warns eines Nutzers").addOption(OptionType.USER, "nutzer", "Nutzer von dem du die Warns löschen willst!"));

        assert guild != null;
        guild.updateCommands().queue();
        startGiveawayRunnable();
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

    private static void startGiveawayRunnable() {
        GiveawayRunnable.run();
    }

    @SneakyThrows
    private static void loadFiles() {

        File file = new File("config.json");

        if (!file.exists()) {
            if (!file.createNewFile()) {
                System.out.println("Could not create config file.");
                return;
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", "TOKEN_HERE");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        }

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(file));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        token = jsonObject.get("token").getAsString();
    }

}

