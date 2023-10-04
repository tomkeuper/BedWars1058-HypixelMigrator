package me.cubecrafter.migrator.support;

import com.tomkeuper.bedwars.api.addon.Addon;
import me.cubecrafter.migrator.HypixelMigrator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BedWars2023Addon extends Addon {
    @Override
    public String getAuthor() {
        return HypixelMigrator.getInstance().getDescription().getAuthors().get(0);
    }

    @Override
    public Plugin getPlugin() {
        return HypixelMigrator.getInstance();
    }

    @Override
    public String getVersion() {
        return HypixelMigrator.getInstance().getDescription().getVersion();
    }

    @Override
    public String getDescription() {
        return HypixelMigrator.getInstance().getDescription().getDescription();
    }

    @Override
    public String getName() {
        return HypixelMigrator.getInstance().getDescription().getName();
    }

    @Override
    public void load() {
        Bukkit.getPluginManager().enablePlugin(HypixelMigrator.getInstance());
    }

    @Override
    public void unload() {
        Bukkit.getPluginManager().disablePlugin(HypixelMigrator.getInstance());
    }
}
