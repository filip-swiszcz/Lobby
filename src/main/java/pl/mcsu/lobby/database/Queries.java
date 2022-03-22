package pl.mcsu.lobby.database;

import org.bukkit.entity.Player;
import pl.mcsu.lobby.objects.Password;

import javax.sql.rowset.CachedRowSet;
import java.net.InetAddress;

public class Queries extends MySQL {

    private static Queries instance = new Queries();

    public Queries() {
        instance = this;
    }

    public static Queries getInstance() {
        return instance;
    }

    /*
    *
    *       Start of class
    *
    * */

    public boolean isRegistered(Player player) {
        String sql = "select id from players where uuid=?";
        Object[] objects = new Object[1];
        objects[0] = player.getUniqueId().toString();
        try {
            CachedRowSet cachedRowSet = select(sql, objects);
            if (cachedRowSet.first()) return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean isIpRegistered(InetAddress inetAddress) {
        String sql = "select id from players where ip=?";
        Object[] objects = new Object[1];
        objects[0] = inetAddress.toString();
        try {
            CachedRowSet cachedRowSet = select(sql, objects);
            while (cachedRowSet.next()) {
                if (cachedRowSet.getInt(1) >= 1) {
                    return true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public String getRank(Player player) {
        String sql = "select permissions from players where uuid=?";
        Object[] objects = new Object[1];
        objects[0] = player.getUniqueId().toString();
        try {
            CachedRowSet cachedRowSet = select(sql, objects);
            cachedRowSet.next();
            return cachedRowSet.getString(1);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Object[] getPasswordAndSalt(Player player) {
        String sql = "select password, salt from players where uuid=?";
        Object[] objects = new Object[1];
        objects[0] = player.getUniqueId().toString();
        Object[] data = new Object[2];
        try {
            CachedRowSet cachedRowSet = select(sql, objects);
            while (cachedRowSet.next()) {
                data[0] = cachedRowSet.getString(1);
                data[1] = cachedRowSet.getBytes(2);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return data;
    }

    public void setRegistered(Player player, String password, InetAddress inetAddress) {
        Password passwordHashed = new Password(password);
        String sql = "insert into players(uuid, name, password, salt, ip) values (?, ?, ?, ?, ?)";
        Object[] objects = new Object[5];
        objects[0] = player.getUniqueId().toString();
        objects[1] = player.getName();
        objects[2] = passwordHashed.getPassword();
        objects[3] = passwordHashed.getSalt();
        objects[4] = inetAddress.toString();
        try {
            execute(sql, objects);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setPassword(Player player, String password) {
        Password passwordHashed = new Password(password);
        String sql = "update players set password=?, salt=? where uuid=?";
        Object[] objects = new Object[3];
        objects[0] = passwordHashed.getPassword();
        objects[1] = passwordHashed.getSalt();
        objects[2] = player.getUniqueId().toString();
        try {
            execute(sql, objects);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setRank(Player player, String rank) {
        String sql = "update players set permissions=? where uuid=?";
        Object[] objects = new Object[2];
        objects[0] = rank;
        objects[1] = player.getUniqueId().toString();
        try {
            execute(sql, objects);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
