package com.simpleduino.Relationship.Commands;

import com.simpleduino.Relationship.Commands.Friends.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */
public class FriendCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            if (args.length > 0) {
                switch(args[0])
                {
                    case "add":
                        new AddFriend((Player)sender, args);
                        break;

                    case "remove":
                        new RemoveFriend((Player)sender, args);
                        break;

                    case "msg":
                        new MsgFriend((Player)sender, args);
                        break;

                    case "accept":
                        new AcceptFriend((Player)sender );
                        break;

                    case "deny":
                        new DenyFriend((Player)sender);
                        break;

                    case "list":
                        new ListFriends((Player)sender);
                        break;

                    case "join":
                        new JoinFriend((Player)sender, args);
                        break;
                }
            }
            else
            {
                new ListFriends((Player)sender);
            }
        }
        return false;
    }
}
