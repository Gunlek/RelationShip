package com.simpleduino.Relationship.Commands.Party;

import com.simpleduino.Relationship.Listeners.PartyListener;
import com.simpleduino.Relationship.Listeners.PlayerListener;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import com.simpleduino.Relationship.RelationshipPlugin;
import com.simpleduino.Relationship.Runnables.RemoveFriendInvite;
import com.simpleduino.Relationship.Runnables.RemovePartyInvite;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class InvitePlayerToParty {

    private Player sender;

    public InvitePlayerToParty(Player p, String[] args)
    {
        this.sender = p;
        if(args.length >= 2) {
            String newMemberName = args[1];
            boolean alreadyInParty = false;
            for(String l : PartyListener.partyList.keySet())
            {
                if(PartyListener.partyList.get(l).contains(newMemberName))
                {
                    alreadyInParty = true;
                    break;
                }
            }
            if (PartyListener.partyList.containsKey(p.getName())) {
                if(!alreadyInParty) {
                    if (!PartyListener.partyList.get(p.getName()).contains(newMemberName)) {
                        if(!PartyListener.partyRequest.containsKey(p.getName())) {
                            try {
                                Player newMember = Bukkit.getPlayer(newMemberName);
                                p.sendMessage(ChatColor.YELLOW + "Vous avez invité " + ChatColor.RED + newMember.getName() + ChatColor.YELLOW + " a rejoindre votre partie");
                                new FancyMessage(p.getName())
                                        .color(ChatColor.RED)
                                        .then(" souhaite vous inviter dans sa partie")
                                        .color(ChatColor.YELLOW)
                                        .send(newMember);
                                new FancyMessage("[Accepter]")
                                        .color(ChatColor.GREEN)
                                        .style(ChatColor.BOLD)
                                        .command("/p accept")
                                        .tooltip(ChatColor.GREEN + "Accepter l'invitation")
                                        .then(" [Refuser]")
                                        .color(ChatColor.DARK_RED)
                                        .style(ChatColor.BOLD)
                                        .command("/p deny")
                                        .tooltip(ChatColor.RED + "Refuser l'invitation")
                                        .send(newMember);
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.YELLOW + "Vous avez invité " + ChatColor.RED + newMemberName + ChatColor.YELLOW + " a rejoindre votre partie");
                                new CustomMessageSender("ALL", "InvitePlayerToParty", new String[]{p.getName(), newMemberName});
                            }

                            PartyListener.partyRequest.put(p.getName(), newMemberName);
                            Bukkit.getScheduler().runTaskLater(RelationshipPlugin.getPlugin(RelationshipPlugin.class), new RemovePartyInvite(p), 20L*15);
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "Vous avez déjà une invitation en cours");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Le joueur que vous invitez est deja membre de votre partie");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Le joueur que vous invitez est deja membre d'une partie");
                }
            }
            else
            {
                p.sendMessage(ChatColor.RED + "Vous n'etes leader d'aucune partie");
            }
        }
    }

}
