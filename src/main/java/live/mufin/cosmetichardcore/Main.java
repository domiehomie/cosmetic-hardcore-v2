package live.mufin.cosmetichardcore;

import live.mufin.cosmetichardcore.commands.ChrlCommand;
import live.mufin.cosmetichardcore.commands.SetLivesCommand;
import live.mufin.cosmetichardcore.data.MySQL;
import live.mufin.cosmetichardcore.data.PlayerDataManager;
import live.mufin.cosmetichardcore.data.SQLDataManager;
import live.mufin.cosmetichardcore.events.ChatEvent;
import live.mufin.cosmetichardcore.events.DeathEvent;
import live.mufin.cosmetichardcore.events.JoinEvent;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.CustomChart;
import org.bstats.json.JsonObjectBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Main extends JavaPlugin {

    public DataHandler dataHandler;
    public PlayerDataManager fileManager;
    public MySQL sql;
    public SQLDataManager sqlGetter;
    public static NamespacedKey key;

    public void onEnable() {
        // Save overall config
        this.saveDefaultConfig();
        this.registerBStats();

        this.dataHandler = new DataHandler(this);
        this.fileManager = new PlayerDataManager(this);
        this.sql = new MySQL(this);
        this.sqlGetter = new SQLDataManager(this);

        key = new NamespacedKey(this, "lives");

        CommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] ======================================="));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &cCosmeticHardcore &7v&c2.0&7 by &cmufinlive&7."));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &7Selected data type: &c" + this.getConfig().getString("storagetype") + "&7."));
        if(dataHandler.type == DataType.FILE) {
            fileManager.saveDefaultConfig();
        } else if (dataHandler.type == DataType.DATABASE) {
            try {
                sql.connect();
                this.sqlGetter.createTable();
                console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &7Database connection &aSUCCESS"));
            } catch (ClassNotFoundException |  SQLException e) {
                console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &7Database connection &cFAILED"));
            }
        }
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] ======================================="));


        this.getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new JoinEvent(this), this);

        this.getCommand("chrl").setExecutor(new ChrlCommand(this));
        this.getCommand("setlives").setExecutor(new SetLivesCommand(this));
    }

    public void onDisable() {

    }

    public enum DataType {
        FILE, NBT, DATABASE
    }


    public void registerBStats() {
        int id = 11675;
        Metrics metrics = new Metrics(this, id);
    }

}
