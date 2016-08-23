package com.simpleduino.Relationship.Commands.Friends;

import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class JoinFriend {

    public static HashMap<Player, String> waitingJoin = new HashMap<>();

    public JoinFriend(Player p, String[] args)
    {
        if(args.length >= 2)
        {
            String dest = args[1];
            try {
                Player destPlayer = Bukkit.getPlayer(dest);
                p.sendMessage(ChatColor.YELLOW + "Vous êtes déjà sur le même serveur que "+ChatColor.RED+destPlayer.getName());
            }
            catch (Exception e)
            {
                waitingJoin.put(p, dest);
                new CustomMessageSender("ALL", "GetPlayerServer", new String[]{dest});
            }
        }
    }
}
