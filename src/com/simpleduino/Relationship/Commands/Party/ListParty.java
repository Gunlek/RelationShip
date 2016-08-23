package com.simpleduino.Relationship.Commands.Party;

import com.simpleduino.Relationship.Listeners.PartyListener;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class ListParty {

    private Player sender;

    public ListParty(Player p, String[] args)
    {
        if(PartyListener.partyList.containsKey(p.getName()))
        {
            //Alors, le joueur executant est leader d'une partie
            p.sendMessage(ChatColor.YELLOW+"Vous êtes leader d'une partie");
            p.sendMessage(ChatColor.YELLOW+"Voici la liste des membres de cette partie");
            for(String member : PartyListener.partyList.get(p.getName()))
            {
                if(PartyListener.partyList.containsKey(member))
                {
                    new FancyMessage("- "+member)
                            .color(ChatColor.AQUA)
                            .tooltip(ChatColor.DARK_RED+"Leader")
                            .then(" [L]")
                            .style(ChatColor.BOLD)
                            .color(ChatColor.DARK_RED)
                            .send(p);
                }
                else {
                    new FancyMessage("✖ ")
                            .color(ChatColor.RED)
                            .style(ChatColor.BOLD)
                            .command("/p kick " + member)
                            .tooltip(ChatColor.RED+"Ejecter le joueur de le partie")
                            .then(member)
                            .color(ChatColor.AQUA)
                            .tooltip(ChatColor.GREEN + "Membre")
                            .then(" [M]")
                            .color(ChatColor.DARK_GREEN)
                            .style(ChatColor.BOLD)
                            .send(p);
                }
            }
            new FancyMessage("[Dissoudre]")
                    .color(ChatColor.DARK_RED)
                    .style(ChatColor.BOLD)
                    .command("/p disband")
                    .tooltip("Dissoudre la partie")
                    .then(" [Inviter]")
                    .tooltip("Inviter des joueurs dans la partie")
                    .color(ChatColor.BLUE)
                    .style(ChatColor.BOLD)
                    .suggest("/p invite ")
                    .send(p);
        }
        else
        {
            boolean isMember = false;
            for(String key : PartyListener.partyList.keySet())
            {
                if(PartyListener.partyList.get(key).contains(p.getName()))
                {
                    //Alors le joueur est membre d'une partie
                    isMember = true;
                }
            }
            if(isMember)
            {
                p.sendMessage(ChatColor.YELLOW+"Vous faites partie d'une partie");
                String partyLeader = null;
                for(String key : PartyListener.partyList.keySet())
                {
                    if(PartyListener.partyList.get(key).contains(p.getName()))
                    {
                        partyLeader=key;
                    }
                }
                p.sendMessage(ChatColor.YELLOW+"Le leader de la partie est "+ChatColor.RED+partyLeader);
                p.sendMessage(ChatColor.YELLOW+"Et voici la liste des membres de cette partie");
                for(String member : PartyListener.partyList.get(partyLeader))
                {
                    if(PartyListener.partyList.containsKey(member))
                    {
                        new FancyMessage("- "+member)
                                .color(ChatColor.AQUA)
                                .tooltip(ChatColor.DARK_RED+"Leader")
                                .then(" [L]")
                                .style(ChatColor.BOLD)
                                .color(ChatColor.DARK_RED)
                                .send(p);
                    }
                    else {
                        new FancyMessage("- " + member)
                                .color(ChatColor.AQUA)
                                .tooltip(ChatColor.GREEN + "Membre")
                                .then(" [M]")
                                .color(ChatColor.DARK_GREEN)
                                .style(ChatColor.BOLD)
                                .send(p);
                    }
                }
                new FancyMessage("[Quitter]")
                        .color(ChatColor.DARK_RED)
                        .style(ChatColor.BOLD)
                        .command("/p leave")
                        .tooltip("Quitter la partie")
                        .send(p);
            }
            else
            {
                new FancyMessage("Souhaitez-vous créer une partie ?")
                        .color(ChatColor.YELLOW)
                        .send(p);
                new FancyMessage("[Oui]")
                        .color(ChatColor.GREEN)
                        .style(ChatColor.BOLD)
                        .tooltip(ChatColor.GREEN+"Créer une partie")
                        .command("/p create")
                        .send(p);
            }
        }
    }
}
