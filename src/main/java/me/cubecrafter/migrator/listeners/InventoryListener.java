package me.cubecrafter.migrator.listeners;

import lombok.RequiredArgsConstructor;
import me.cubecrafter.migrator.HypixelMigrator;
import me.cubecrafter.migrator.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

@RequiredArgsConstructor
public class InventoryListener implements Listener {

    private final HypixelMigrator plugin;

    @EventHandler
    public void onShopOpen(InventoryOpenEvent e) {
        Player player = (Player) e.getPlayer();
        Object arena = plugin.getBedWars().getArenaByPlayer(player);
        if (arena == null) return;
        if (e.getView().getTitle().equals(plugin.getBedWars().getShopIndexName(player))) {
            YamlConfiguration config = plugin.getFileManager().getConfig();
            int slot = config.getInt("shop-item.slot");
            e.getInventory().setItem(slot, ItemBuilder.fromConfig(config.getConfigurationSection("shop-item")).setTag("migrator", "migrate-item").build());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        Player player = (Player) e.getWhoClicked();
        Object arena = plugin.getBedWars().getArenaByPlayer(player);
        if (arena == null) return;
        if (ItemBuilder.getTag(e.getCurrentItem(), "migrator").equals("migrate-item")) {
            player.closeInventory();
            plugin.getMigrator().migrate(player);
        }
    }

}
