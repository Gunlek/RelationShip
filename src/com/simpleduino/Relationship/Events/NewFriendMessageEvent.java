package com.simpleduino.Relationship.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class NewFriendMessageEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String sender;
    private Player recipientPlayer;
    private String message;

    public NewFriendMessageEvent(String s, Player i, String msg)
    {
        this.sender = s;
        this.recipientPlayer = i;
        this.message = msg;
    }

    public String getSender()
    {
        return this.sender;
    }

    public Player getRecipient()
    {
        return this.recipientPlayer;
    }

    public String getMessage()
    {
        return this.message;
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
