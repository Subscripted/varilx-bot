package de.subscripted.Unused;

import de.subscripted.Unused.JobSQLManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CreateJob extends ListenerAdapter {

    private JobSQLManager sqlManager;

    public CreateJob(JobSQLManager jobSQLManager) {
        sqlManager = new JobSQLManager();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
            return;
        if (event.getName().equals("createjob")) {
            if (!event.getUser().isBot()) {
                String jobName = event.getOption("jobname").getAsString();

                sqlManager.createJob(jobName);

                event.reply("Job '" + jobName + "' wurde erstellt!").queue();
            }
        }
    }

    public void closeConnection() {
        sqlManager.closeConnection();
    }
}
