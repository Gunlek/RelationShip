package com.simpleduino.Relationship.Commands.Party;

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

public class KickPlayerFromParty {

    private Player toKick;

    public KickPlayerFromParty(Player p, String[] args)
    {
        this.toKick = p;
        String leader = p.getName();
        if(PartyListener.partyList.keySet().contains(p.getName())) {
            ArrayList partyMembers = PartyListener.partyList.get(p.getName());
            String kickedPlayer = args[1];

            if (partyMembers.contains(kickedPlayer)) {
                partyMembers.remove(kickedPlayer);
                PartyListener.partyList.remove(leader);
                PartyListener.partyList.put(leader, partyMembers);
                p.sendMessage(ChatColor.YELLOW + "Vous avez éjecté " + ChatColor.RED + kickedPlayer + ChatColor.YELLOW + " de votre partie");

                try {
                    Player pKicked = Bukkit.getPlayer(kickedPlayer);
                    pKicked.sendMessage(ChatColor.YELLOW+"Vous avez été éjecté de la partie par "+ChatColor.RED+leader);
                } catch (Exception e) {
                    //Le membre n'est pas sur ce serveur
                }

                for (String member : PartyListener.partyList.get(leader)) {
                    if (!member.equalsIgnoreCase(kickedPlayer) && !member.equalsIgnoreCase(leader)) {
                        try {
                            Player pMember = Bukkit.getPlayer(member);
                            pMember.sendMessage(ChatColor.RED + kickedPlayer + ChatColor.YELLOW + " a été éjecté de votre partie");
                        } catch (Exception e) {
                            //Le membre n'est pas sur ce serveur
                        }
                    }
                }

                new CustomMessageSender("ALL", "KickPlayerFromParty", new String[]{leader, kickedPlayer});
            }
            else
            {
                p.sendMessage(ChatColor.RED+"Vous n'êtes pas le leader de cette partie");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED+"Vous n'êtes pas leader d'une partie");
        }
    }

}
