package me.cubecrafter.migrator;

import com.tomkeuper.bedwars.api.BedWars;
import lombok.Getter;
import me.cubecrafter.migrator.config.FileManager;
import me.cubecrafter.migrator.core.LayoutMigrator;
import me.cubecrafter.migrator.listeners.InventoryListener;
import me.cubecrafter.migrator.support.BW1058;
import me.cubecrafter.migrator.support.BW2023;
import me.cubecrafter.migrator.support.BedWars2023Addon;
import me.cubecrafter.migrator.support.IBedWars;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class HypixelMigrator extends JavaPlugin {

    @Getter
    private static HypixelMigrator instance;
    private FileManager fileManager;
    private LayoutMigrator migrator;
    private IBedWars bedWars;

    @Override
    public void onEnable() {
       instance = this;
       fileManager = new FileManager(this);
       if (fileManager.getConfig().getString("hypixel-api-key").isEmpty()) {
           getLogger().severe("The Hypixel API key is not set! Please set it in config.yml!");
           getLogger().severe("To get your API key, join hypixel.net and use the command /api new. Disabling...");
           getServer().getPluginManager().disablePlugin(this);
           return;
       }
        if (Bukkit.getPluginManager().isPluginEnabled("BedWars1058")){
            System.out.println(ChatColor.translateAlternateColorCodes('&', "[BedWars1058-HypixelMigrator] &aBedWars1058 found... Hooking!"));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "[BedWars1058-HypixelMigrator] &aRunning on" + "&b" + getServer().getVersion()));
            bedWars = new BW1058();
        } else if (Bukkit.getPluginManager().isPluginEnabled("BedWars2023")){
            System.out.println(ChatColor.translateAlternateColorCodes('&', "[BedWars2023-HypixelMigrator] &aBedWars2023 found... Hooking!"));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "[BedWars2023-HypixelMigrator] &aRunning on" + "&b" + getServer().getVersion()));
            bedWars = new BW2023();
            Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider().getAddonsUtil().registerAddon(new BedWars2023Addon());
        }

       migrator = new LayoutMigrator(this);
       getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
       new Metrics(this, 16340);
    }

}
