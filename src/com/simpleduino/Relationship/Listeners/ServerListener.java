package com.simpleduino.Relationship.Listeners;

import com.simpleduino.Relationship.Events.OnlinePlayerUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Simple-Duino on 11/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class ServerListener implements Listener {

    private File friendFile = new File("plugins/Relationship/friends.yml");
    private YamlConfiguration friendList = YamlConfiguration.loadConfiguration(friendFile);

    @EventHandler
    public void onOnlinePlayerListUpdate(OnlinePlayerUpdateEvent e)
    {
        ArrayList<String> newPlayerList = e.getNewPlayerList();
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(friendList.isSet(p.getUniqueId().toString()+".friends")) {
                ArrayList<String> playerFriends = new ArrayList<>();
                for (String friend : friendList.get(p.getUniqueId().toString() + ".friends").toString().split(", ")) {
                    playerFriends.add(friend);
                }

                PlayerListener.onlineFriends.remove(p);
                ArrayList<String> onlineFriends = new ArrayList<>();
                for (String friend : playerFriends) {
                    if (newPlayerList.contains(friend)) {
                        onlineFriends.add(friend);
                    }
                }

                PlayerListener.onlineFriends.put(p, onlineFriends);
            }
        }
    }

}
