package com.simpleduino.Relationship.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Simple-Duino on 12/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class FriendRemoveEvent extends Event{

    private static final HandlerList handlers = new HandlerList();
    private String registerPlayer;
    private String removedFriend;

    public FriendRemoveEvent(String i, String nf)
    {
        this.registerPlayer = i;
        this.removedFriend = nf;
    }

    public String getRegisterPlayer()
    {
        return this.registerPlayer;
    }

    public String getRemovedFriend()
    {
        return this.removedFriend;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

}
