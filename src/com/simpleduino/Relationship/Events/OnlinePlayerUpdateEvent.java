package com.simpleduino.Relationship.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */
public class OnlinePlayerUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private ArrayList<String> newPlayerList;

    public OnlinePlayerUpdateEvent(ArrayList<String> npl)
    {
        this.newPlayerList = npl;
    }

    public ArrayList<String> getNewPlayerList()
    {
        return this.newPlayerList;
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
