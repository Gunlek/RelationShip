package com.simpleduino.Relationship.Commands.Friends;

import com.simpleduino.Relationship.Listeners.FriendsListener;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 11/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class DenyFriend {

    public DenyFriend(Player p) {
        String playerValidation = null;
        for(String pValid : FriendsListener.friendRequest.keySet())
        {
            if(FriendsListener.friendRequest.get(pValid).equalsIgnoreCase(p.getName()))
            {
                playerValidation = pValid;
                break;
            }
        }

        if(playerValidation != null)
        {
            FriendsListener.friendRequest.remove(playerValidation);
            p.sendMessage(ChatColor.RED + "Vous avez refusé l'invitation de "+playerValidation);
            try {
                Bukkit.getPlayer(playerValidation).sendMessage(ChatColor.RED + p.getName() + " vient de décliner votre invitation");
            }
            catch(Exception e)
            {
                new CustomMessageSender("ALL", "FriendRequestDeny", new String[]{p.getName(), playerValidation});
            }
        }
    }
}
