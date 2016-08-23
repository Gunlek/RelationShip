package com.simpleduino.Relationship.Listeners;

import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import com.simpleduino.Relationship.Messaging.MessageSender;
import com.simpleduino.Relationship.RelationshipPlugin;
import com.simpleduino.Relationship.SQL.RelationshipSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */
public class PlayerListener implements Listener {

    private RelationshipSQL relationshipSQL = new RelationshipSQL();

    public static ArrayList<String> onlinePlayers = new ArrayList<>();
    public static HashMap<Player, ArrayList<String>> onlineFriends = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        e.setJoinMessage("");
        if(!PlayerListener.onlinePlayers.contains(e.getPlayer().getName()))
            PlayerListener.onlinePlayers.remove(e.getPlayer().getName());
        new MessageSender("PlayerList", new String[]{"ALL"});
        final Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(RelationshipPlugin.getPlugin(RelationshipPlugin.class), new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<String> friendList = new ArrayList<>();
                if(onlineFriends.containsKey(p))
                    onlineFriends.remove(p);
                for(String friend : relationshipSQL.getFriendList(p.getUniqueId().toString()))
                {
                    if(onlinePlayers != null) {
                        if (onlinePlayers.contains(friend))
                            friendList.add(friend);
                    }
                }

                onlineFriends.put(p, friendList);

                p.sendMessage(ChatColor.YELLOW + "Vous avez "+ChatColor.RED+Integer.toString(onlineFriends.get(p).size())+ChatColor.YELLOW+" ami(s) en ligne");
            }
        }, 10L);
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent e)
    {
        e.setQuitMessage("");
        if(PlayerListener.onlinePlayers.contains(e.getPlayer().getName()))
            PlayerListener.onlinePlayers.remove(e.getPlayer().getName());
    }
}
