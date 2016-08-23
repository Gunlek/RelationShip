package com.simpleduino.Relationship.Commands.Friends;

import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 11/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class MsgFriend {

    public MsgFriend(Player p, String[] args)
    {
        if(args.length >= 2)
        {
            String dest = args[1];
            String msg = args[2];

            try
            {
                Player destPlayer = Bukkit.getPlayer(dest);
                destPlayer.sendMessage(ChatColor.BLUE+"["+p.getName()+ChatColor.BOLD+" ➤ "+ChatColor.RESET.toString()+ChatColor.BLUE+"Vous]"+ChatColor.RESET+": "+msg);
                p.sendMessage(ChatColor.BLUE+"[Vous"+ChatColor.BOLD+" ➤ "+ChatColor.RESET.toString()+ChatColor.BLUE+dest+"]"+ChatColor.RESET+": "+msg);
            }
            catch (Exception e)
            {
                p.sendMessage(ChatColor.BLUE+"[Vous"+ChatColor.BOLD+" ➤ "+ChatColor.RESET.toString()+ChatColor.BLUE+dest+"]"+ChatColor.RESET+": "+msg);
                new CustomMessageSender("ALL", "FriendMessage", new String[]{p.getName(), dest, msg});
            }
        }
    }
}
