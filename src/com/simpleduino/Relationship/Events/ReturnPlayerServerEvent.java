package com.simpleduino.Relationship.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class ReturnPlayerServerEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String serverName;
    private String recipientName;

    public ReturnPlayerServerEvent(String s, String r)
    {
        this.serverName = s;
        this.recipientName = r;
    }

    public String getServerName()
    {
        return this.serverName;
    }

    public String getRecipientName()
    {
        return this.recipientName;
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
