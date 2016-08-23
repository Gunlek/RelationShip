package com.simpleduino.Relationship.SQL;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Simple-Duino on 12/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class RelationshipSQL {

    private File cfgFile = new File("plugins/Relationship/config.yml");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
    private Connection con = null;

    public RelationshipSQL()
    {
        String hostname = cfg.get("sql.hostname").toString();
        String database = cfg.get("sql.database").toString();
        String username = cfg.get("sql.username").toString();
        String password = cfg.get("sql.password").toString();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.con = DriverManager.getConnection("jdbc:mysql://"+hostname+":3306/"+database, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!this.isInit())
        {
            this.initDb();
        }
    }

    private void initDb()
    {
        try {
            Statement statement = this.con.createStatement();
            statement.execute("CREATE TABLE `"+cfg.get("sql.database").toString()+"`.`friends` ( `id` INT NOT NULL AUTO_INCREMENT , `playerUniqueId` VARCHAR(255) NOT NULL , `friendList` TEXT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isInit()
    {
        try {
            Statement statement = this.con.createStatement();
            statement.execute("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE `TABLE_NAME` = \"friends\"");
            ResultSet result = statement.getResultSet();
            if(result.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean areFriends(String uuid, String f2)
    {
        try {
            Statement statement = this.con.createStatement();
            statement.executeQuery("SELECT * FROM friends WHERE playerUniqueId=\""+uuid+"\"");
            ResultSet result = statement.getResultSet();
            while(result.next())
            {
                if(result.getString("friendList").toLowerCase().contains(f2.toLowerCase()))
                {
                    return true;
                }
            }
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addFriend(String uuid, String f2)
    {
        try {
            Statement statement = this.con.createStatement();
            statement.executeQuery("SELECT * FROM friends WHERE playerUniqueId=\""+uuid+"\"");
            ResultSet result = statement.getResultSet();
            if(result.next())
            {
                String friendList = result.getString("friendList")+f2+", ";
                statement.execute("UPDATE friends SET friendList=\""+friendList+"\" WHERE playerUniqueId=\""+uuid+"\"");
            }
            else
            {
                statement.execute("INSERT INTO friends(playerUniqueId, friendList) VALUES(\""+uuid+"\", \""+f2+", \")");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFriend(String uuid, String f2)
    {
        try {
            Statement statement = this.con.createStatement();
            statement.executeQuery("SELECT * FROM friends WHERE playerUniqueId=\""+uuid+"\"");
            ResultSet result = statement.getResultSet();
            if(result.next())
            {
                String friendList = result.getString("friendList").replace(f2+", ", "");
                statement.execute("UPDATE friends SET friendList=\""+friendList+"\" WHERE playerUniqueId=\""+uuid+"\"");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getFriendList(String uuid)
    {
        ArrayList<String> friendList = new ArrayList<>();
        try {
            Statement statement = this.con.createStatement();
            statement.executeQuery("SELECT * FROM friends WHERE playerUniqueId=\""+uuid+"\"");
            ResultSet result = statement.getResultSet();
            if(result.next())
            {
                for(String friend : result.getString("friendList").split(", "))
                {
                    if(friend != "") {
                        friendList.add(friend);
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return friendList;
    }

    public boolean hasFriends(String uuid)
    {
        ArrayList<String> friendList = new ArrayList<>();
        try {
            Statement statement = this.con.createStatement();
            statement.executeQuery("SELECT * FROM friends WHERE playerUniqueId=\""+uuid+"\"");
            ResultSet result = statement.getResultSet();
            if(result.next())
            {
                for(String friend : result.getString("friendList").split(", "))
                {
                    if(friend != "") {
                        friendList.add(friend);
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if(friendList.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getFriendsCount(String uuid)
    {
        return this.getFriendList(uuid).size();
    }
}
