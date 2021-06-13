package live.mufin.cosmetichardcore.commands;

import live.mufin.cosmetichardcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class SetLivesCommand implements CommandExecutor {

    private Main plugin;

    public SetLivesCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("setlives")) {
            if(!sender.hasPermission("cosmetichardcore.setlives")) return true;
            if(args.length != 2) return true;
            try {
                Player target = Bukkit.getPlayer(args[0]);
                int lives = Integer.parseInt(args[1]);
                switch(plugin.dataHandler.type) {
                    case DATABASE:
                        plugin.sqlGetter.setLives(target.getUniqueId(), lives);
                        break;
                    case FILE:
                        plugin.fileManager.getConfig().set(target.getUniqueId() + ".lives", lives);
                        plugin.fileManager.saveConfig();
                        break;
                    case NBT:
                        target.getPersistentDataContainer().set(plugin.key, PersistentDataType.INTEGER, lives);
                        break;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &7Set lives of &c" + target.getName() + "&7 to &c" + lives + "&7."));
            } catch (NullPointerException | IllegalArgumentException e) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &cInvalid player or amount."));
            }
        }
        return true;
    }
}
