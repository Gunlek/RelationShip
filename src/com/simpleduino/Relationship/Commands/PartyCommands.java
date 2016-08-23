package com.simpleduino.Relationship.Commands;

import com.simpleduino.Relationship.Commands.Friends.*;
import com.simpleduino.Relationship.Commands.Party.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */
public class PartyCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            if (args.length > 0) {
                switch(args[0])
                {
                    case "create":
                        new CreateParty((Player)sender, args);
                        break;

                    case "disband":
                        new DisbandParty((Player)sender, args);
                        break;

                    case "invite":
                        new InvitePlayerToParty((Player)sender, args);
                        break;

                    case "kick":
                        new KickPlayerFromParty((Player)sender, args);
                        break;

                    case "accept":
                        new AcceptParty((Player)sender, args);
                        break;

                    case "deny":
                        new DenyParty((Player)sender, args);
                        break;

                    case "leave":
                        new LeaveParty((Player)sender, args);
                        break;
                }
            }
            else
            {
                new ListParty((Player)sender, args);
            }
        }
        return false;
    }
}
