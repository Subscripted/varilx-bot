package de.subscripted.admin;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GiveawayRunnable {

    private static List<Giveaway> giveawayList;

    public static void run() {

        giveawayList = GiveawayManager.getGiveaways();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int currentTime = (int) (System.currentTimeMillis() / 1000);
                for (Giveaway giveaway : giveawayList) {
                    if (currentTime > giveaway.getEndTime()) {
                        GiveawayManager.endGiveaway(giveaway);
                    }
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    public static void updateRunnable() {
        giveawayList = GiveawayManager.getGiveaways();
    }
}
