package com.simpleduino.Relationship.Messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.simpleduino.Relationship.Commands.Friends.AddFriend;
import com.simpleduino.Relationship.Events.*;
import com.simpleduino.Relationship.Listeners.FriendsListener;
import com.simpleduino.Relationship.Listeners.PartyListener;
import com.simpleduino.Relationship.Listeners.PlayerListener;
import com.simpleduino.Relationship.RelationshipPlugin;
import com.simpleduino.Relationship.SQL.RelationshipSQL;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class MessageListener implements PluginMessageListener {

    private RelationshipSQL relationshipSQL = new RelationshipSQL();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("PlayerList")) {
            String server = in.readUTF();
            String[] playerList = in.readUTF().split(", ");
            if(server.equalsIgnoreCase("all")) {
                ArrayList<String> onlinePlayers = new ArrayList<>();
                for (String p : playerList) {
                    onlinePlayers.add(p);
                }
                PlayerListener.onlinePlayers = onlinePlayers;
                Bukkit.getPluginManager().callEvent(new OnlinePlayerUpdateEvent(onlinePlayers));
            }
        }

        else if (subchannel.equals("FriendRequest")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String inviter = null, invited = null;
            try {
                inviter = msgin.readUTF();
                invited = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FriendsListener.friendRequest.put(inviter, invited);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equalsIgnoreCase(invited)) {
                    new FancyMessage(inviter)
                            .color(ChatColor.RED)
                            .then(" souhaite devenir votre ami")
                            .color(ChatColor.YELLOW)
                            .send(p);

                    new FancyMessage("[Accepter]")
                            .color(ChatColor.GREEN)
                            .style(ChatColor.BOLD)
                            .command("/f accept")
                            .tooltip("Accepter la demande")
                            .then(" [Refuser]")
                            .color(ChatColor.DARK_RED)
                            .style(ChatColor.BOLD)
                            .command("/f deny")
                            .tooltip("Refuser la demande")
                            .send(p);

                    break;
                }
            }
        }

        else if(subchannel.equals("FriendRequestAccept"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String inviter = null, invited = null;
            try {
                inviter = msgin.readUTF();
                invited = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try
            {
                Player inviterPlayer = Bukkit.getPlayer(inviter);
                FriendsListener.friendRequest.remove(inviter);
                Bukkit.getPluginManager().callEvent(new NewFriendEvent(inviterPlayer, invited));
            }
            catch(Exception e)
            {
                //Le joueur qui ajoute l'ami n'est pas sur ce serveur
            }
        }

        else if(subchannel.equals("FriendRequestDeny"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String decliner = null, inviter = null;
            try {
                decliner = msgin.readUTF();
                inviter = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Bukkit.getPlayer(inviter).sendMessage(ChatColor.RED + decliner + " vient de décliner votre invitation");
            }
            catch(Exception e)
            {
                //Le joueur n'est pas sur le serveur
            }

            FriendsListener.friendRequest.remove(inviter);
        }

        else if(subchannel.equals("FriendMessage"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String sender = null, dest = null, msg = null;
            try
            {
                sender = msgin.readUTF();
                dest = msgin.readUTF();
                msg = msgin.readUTF();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                Player destPlayer = Bukkit.getPlayer(dest);
                Bukkit.getPluginManager().callEvent(new NewFriendMessageEvent(sender, destPlayer, msg));
            }
            catch (Exception e)
            {
                //Le joueur destinataire n'est pas sur le serveur
            }
        }

        else if(subchannel.equals("PlayerRemoveFriend"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String remover = null, removed = null;
            try
            {
                remover = msgin.readUTF();
                removed = msgin.readUTF();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            Bukkit.getPluginManager().callEvent(new FriendRemoveEvent(removed, remover));
        }

        else if(subchannel.equals("GetPlayerServer"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String playerName = null;
            try
            {
                playerName = msgin.readUTF();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try {
                Player wantedPlayer = Bukkit.getPlayer(playerName);
                new CustomMessageSender("ALL", "ReturnPlayerServer", new String[]{playerName, RelationshipPlugin.serverName});
            }
            catch (Exception e)
            {
                //Le joueur recherché n'est pas sur ce serveur
            }
        }

        else if(subchannel.equals("ReturnPlayerServer"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String playerName = null, serverName = null;
            try
            {
                playerName = msgin.readUTF();
                serverName = msgin.readUTF();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            Bukkit.getPluginManager().callEvent(new ReturnPlayerServerEvent(serverName, playerName));
        }

        else if(subchannel.equalsIgnoreCase("playerjoinproxy"))
        {
            String playerName = in.readUTF();
            String playerUUID = in.readUTF();
            for(Player pCheck : Bukkit.getOnlinePlayers())
            {
                if(relationshipSQL.getFriendList(pCheck.getUniqueId().toString()).contains(playerName))
                    pCheck.sendMessage(ChatColor.BLUE+"["+ChatColor.AQUA+"Ami"+ChatColor.BLUE+"] "+ ChatColor.DARK_GRAY .toString() + ChatColor.ITALIC + playerName + ChatColor.RESET.toString() + ChatColor.DARK_GRAY + " a rejoint le serveur");
            }
            if(!PlayerListener.onlinePlayers.contains(playerName))
            {
                PlayerListener.onlinePlayers.add(playerName);
            }


        }

        else if(subchannel.equalsIgnoreCase("playerleftproxy"))
        {
            String playerName = in.readUTF();
            String playerUUID = in.readUTF();
            for(Player pCheck : Bukkit.getOnlinePlayers())
            {
                if(relationshipSQL.getFriendList(pCheck.getUniqueId().toString()).contains(playerName))
                    pCheck.sendMessage(ChatColor.BLUE+"["+ChatColor.AQUA+"Ami"+ChatColor.BLUE+"] "+ ChatColor.DARK_GRAY .toString() + ChatColor.ITALIC + playerName + ChatColor.RESET.toString() + ChatColor.DARK_GRAY + " vient de quitter le serveur");
            }
            if(PlayerListener.onlinePlayers.contains(playerName))
            {
                PlayerListener.onlinePlayers.remove(playerName);
            }
        }

        else if(subchannel.equals("PlayerCreateParty"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String leader = null;
            try
            {
                leader = msgin.readUTF();
                if(!PartyListener.partyList.containsKey(leader))
                {
                    ArrayList<String> memberList = new ArrayList<>();
                    memberList.add(leader);
                    PartyListener.partyList.put(leader, memberList);
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(subchannel.equals("PlayerDisbandParty"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String leader = null;
            try
            {
                leader = msgin.readUTF();
                if(PartyListener.partyList.containsKey(leader))
                {
                    for(String member : PartyListener.partyList.get(leader))
                    {
                        try
                        {
                            Player m = Bukkit.getPlayer(member);
                            m.sendMessage(ChatColor.RED + leader + ChatColor.YELLOW + " a dissout la partie dans laquelle vous étiez");
                        }
                        catch (Exception e)
                        {
                            //Le membre testé n'est pas sur le serveur
                        }
                    }
                    PartyListener.partyList.remove(leader);
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(subchannel.equals(("InvitePlayerToParty")))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String leader = null, newMember = null;
            try
            {
                leader = msgin.readUTF();
                newMember = msgin.readUTF();
                try
                {
                    Player m = Bukkit.getPlayer(newMember);
                    new FancyMessage(leader)
                            .color(ChatColor.RED)
                            .then(" souhaite vous inviter dans sa partie")
                            .color(ChatColor.YELLOW)
                            .send(m);
                    new FancyMessage("[Accepter]")
                            .color(ChatColor.GREEN)
                            .style(ChatColor.BOLD)
                            .command("/p accept")
                            .tooltip(ChatColor.GREEN + "Accepter l'invitation")
                            .then(" [Refuser]")
                            .color(ChatColor.DARK_RED)
                            .style(ChatColor.BOLD)
                            .command("/p deny")
                            .tooltip(ChatColor.RED + "Refuser l'invitation")
                            .send(m);
                    PartyListener.partyRequest.put(leader, m.getName());
                }
                catch (Exception e)
                {
                    //Le membre testé n'est pas sur le serveur
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(subchannel.equals("PartyRequestAccept"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String leader = null, newMember = null;
            try
            {
                leader = msgin.readUTF();
                newMember = msgin.readUTF();
                ArrayList<String> newPartyMemberList = PartyListener.partyList.get(leader);
                newPartyMemberList.add(newMember);
                PartyListener.partyList.remove(leader);
                PartyListener.partyList.put(leader, newPartyMemberList);
                PartyListener.partyRequest.remove(leader);

                try
                {
                    Player pLeader = Bukkit.getPlayer(leader);
                    pLeader.sendMessage(ChatColor.RED + newMember + ChatColor.YELLOW + " a rejoint votre partie");
                }
                catch(Exception e)
                {
                    //Le leader n'est pas sur ce serveur
                }

                for(String member : PartyListener.partyList.get(leader))
                {
                    if(!member.equalsIgnoreCase(leader)) {
                        try {
                            Player pMember = Bukkit.getPlayer(member);
                            pMember.sendMessage(ChatColor.RED + newMember + ChatColor.YELLOW + " a rejoint la partie");
                        } catch (Exception e) {
                            //Le membre testé n'est pas sur ce serveur
                        }
                    }
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(subchannel.equals("PartyRequestDeny"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String leader = null, newMember = null;
            try
            {
                leader = msgin.readUTF();
                newMember = msgin.readUTF();
                PartyListener.partyRequest.remove(leader);

                try
                {
                    Bukkit.getPlayer(leader).sendMessage(ChatColor.RED + newMember + ChatColor.YELLOW + " vient de refuser votre invitation");
                }
                catch(Exception e)
                {
                    //Le leader n'est pas sur ce serveur
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(subchannel.equals("KickPlayerFromParty"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String leader = null, kickedPlayer = null;
            try
            {
                leader = msgin.readUTF();
                kickedPlayer = msgin.readUTF();
                ArrayList partyMembers = PartyListener.partyList.get(leader);
                partyMembers.remove(kickedPlayer);
                PartyListener.partyList.remove(leader);
                PartyListener.partyList.put(leader, partyMembers);

                try
                {
                    Player pKickedPlayer = Bukkit.getPlayer(kickedPlayer);
                    pKickedPlayer.sendMessage(ChatColor.YELLOW+"Vous avez été éjecté de la partie par "+ChatColor.RED+leader);
                }
                catch(Exception e)
                {
                    //Le joueur éjecté n'est pas sur le serveur
                }

                for(String member : PartyListener.partyList.get(leader))
                {
                    if(!member.equalsIgnoreCase(kickedPlayer))
                    {
                        try
                        {
                            Player pMember = Bukkit.getPlayer(member);
                            pMember.sendMessage(ChatColor.RED+kickedPlayer+ChatColor.YELLOW+" a été éjecté de votre partie");
                        }
                        catch(Exception e)
                        {
                            //Le membre n'est pas sur ce serveur
                        }
                    }
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(subchannel.equals("PlayerLeaveParty"))
        {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String leader = null, leftPlayer = null;
            try
            {
                leader = msgin.readUTF();
                leftPlayer = msgin.readUTF();
                ArrayList partyMembers = PartyListener.partyList.get(leader);
                partyMembers.remove(leftPlayer);
                PartyListener.partyList.remove(leader);
                PartyListener.partyList.put(leader, partyMembers);

                try
                {
                    Player pKickedPlayer = Bukkit.getPlayer(leftPlayer);
                    pKickedPlayer.sendMessage(ChatColor.YELLOW+"Vous avez quitté la partie de "+ChatColor.RED+leader);
                }
                catch(Exception e)
                {
                    //Le joueur éjecté n'est pas sur le serveur
                }

                try
                {
                    Player pLeader = Bukkit.getPlayer(leader);
                    pLeader.sendMessage(ChatColor.RED+leftPlayer+ChatColor.YELLOW+" a quitté votre partie");
                }
                catch(Exception e)
                {
                    //Le joueur leader n'est pas sur le serveur
                }

                for(String member : PartyListener.partyList.get(leader))
                {
                    if(!member.equalsIgnoreCase(leftPlayer) && !member.equalsIgnoreCase(leader))
                    {
                        try
                        {
                            Player pMember = Bukkit.getPlayer(member);
                            pMember.sendMessage(ChatColor.RED+leftPlayer+ChatColor.YELLOW+" a quitté votre partie");
                        }
                        catch(Exception e)
                        {
                            //Le membre n'est pas sur ce serveur
                        }
                    }
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(subchannel.equals("GetServer"))
        {
            RelationshipPlugin.serverName = in.readUTF();
        }
    }

}
