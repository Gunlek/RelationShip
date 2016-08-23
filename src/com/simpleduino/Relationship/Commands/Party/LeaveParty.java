package com.simpleduino.Relationship.Commands.Party;

import com.simpleduino.Relationship.Listeners.PartyListener;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 16/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class LeaveParty {

    public LeaveParty(Player p, String[] args)
    {
        boolean isMember = false;
        String leader=null;
        for(String l : PartyListener.partyList.keySet())
        {
            if(PartyListener.partyList.get(l).contains(p.getName()))
            {
                isMember = true;
                leader = l;
                break;
            }
        }

        if(isMember)
        {
            ArrayList memberList = PartyListener.partyList.get(leader);
            memberList.remove(p.getName());
            PartyListener.partyList.remove(leader);
            PartyListener.partyList.put(leader, memberList);

            p.sendMessage(ChatColor.YELLOW+"Vous avez quitté la partie de "+ChatColor.RED+leader);
            for(String member : PartyListener.partyList.get(leader))
            {
                if(!member.equalsIgnoreCase(p.getName()))
                {
                    try
                    {
                        Player pMember = Bukkit.getPlayer(member);
                        pMember.sendMessage(ChatColor.RED+p.getName()+ChatColor.YELLOW+" a quitté votre partie");
                    }
                    catch(Exception e)
                    {
                        //Le membre n'est pas sur ce serveur
                    }
                }
            }

            new CustomMessageSender("ALL", "PlayerLeaveParty", new String[]{leader, p.getName()});
        }
    }

}
