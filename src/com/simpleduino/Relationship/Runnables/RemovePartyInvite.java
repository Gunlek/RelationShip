package com.simpleduino.Relationship.Runnables;

import com.simpleduino.Relationship.Listeners.PartyListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Simple-Duino on 17/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class RemovePartyInvite extends BukkitRunnable{

    private Player pToRemove;

    public RemovePartyInvite(Player p)
    {
        this.pToRemove = p;
    }

    @Override
    public void run() {
        if(PartyListener.partyRequest.containsKey(this.pToRemove.getName())) {
            PartyListener.partyRequest.remove(this.pToRemove.getName());
            pToRemove.sendMessage(ChatColor.YELLOW + "L'invitation de partie a expiré");
        }
    }
}
