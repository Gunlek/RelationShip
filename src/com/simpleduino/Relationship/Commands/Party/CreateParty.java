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

public class CreateParty {

    private Player leader;

    public CreateParty(Player p, String[] args)
    {
        this.leader = p;
        boolean alreadyInParty = false;
        for(String l : PartyListener.partyList.keySet())
        {
            if(PartyListener.partyList.get(l).contains(this.leader.getName()))
            {
                alreadyInParty = true;
                break;
            }
        }
        if(!alreadyInParty) {
            if (!PartyListener.partyList.containsKey(p.getName())) {
                ArrayList<String> memberList = new ArrayList<>();
                memberList.add(p.getName());
                PartyListener.partyList.put(p.getName(), memberList);
                new CustomMessageSender("ALL", "PlayerCreateParty", new String[]{p.getName()});
                p.sendMessage(ChatColor.YELLOW + "Vous avez créé une partie, invitez vos amis !");
            } else {
                p.sendMessage(ChatColor.RED + "Vous êtes déjà le leader d'une partie");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "Vous êtes déjà membre d'une partie");
        }
    }
}
