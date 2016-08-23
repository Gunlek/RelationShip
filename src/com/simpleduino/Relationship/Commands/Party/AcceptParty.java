package com.simpleduino.Relationship.Commands.Party;

import com.simpleduino.Relationship.Events.PlayerJoinPartyEvent;
import com.simpleduino.Relationship.Listeners.PartyListener;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class AcceptParty {

    private Player sender;

    public AcceptParty(Player p, String[] args)
    {
        this.sender = p;
        String leader = null;
        for(String l : PartyListener.partyRequest.keySet())
        {
            if(PartyListener.partyRequest.get(l).equalsIgnoreCase(this.sender.getName()))
            {
                leader = l;
                break;
            }
        }

        if(leader!=null) {
            ArrayList<String> newPartyMemberList = PartyListener.partyList.get(leader);
            newPartyMemberList.add(this.sender.getName());
            PartyListener.partyList.remove(leader);
            PartyListener.partyList.put(leader, newPartyMemberList);
            new CustomMessageSender("ALL", "PartyRequestAccept", new String[]{leader, this.sender.getName()});
            PartyListener.partyRequest.remove(leader);

            p.sendMessage(ChatColor.YELLOW + "Vous avez rejoint la partie de "+ChatColor.RED+leader);
            for(String member : PartyListener.partyList.get(leader))
            {
                if(!member.equalsIgnoreCase(p.getName()))
                {
                    try
                    {
                        Player pMsg = Bukkit.getPlayer(member);
                        pMsg.sendMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + " a rejoint la partie");
                    }
                    catch(Exception e)
                    {
                        //Le membre n'est pas sur ce serveur
                    }
                }
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "Vous n'avez aucune invitation de partie en cours");
        }
    }
}
