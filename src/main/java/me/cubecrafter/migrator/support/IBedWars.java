package me.cubecrafter.migrator.support;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IBedWars {
    Object getArenaByPlayer(Player player);

    String getShopIndexName(Player player);

    ItemStack setNmsTag(ItemStack item, String key, String value);

    String getNmsTag(ItemStack item, String key);

    void setQuickBuyCache(Player player, String[] items);
}
