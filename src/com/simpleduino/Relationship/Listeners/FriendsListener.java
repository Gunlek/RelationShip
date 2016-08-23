package com.simpleduino.Relationship.Listeners;

import com.simpleduino.Relationship.Commands.Friends.JoinFriend;
import com.simpleduino.Relationship.Events.FriendRemoveEvent;
import com.simpleduino.Relationship.Events.NewFriendEvent;
import com.simpleduino.Relationship.Events.NewFriendMessageEvent;
import com.simpleduino.Relationship.Events.ReturnPlayerServerEvent;
import com.simpleduino.Relationship.Messaging.MessageSender;
import com.simpleduino.Relationship.SQL.RelationshipSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Simple-Duino on 12/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class FriendsListener implements Listener {

    public static HashMap<String, String> friendRequest = new HashMap<>();

    //Lorsqu'un joueur a un nouvel amis
    @EventHandler
    public void onNewFriend(NewFriendEvent e)
    {
        Player p = e.getRegisterPlayer();
        String newFriend = e.getNewFriend();

        int amountOfFriends = -1;
        for(PermissionAttachmentInfo perm: p.getEffectivePermissions()){
            String permString = perm.getPermission();
            if(permString.startsWith("RelationShip.friends.")){
                String[] amount = permString.split(".");
                amountOfFriends = Integer.parseInt(amount[2]);
            }
        }

        if(amountOfFriends < new RelationshipSQL().getFriendsCount(p.getUniqueId().toString()) || amountOfFriends==-1) {
            new RelationshipSQL().addFriend(p.getUniqueId().toString(), newFriend);
            p.sendMessage(ChatColor.YELLOW + "Vous etes maintenant ami avec " + ChatColor.RED + newFriend);
        }
        else
        {
            p.sendMessage(ChatColor.RED + "Vous avez atteint le nombre maximum d'amis que vous pouvez avoir à votre grade");
        }
    }

    //Lorsqu'un joueur retire un ami de sa liste
    @EventHandler
    public void onFriendRemoving(FriendRemoveEvent e)
    {
        String p = e.getRegisterPlayer();
        String removedFriend = e.getRemovedFriend();

        try
        {
            Player pCheck = Bukkit.getPlayer(p);
            new RelationshipSQL().removeFriend(pCheck.getUniqueId().toString(), removedFriend);
            pCheck.sendMessage(ChatColor.YELLOW+"Vous avez retire "+ChatColor.RED+removedFriend+ChatColor.RESET.toString()+ChatColor.YELLOW+" de votre liste d'amis");
        }
        catch (Exception e1)
        {
            //Le joueur n'est pas sur ce serveur
        }
    }

    //Lorsque le joueur reçoit un message
    @EventHandler
    public void onFriendMessage(NewFriendMessageEvent e)
    {
        String sender = e.getSender();
        Player recipient = e.getRecipient();
        String message = e.getMessage();

        recipient.sendMessage(ChatColor.BLUE+"["+sender+ChatColor.BOLD+" ➤ "+ChatColor.RESET.toString()+ChatColor.BLUE+"Vous]"+ChatColor.RESET+": "+message);
    }

    //Lorsqu'on a le retour du serveur sur lequel se situe un ami vers lequel on veut se téléporter
    @EventHandler
    public void onFriendServerReturn(ReturnPlayerServerEvent e)
    {
        String serverName = e.getServerName();
        String recipient = e.getRecipientName();

        for(Player p : JoinFriend.waitingJoin.keySet())
        {
            if(JoinFriend.waitingJoin.get(p).equalsIgnoreCase(recipient))
                new MessageSender("Connect", new String[]{serverName});
        }
    }
}
