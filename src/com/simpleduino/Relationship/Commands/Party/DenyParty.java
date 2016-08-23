package com.simpleduino.Relationship.Commands.Party;

import com.simpleduino.Relationship.Listeners.PartyListener;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class DenyParty {

    private Player sender;

    public DenyParty(Player p, String[] args) {
        this.sender = p;
        String leader = null;
        for (String l : PartyListener.partyRequest.keySet()) {
            if (PartyListener.partyRequest.get(l).equalsIgnoreCase(this.sender.getName())) {
                leader = l;
                break;
            }
        }

        if (leader != null) {
            PartyListener.partyRequest.remove(leader);
            p.sendMessage(ChatColor.YELLOW + "Vous venez de décliner l'invitation de "+ChatColor.RED+leader);
            new CustomMessageSender("ALL", "PartyRequestDeny", new String[]{leader, this.sender.getName()});
        }
    }
}
