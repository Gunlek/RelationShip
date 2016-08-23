package com.simpleduino.Relationship.Runnables;

import com.simpleduino.Relationship.Listeners.FriendsListener;
import com.simpleduino.Relationship.Listeners.PartyListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Simple-Duino on 17/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class RemoveFriendInvite extends BukkitRunnable{

    private Player pToRemove;

    public RemoveFriendInvite(Player p)
    {
        this.pToRemove = p;
    }

    @Override
    public void run() {
        if(FriendsListener.friendRequest.containsKey(this.pToRemove.getName())) {
            FriendsListener.friendRequest.remove(this.pToRemove.getName());
            pToRemove.sendMessage(ChatColor.YELLOW + "L'invitation d'amis a expiré");
        }
    }
}
