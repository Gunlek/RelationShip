package com.simpleduino.Relationship.Commands.Friends;

import com.simpleduino.Relationship.Events.FriendRemoveEvent;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import com.simpleduino.Relationship.SQL.RelationshipSQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 11/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class RemoveFriend {

    public RemoveFriend(Player p, String[] args)
    {
        if(args.length >= 2)
        {
            String removed = args[1];

            try
            {
                Player removedPlayer = Bukkit.getPlayer(removed);

                Bukkit.getPluginManager().callEvent(new FriendRemoveEvent(removedPlayer.getName(), p.getName()));
                Bukkit.getPluginManager().callEvent(new FriendRemoveEvent(p.getName(), removedPlayer.getName()));
            }
            catch (Exception e)
            {
                new CustomMessageSender("ALL", "PlayerRemoveFriend", new String[]{p.getName(), removed});
                Bukkit.getPluginManager().callEvent(new FriendRemoveEvent(p.getName(), removed));
            }
        }
    }
}
