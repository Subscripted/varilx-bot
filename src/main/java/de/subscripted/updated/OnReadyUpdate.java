package de.subscripted.updated;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnReadyUpdate extends ListenerAdapter {
    public void onGuildReady(GuildReadyEvent event) {
        String roleIdToAdd = "1137107740838342796";
        addRoleToBot(event, roleIdToAdd);
        removeRoleFromBot(event, roleIdToAdd);
    }

    private void addRoleToBot(GuildReadyEvent event, String roleId) {
        if (event.getGuild().getRoleById(roleId) == null) {
            System.out.println("Die Rolle wurde nicht gefunden.");
            return;
        }

        Member botMember = event.getGuild().getSelfMember();

        event.getGuild().addRoleToMember(botMember, event.getGuild().getRoleById(roleId)).queue(
                success -> System.out.println("Rolle erfolgreich hinzugefügt!"),
                error -> System.out.println("Fehler beim Hinzufügen der Rolle: " + error.getMessage())
        );
    }

    private void removeRoleFromBot(GuildReadyEvent event, String roleId) {
        if (event.getGuild().getRoleById(roleId) == null) {
            System.out.println("Die Rolle wurde nicht gefunden.");
            return;
        }

        Member botMember = event.getGuild().getSelfMember();
        event.getGuild().removeRoleFromMember(botMember, event.getGuild().getRoleById(roleId)).queue(
                success -> System.out.println("Rolle erfolgreich entfernt!"),
                error -> System.out.println("Fehler beim Entfernen der Rolle: " + error.getMessage())
        );
    }
}
