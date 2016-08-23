package com.simpleduino.Relationship.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class PlayerJoinPartyEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String partyLeader;
    private String newMember;

    public PlayerJoinPartyEvent(String l, String n)
    {
        this.partyLeader = l;
        this.newMember = n;
    }

    public String getPartyLeader()
    {
        return this.partyLeader;
    }

    public String getNewMember()
    {
        return this.newMember;
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
