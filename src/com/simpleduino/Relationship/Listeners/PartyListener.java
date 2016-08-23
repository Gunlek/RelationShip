package com.simpleduino.Relationship.Listeners;

import com.simpleduino.Relationship.Events.PlayerJoinPartyEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Simple-Duino on 13/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class PartyListener implements Listener {

    public static HashMap<String, ArrayList<String>> partyList = new HashMap<>();
    public static HashMap<String, String> partyRequest = new HashMap<>();
}
