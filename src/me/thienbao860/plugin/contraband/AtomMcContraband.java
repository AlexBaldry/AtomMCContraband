package me.thienbao860.plugin.contraband;

import me.clip.placeholderapi.PlaceholderAPI;
import me.thienbao860.plugin.contraband.commands.CommandManager;
import me.thienbao860.plugin.contraband.config.CConfig;
import me.thienbao860.plugin.contraband.listener.*;
import me.thienbao860.plugin.contraband.manager.PlayerManager;
import me.thienbao860.plugin.contraband.manager.ShopManager;
import me.thienbao860.plugin.contraband.object.CShopItem;
import me.thienbao860.plugin.contraband.object.CTimer;
import me.thienbao860.plugin.contraband.object.ShopInventory;
import me.thienbao860.plugin.contraband.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class AtomMcContraband extends JavaPlugin {

    private CConfig conf;
    private static AtomMcContraband instance;
    private ShopManager shopManager;
    private PlayerManager playerManager;
    private ShopInventory inv;
    private ContrabandPlaceholder exp;

    @Override
    public void onEnable() {
        instance = this;
        reload();

        getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new ItemShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new ContrabandCollector(this), this);
        if (Bukkit.getPluginManager().isPluginEnabled("AutoSell")) {
            getServer().getPluginManager().registerEvents(new AutoSellCollector(this), this);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("TokenEnchant")) {
            getServer().getPluginManager().registerEvents(new BlockExplodeCancel(this), this);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            exp = new ContrabandPlaceholder(this);
            exp.register();
        }


        CTimer.runScheduler();

        getCommand("contraband").setExecutor(new CommandManager(this));
    }

    @Override
    public void onDisable() {
        closeAllInventory();
        if (exp != null) {
            PlaceholderAPI.unregisterExpansion(exp);
            exp = null;
        }
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ShopInventory getShopInventory() {
        return inv;
    }

    public CConfig getCConfig() {
        return conf;
    }

    public static AtomMcContraband getInstance() {
        return instance;
    }

    public void closeAllInventory() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof ShopInventory) {
                player.closeInventory();
            }
        }
    }

    public void reload() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveResource("config.yml", false);
        }
        reloadConfig();
        conf = new CConfig(this);
        shopManager = new ShopManager(this);
        playerManager = new PlayerManager();
        shopManager.loadShop();
        inv = new ShopInventory();
        playerManager.load();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (!getConfig().isSet("startTimeDate.rare")) {
                getConfig().set("startTimeDate.rare", System.currentTimeMillis());
            }
            if (!getConfig().isSet("startTimeDate.common")) {
                getConfig().set("startTimeDate.common", System.currentTimeMillis());
            }

            if (getConfig().getStringList("shopOrder.rare").isEmpty()) {
                ArrayList<String> arr = new ArrayList<>();

                for (CShopItem item : MathUtil.randomArray(getShopManager().getRareItems(), 3)) {
                    arr.add(item.getName());
                }
                getConfig().set("shopOrder.rare", arr);
            }
            if (getConfig().getStringList("shopOrder.common").isEmpty()) {
                ArrayList<String> arr = new ArrayList<>();
                for (CShopItem item : MathUtil.randomArray(getShopManager().getCommonItems(), 9)) {
                    arr.add(item.getName());
                }
                getConfig().set("shopOrder.common", arr);
            }
            reloadConfig();
        }, 5L);
    }


}
