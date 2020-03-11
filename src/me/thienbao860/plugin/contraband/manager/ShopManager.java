package me.thienbao860.plugin.contraband.manager;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.object.CShopItem;
import me.thienbao860.plugin.contraband.utils.MathUtil;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopManager {

    private final AtomMcContraband plugin;
    private List<CShopItem> rare;
    private List<CShopItem> common;

    public ShopManager(AtomMcContraband plugin) {
        this.plugin = plugin;
        rare = new ArrayList<>();
        common = new ArrayList<>();
    }

    public void loadShop() {
        rare.clear();
        common.clear();
        FileConfiguration conf = plugin.getCConfig().getShopConf();
        if (!conf.isSet("shops.rare")) conf.set("shops.rare", new ArrayList<>());
        if (!conf.isSet("shops.common")) conf.set("shops.common", new ArrayList<>());
        plugin.getCConfig().saveShop();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            try {
                if (!conf.getConfigurationSection("shops.rare").getKeys(false).isEmpty()) {
                    for (String item : conf.getConfigurationSection("shops.rare").getKeys(false)) {
                        ArrayList<String> arr = new ArrayList<>();
                        String f = "shops.rare." + item + ".";
                        ItemStack is = new ItemStack(Material.valueOf(conf.getString(f + "material").toUpperCase()), 1, (byte) conf.getInt(f + "data"));
                        ItemMeta meta = is.getItemMeta();
                        meta.setDisplayName(MsgUtil.colorize(conf.getString(f + "name")));
                        for (String name : conf.getStringList(f + "lores")) {
                            arr.add(MsgUtil.colorize(name));
                        }
                        meta.setLore(arr);
                        is.setItemMeta(meta);
                        int price = conf.getInt(f + "price");
                        rare.add(new CShopItem(is, item, 0, price, conf.getStringList(f + "commands")));
                    }
                }
            } catch (NullPointerException ignored) {}

            try {
                if (!conf.getConfigurationSection("shops.common").getKeys(false).isEmpty()) {
                    for (String item : conf.getConfigurationSection("shops.common").getKeys(false)) {
                        ArrayList<String> arr = new ArrayList<>();
                        String f = "shops.common." + item + ".";
                        ItemStack is = new ItemStack(Material.valueOf(conf.getString(f + "material").toUpperCase()), 1, (byte) conf.getInt(f + "data"));
                        ItemMeta meta = is.getItemMeta();
                        meta.setDisplayName(MsgUtil.colorize(conf.getString(f + "name")));
                        for (String name : conf.getStringList(f + "lores")) {
                            arr.add(MsgUtil.colorize(name));
                        }
                        meta.setLore(arr);
                        is.setItemMeta(meta);
                        int price = conf.getInt(f + "price");
                        common.add(new CShopItem(is, item, 1, price, conf.getStringList(f + "commands")));
                    }
                }
            } catch (NullPointerException ignored) { }
        }, 5L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (plugin.getConfig().getStringList("shopOrder.rare").isEmpty()
                    || plugin.getConfig().getStringList("shopOrder.rare").size() < getRareItems().size()) {
                ArrayList<String> arr = new ArrayList<>();
                for (CShopItem item : MathUtil.randomArray(getRareItems(), 3)) {
                    arr.add(item.getName());
                }
                plugin.getConfig().set("shopOrder.rare", arr);
            }
            if (plugin.getConfig().getStringList("shopOrder.common").isEmpty()
                    || plugin.getConfig().getStringList("shopOrder.common").size() < getCommonItems().size()) {
                ArrayList<String> arr = new ArrayList<>();
                for (CShopItem item : MathUtil.randomArray(getCommonItems(), 9)) {
                    arr.add(item.getName());
                }
                plugin.getConfig().set("shopOrder.common", arr);
            }
            plugin.saveConfig();
        }, 5L);
    }

    public List<CShopItem> getRareItems() {
        return rare;
    }

    public List<CShopItem> getCommonItems() {
        return common;
    }

    public CShopItem getItemFromName(String s, int typeId) {
        switch (typeId) {
            case 0:
                for (CShopItem item : getRareItems()) {
                    if (item.getName().equalsIgnoreCase(s)) {
                        return item;
                    }
                }
            case 1:
                for (CShopItem item2 : getCommonItems()) {
                    if (item2.getName().equalsIgnoreCase(s)) {
                        return item2;
                    }
                }
        }
        return null;
    }

}
