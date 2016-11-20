package com.simpleduino.Relationship;

import com.simpleduino.Relationship.Commands.FriendCommands;
import com.simpleduino.Relationship.Commands.PartyCommands;
import com.simpleduino.Relationship.Events.OnlinePlayerUpdateEvent;
import com.simpleduino.Relationship.Listeners.FriendsListener;
import com.simpleduino.Relationship.Listeners.PlayerListener;
import com.simpleduino.Relationship.Listeners.ServerListener;
import com.simpleduino.Relationship.Messaging.MessageListener;
import com.simpleduino.Relationship.Messaging.MessageSender;
import com.simpleduino.Relationship.Runnables.FriendsChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */
public class RelationshipPlugin extends JavaPlugin {

    public static String serverName;

    public void onEnable()
    {

        File cfgFile = new File("plugins/Relationship/config.yml");
        if(!cfgFile.exists()) {
            cfgFile.getParentFile().mkdirs();
            try {
                cfgFile.createNewFile();
                YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
                cfg.set("sql.hostname", "localhost");
                cfg.set("sql.database", "relationship");
                cfg.set("sql.username", "user");
                cfg.set("sql.password", "password");
                try {
                    cfg.save(cfgFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new ServerListener(), this);
        this.getServer().getPluginManager().registerEvents(new FriendsListener(), this);

        this.getCommand("friends").setExecutor(new FriendCommands());
        this.getCommand("f").setExecutor(new FriendCommands());
        this.getCommand("friend").setExecutor(new FriendCommands());
        this.getCommand("ami").setExecutor(new FriendCommands());
        this.getCommand("amis").setExecutor(new FriendCommands());

        this.getCommand("party").setExecutor(new PartyCommands());
        this.getCommand("p").setExecutor(new PartyCommands());
        this.getCommand("partie").setExecutor(new PartyCommands());

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new MessageListener());

        if(Bukkit.getOnlinePlayers().size() > 0) {
            new MessageSender("GetServer", null);
        }

        new FriendsChecker().runTaskTimerAsynchronously(this, 0, 20L*15);
    }

    public void onDisable()
    {

    }

}
