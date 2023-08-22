package de.subscripted.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Giveaway {
    private User creator;
    private String prize;
    private String channelId;
    private String messageId;
    private int winners;
    private int endTime;
    private List<String> users;
}
