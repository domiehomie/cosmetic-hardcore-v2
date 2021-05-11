package live.mufin.cosmetichardcore.data;

import live.mufin.cosmetichardcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLDataManager {
    private Main plugin;

    public SQLDataManager(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates table for when the server starts.
     */
    public void createTable() {
        PreparedStatement ps;
        try {
            ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS lives (UUID VARCHAR(100),LIVES INT(100),PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a player to the database
     *
     * @param player
     */
    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(uuid)) {
                PreparedStatement ps = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO lives (UUID) VALUES (?)");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a uuid is in the database
     *
     * @param uuid
     * @return true -> player is in the database || false -> player is not in the database
     */
    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM lives WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setLives(UUID uuid, int lives) {
        try{
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE profiledata SET LIVES=? WHERE UUID=?");
            ps.setInt(1, lives);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getLives(UUID uuid) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT LIVES FROM lives WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            int lives = 0;
            if (results.next()) {
                lives = results.getInt("LIVES");
                return lives;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
