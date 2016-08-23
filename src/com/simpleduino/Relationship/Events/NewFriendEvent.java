package com.simpleduino.Relationship.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 12/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class NewFriendEvent extends Event{

    private static final HandlerList handlers = new HandlerList();
    private Player registerPlayer;
    private String newFriend;

    public NewFriendEvent(Player i, String nf)
    {
        this.registerPlayer = i;
        this.newFriend = nf;
    }

    public Player getRegisterPlayer()
    {
        return this.registerPlayer;
    }

    public String getNewFriend()
    {
        return this.newFriend;
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
