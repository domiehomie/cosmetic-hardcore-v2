package live.mufin.cosmetichardcore.commands;

import live.mufin.cosmetichardcore.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChrlCommand implements CommandExecutor {
    private Main plugin;
    public ChrlCommand(Main plugin) {this.plugin = plugin;}
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("chrl")) {
            if(!sender.hasPermission("cosmetichardcore.reload")) return true;
            if(plugin.dataHandler.type == Main.DataType.FILE) {
                plugin.fileManager.reloadConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &7Reloaded &cconfig.yml &7and &cplayerdata.yml&7."));
            }else{
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &7Reloaded &cconfig.yml&7."));
            }
        }
        return true;
    }
}
