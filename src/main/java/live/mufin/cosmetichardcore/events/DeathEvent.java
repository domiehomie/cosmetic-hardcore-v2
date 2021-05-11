package live.mufin.cosmetichardcore.events;

import live.mufin.cosmetichardcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class DeathEvent implements Listener {

    private Main plugin;
    public DeathEvent(Main plugin) { this.plugin = plugin; }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(e.getEntity().getType() != EntityType.PLAYER) return;
        Player p = e.getEntity();
        switch(plugin.dataHandler.type) {
            case NBT:
                if(p.getPersistentDataContainer().has(plugin.key, PersistentDataType.INTEGER)){
                    int lives = p.getPersistentDataContainer().get(plugin.key, PersistentDataType.INTEGER) - 1;
                    if(lives < 0) return;
                    p.getPersistentDataContainer().set(plugin.key, PersistentDataType.INTEGER, lives);
                    if(lives != 0)
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &c" + p.getName() + " &7 has just died and lost a life. They now have &c" + lives + " &7lives."));
                    else
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &c" + p.getName() + " &7 has just died and lost their hardcore status."));
                }
                break;
            case FILE:
                if(plugin.fileManager.getConfig().contains(p.getUniqueId() + ".lives")) {
                    int lives = plugin.fileManager.getConfig().getInt(p.getUniqueId() + ".lives") - 1;
                    plugin.fileManager.getConfig().set(p.getUniqueId() + ".lives", lives);
                    plugin.fileManager.saveConfig();
                    if(lives != 0)
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &c" + p.getName() + " &7 has just died and lost a life. They now have &c" + lives + " &7lives."));
                    else
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &c" + p.getName() + " &7 has just died and lost their hardcore status."));
                }
                break;
            case DATABASE:
                if(plugin.sqlGetter.exists(p.getUniqueId())) {
                    int lives = plugin.sqlGetter.getLives(p.getUniqueId()) - 1;
                    plugin.sqlGetter.setLives(p.getUniqueId(), lives);
                    if(lives != 0)
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &c" + p.getName() + " &7 has just died and lost a life. They now have &c" + lives + " &7lives."));
                    else
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &c" + p.getName() + " &7 has just died and lost their hardcore status."));
                }
                break;
        }
    }
}
