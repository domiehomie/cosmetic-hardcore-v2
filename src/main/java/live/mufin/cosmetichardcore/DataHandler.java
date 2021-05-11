package live.mufin.cosmetichardcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class DataHandler  {

    private Main plugin;
    public Main.DataType type;
    public DataHandler(Main plugin) {
        this.plugin = plugin;
        try {
            type = Main.DataType.valueOf(plugin.getConfig().getString("storagetype"));
        } catch (IllegalArgumentException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Invalid storage type. Disabling plugin.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public int getLives(Player player) {
        if(type == Main.DataType.NBT) {
            if(player.getPersistentDataContainer().has(plugin.key, PersistentDataType.INTEGER))
                return player.getPersistentDataContainer().get(plugin.key, PersistentDataType.INTEGER);
        } else if(type == Main.DataType.FILE) {
            return plugin.fileManager.getConfig().getInt(player.getUniqueId() + ".lives");
        } else if(type == Main.DataType.DATABASE) {
            return plugin.sqlGetter.getLives(player.getUniqueId());
        }
        return 0;
    }
}
