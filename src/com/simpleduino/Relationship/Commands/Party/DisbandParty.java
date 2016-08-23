package com.simpleduino.Relationship.Commands.Party;

import com.simpleduino.Relationship.Listeners.PartyListener;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class DisbandParty {

    private Player leader;

    public DisbandParty(Player p, String[] args)
    {
        this.leader = p;
        boolean alreadyInParty = false;
        for(String l : PartyListener.partyList.keySet())
        {
            if(PartyListener.partyList.get(l).contains(this.leader.getName()) && !l.equalsIgnoreCase(p.getName()))
            {
                alreadyInParty = true;
                break;
            }
        }
        if(!alreadyInParty) {
            if (PartyListener.partyList.containsKey(p.getName())) {
                PartyListener.partyList.remove(p.getName());
                new CustomMessageSender("ALL", "PlayerDisbandParty", new String[]{p.getName()});
                p.sendMessage(ChatColor.YELLOW + "Votre partie a ete dissoute");
            } else {
                p.sendMessage(ChatColor.RED + "Vous n'êtes pas le leader d'une partie");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "Vous ne pouvez pas dissoudre une partie dont vous n'êtes pas leader");
        }
    }

}
