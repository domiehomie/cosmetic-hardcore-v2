package live.mufin.cosmetichardcore;

import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class API {

    public static int getLives(Player p) {
        Main main = new Main();
        switch (DataHandler.type) {
            case FILE:
                if(main.fileManager.getConfig().contains(p.getUniqueId() + ".lives"))
                    return main.fileManager.getConfig().getInt(p.getUniqueId() + ".lives");
                break;
            case NBT:
                if(p.getPersistentDataContainer().has(Main.key, PersistentDataType.INTEGER))
                    return p.getPersistentDataContainer().get(Main.key, PersistentDataType.INTEGER);
                break;
            case DATABASE:
                if(main.sqlGetter.exists(p.getUniqueId()))
                    return main.sqlGetter.getLives(p.getUniqueId());
                break;
        }

        return 0;
    }
}
