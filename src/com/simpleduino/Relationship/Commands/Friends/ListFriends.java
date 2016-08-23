package com.simpleduino.Relationship.Commands.Friends;

import com.simpleduino.Relationship.Listeners.PlayerListener;
import com.simpleduino.Relationship.SQL.RelationshipSQL;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Simple-Duino on 11/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class ListFriends {

    private RelationshipSQL relationshipSQL = new RelationshipSQL();

    public ListFriends(Player p)
    {
        ArrayList<String> friendList = new ArrayList<>();
        for(String friend : relationshipSQL.getFriendList(p.getUniqueId().toString()))
        {
            if(PlayerListener.onlinePlayers.contains(friend))
                friendList.add(friend);
        }

        if(PlayerListener.onlineFriends.containsKey(p))
            PlayerListener.onlineFriends.remove(p);
        PlayerListener.onlineFriends.put(p, friendList);

        if(relationshipSQL.hasFriends(p.getUniqueId().toString())) {
            p.sendMessage(ChatColor.YELLOW + "Voici votre liste d'amis:");

            for (String friend : relationshipSQL.getFriendList(p.getUniqueId().toString())) {
                boolean online = false;
                if(PlayerListener.onlinePlayers.contains(friend)) {
                    new FancyMessage("✈")
                            .color(ChatColor.YELLOW)
                            .suggest("/f msg " + friend + " ")
                            .tooltip(ChatColor.YELLOW + "Envoyer un message")
                            .style(ChatColor.BOLD)
                            .then("➲")
                            .style(ChatColor.BOLD)
                            .color(ChatColor.BLUE)
                            .style(ChatColor.BOLD)
                            .command("/f join " + friend)
                            .tooltip(ChatColor.BLUE + "Rejoindre " + friend)
                            .then("✖")
                            .style(ChatColor.BOLD)
                            .color(ChatColor.RED)
                            .command("/f remove " + friend)
                            .tooltip(ChatColor.RED + "Retirer " + friend + " de ma liste d'amis")
                            .then(" " + friend)
                            .color(ChatColor.GREEN)
                            .send(p);
                }
                else
                {
                    new FancyMessage("✈")
                            .color(ChatColor.YELLOW)
                            .suggest("/f msg " + friend + " ")
                            .tooltip(ChatColor.YELLOW + "Envoyer un message")
                            .style(ChatColor.BOLD)
                            .then("➲")
                            .style(ChatColor.BOLD)
                            .color(ChatColor.BLUE)
                            .style(ChatColor.BOLD)
                            .command("/f join " + friend)
                            .tooltip(ChatColor.BLUE + "Rejoindre " + friend)
                            .then("✖")
                            .style(ChatColor.BOLD)
                            .color(ChatColor.RED)
                            .command("/f remove " + friend)
                            .tooltip(ChatColor.RED + "Retirer " + friend + " de ma liste d'amis")
                            .then(" " + friend)
                            .color(ChatColor.GRAY)
                            .send(p);
                }
            }
            new FancyMessage("[Ajouter]")
                    .color(ChatColor.BLUE)
                    .style(ChatColor.BOLD)
                    .tooltip("Ajouter un ami")
                    .suggest("/f add ")
                    .send(p);
        }
        else
        {
            p.sendMessage(ChatColor.RED + "Vous n'avez pas encore d'amis dans votre liste");
        }
    }
}
