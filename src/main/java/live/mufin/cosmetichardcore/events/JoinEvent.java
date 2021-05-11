package live.mufin.cosmetichardcore.events;

import live.mufin.cosmetichardcore.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class JoinEvent implements Listener {
    private Main plugin;
    public JoinEvent(Main plugin) { this.plugin = plugin; }

    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        int defaultlives = plugin.getConfig().getInt("defaultlives");
        switch(plugin.dataHandler.type) {
            case NBT:
                if(!p.getPersistentDataContainer().has(plugin.key, PersistentDataType.INTEGER))
                    p.getPersistentDataContainer().set(plugin.key, PersistentDataType.INTEGER, defaultlives);
                break;
            case FILE:
                if(!plugin.fileManager.getConfig().contains(p.getUniqueId() + ".lives"))
                    plugin.fileManager.getConfig().set(p.getUniqueId() + ".lives", defaultlives);
                break;
            case DATABASE:
                if(!plugin.sqlGetter.exists(p.getUniqueId()))
                    plugin.sqlGetter.setLives(p.getUniqueId(), defaultlives);
                break;
        }
    }
}
