package live.mufin.cosmetichardcore.events;

import live.mufin.cosmetichardcore.Main;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataType;

public class ChatEvent implements Listener {

    private Main plugin;
    public ChatEvent(Main plugin) { this.plugin = plugin; }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        switch(plugin.dataHandler.type) {
            case NBT:
                this.updatePlayer(p, p.getPersistentDataContainer().get(plugin.key, PersistentDataType.INTEGER));
                break;
            case FILE:
                this.updatePlayer(p, plugin.fileManager.getConfig().getInt(p.getUniqueId() + ".lives"));
                break;
            case DATABASE:
                this.updatePlayer(p, plugin.sqlGetter.getLives(p.getUniqueId()));
                break;
        }
    }

    public void updatePlayer(Player p, int lives) {
        if(lives != 0) {
            p.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("hardcorename").replace("%PLAYER%", p.getName())));
        } else {
            p.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("normalname").replace("%PLAYER%", p.getName())));
        }
    }

}
