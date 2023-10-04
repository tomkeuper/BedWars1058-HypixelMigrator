package me.cubecrafter.migrator.support;

import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.shop.ICategoryContent;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import com.tomkeuper.bedwars.api.shop.IPlayerQuickBuyCache;
import me.cubecrafter.migrator.core.LayoutItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;

public class BW2023 implements IBedWars{
    final BedWars instance;

    public BW2023() {
        this.instance = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
    }

    @Override
    public Object getArenaByPlayer(Player player) {
        return instance.getArenaUtil().getArenaByPlayer(player);
    }

    @Override
    public String getShopIndexName(Player player) {
        return Language.getMsg(player, Messages.SHOP_INDEX_NAME);
    }

    @Override
    public ItemStack setNmsTag(ItemStack item, String key, String value){
        return instance.getVersionSupport().setTag(item,key,value);
    }

    @Override
    public String getNmsTag(ItemStack item, String key) {
        return instance.getVersionSupport().getTag(item, key);
    }

        @Override
    public void setQuickBuyCache(Player player, String[] items){
        IPlayerQuickBuyCache cache = instance.getShopUtil().getPlayerQuickBuyCache().getQuickBuyCache(player.getUniqueId());
        Iterator<String> it = Arrays.stream(items).iterator();
        for (int i = 19; i < 44; i++) {
            if (i == 26 || i == 27 || i == 35 || i == 36) continue;
            String item = it.next();
            if (item.equals("null")) {
                cache.setElement(i, (ICategoryContent) null);
            } else {
                String category = LayoutItem.matchItem(item).getCategory();
                cache.setElement(i, category);
            }
        }
        cache.pushChangesToDB();
    }

}
