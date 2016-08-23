package com.simpleduino.Relationship.Runnables;

import com.simpleduino.Relationship.Messaging.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */
public class FriendsChecker extends BukkitRunnable {

    @Override
    public void run() {
        if(Bukkit.getOnlinePlayers().size() >= 1) {
            new MessageSender("PlayerList", new String[]{"ALL"});
        }
    }

}
