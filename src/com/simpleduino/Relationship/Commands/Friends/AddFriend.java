package com.simpleduino.Relationship.Commands.Friends;

import com.simpleduino.Relationship.Listeners.FriendsListener;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import com.simpleduino.Relationship.RelationshipPlugin;
import com.simpleduino.Relationship.Runnables.RemoveFriendInvite;
import com.simpleduino.Relationship.SQL.RelationshipSQL;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.HashMap;

/**
 * Created by Simple-Duino on 11/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class AddFriend {

    public AddFriend(Player p, String[] args)
    {
        if(args.length >= 2) {
            if(!FriendsListener.friendRequest.containsKey(p.getName())) {
                boolean alreadyFriend = false;
                if(new RelationshipSQL().areFriends(p.getUniqueId().toString(), args[1]))
                    alreadyFriend = true;

                int amountOfFriends = -1;
                for(PermissionAttachmentInfo perm: p.getEffectivePermissions()){
                    String permString = perm.getPermission().toLowerCase();
                    if(permString.toLowerCase().startsWith("relationship.friends.")){
                        String[] amount = permString.split("\\.");
                        amountOfFriends = Integer.parseInt(amount[2]);
                    }
                }

                if(amountOfFriends < new RelationshipSQL().getFriendsCount(p.getUniqueId().toString()) || amountOfFriends==-1) {
                    if (!alreadyFriend) {
                        if (!args[1].equalsIgnoreCase(p.getName())) {
                            try {
                                Player newFriend = Bukkit.getPlayer(args[1]);
                                p.sendMessage(ChatColor.YELLOW + "Vous avez invité " + ChatColor.RED + newFriend.getName() + ChatColor.YELLOW + " a devenir votre ami");
                                new FancyMessage(p.getName())
                                        .color(ChatColor.RED)
                                        .then(" souhaite devenir votre ami")
                                        .color(ChatColor.YELLOW)
                                        .send(newFriend);

                                new FancyMessage("[Accepter]")
                                        .color(ChatColor.GREEN)
                                        .style(ChatColor.BOLD)
                                        .command("/f accept")
                                        .tooltip("Accepter la demande")
                                        .then(" [Refuser]")
                                        .color(ChatColor.DARK_RED)
                                        .style(ChatColor.BOLD)
                                        .command("/f deny")
                                        .tooltip("Refuser la demande")
                                        .send(newFriend);
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.YELLOW + "Vous avez invité " + ChatColor.RED + args[1] + ChatColor.YELLOW + " a devenir votre ami");

                                new CustomMessageSender("ALL", "FriendRequest", new String[]{p.getName(), args[1]});
                            }

                            Bukkit.getScheduler().runTaskLater(RelationshipPlugin.getPlugin(RelationshipPlugin.class), new RemoveFriendInvite(p), 20L * 15);
                            FriendsListener.friendRequest.put(p.getName(), args[1]);
                        } else {
                            p.sendMessage(ChatColor.RED + "Vous ne pouvez pas être votre propre ami");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + args[1] + " est deja votre ami");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Vous avez atteint le nombre maximum d'amis que vous pouvez avoir à votre grade");
                }
            }
            else
            {
                p.sendMessage(ChatColor.RED + "Vous avez déjà une requête d'amis en cours...");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "Erreur de syntaxe: /f add <nom_ami>");
        }
    }
}
