package com.simpleduino.Relationship.Commands.Friends;

import com.simpleduino.Relationship.Events.NewFriendEvent;
import com.simpleduino.Relationship.Listeners.FriendsListener;
import com.simpleduino.Relationship.Messaging.CustomMessageSender;
import com.simpleduino.Relationship.RelationshipPlugin;
import com.simpleduino.Relationship.SQL.RelationshipSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

/**
 * Created by Simple-Duino on 11/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class AcceptFriend {

    private RelationshipSQL relationshipSQL = new RelationshipSQL();

    public AcceptFriend(Player p)
    {
        boolean hasRequest = false;
        for(String key : FriendsListener.friendRequest.keySet())
        {
            if(FriendsListener.friendRequest.get(key).equalsIgnoreCase(p.getName()))
            {
                hasRequest = true;
                break;
            }
        }
        if(hasRequest) {
            int amountOfFriends = -1;
            for(PermissionAttachmentInfo perm: p.getEffectivePermissions()){
                String permString = perm.getPermission().toLowerCase();
                if(permString.toLowerCase().startsWith("relationship.friends.")){
                    String[] amount = permString.split("\\.");
                    amountOfFriends = Integer.parseInt(amount[2]);
                }
            }
            if(amountOfFriends==-1 || amountOfFriends < relationshipSQL.getFriendsCount(p.getUniqueId().toString())) {
                String playerValidation = null;
                for (String pValid : FriendsListener.friendRequest.keySet()) {
                    if (FriendsListener.friendRequest.get(pValid).equalsIgnoreCase(p.getName())) {
                        playerValidation = pValid;
                        break;
                    }
                }
                if (playerValidation != null) {
                    Player pCheck = null;
                    try {
                        pCheck = Bukkit.getPlayer(playerValidation);
                    } catch (Exception e) {

                    }
                    if (pCheck != null)
                        Bukkit.getPluginManager().callEvent(new NewFriendEvent(pCheck, p.getName()));
                    new CustomMessageSender("ALL", "FriendRequestAccept", new String[]{playerValidation, p.getName()});
                    Bukkit.getPluginManager().callEvent(new NewFriendEvent(p, playerValidation));
                    FriendsListener.friendRequest.remove(playerValidation);
                }
            }
            else
            {
                p.sendMessage(ChatColor.RED + "Vous avez déjà atteint le nombre maximum d'amis pour votre grade");
                new DenyFriend(p);
            }
        }

        else
        {
            p.sendMessage(ChatColor.RED + "Vous n'avez pas d'invitation en cours");
        }
    }
}
