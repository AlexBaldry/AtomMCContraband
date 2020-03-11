package me.thienbao860.plugin.contraband.object;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.manager.ShopManager;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CTimer {

    public static void runScheduler() {
        AtomMcContraband plugin = AtomMcContraband.getInstance();
        Inventory inv = plugin.getShopInventory().getInventory();
        ShopManager sm = plugin.getShopManager();
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,
                () -> {
                    long millis1 = plugin.getConfig().getLong("startTimeDate.rare");
                    long millis2 = plugin.getConfig().getLong("startTimeDate.common");
                    inv.setItem(10, setWatch(plugin.getConfig().getString("rareClock.title"), plugin.getConfig().getStringList("rareClock.lores")));
                    inv.setItem(15, setWatch(plugin.getConfig().getString("commonClock.title"), plugin.getConfig().getStringList("commonClock.lores")));

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getOpenInventory().getTopInventory().getHolder() instanceof ShopInventory) {
                            player.updateInventory();
                        }
                    }

                    if (isExpired(getNewDate(new Date(millis1), plugin.getConfig().getInt("countdownTime.rare")))) {
                        plugin.getConfig().set("startTimeDate.rare", System.currentTimeMillis());
                        plugin.getShopInventory().randomize(0);
                        plugin.saveConfig();
                    }
                    if (isExpired(getNewDate(new Date(millis2), plugin.getConfig().getInt("countdownTime.common")))) {
                        plugin.getConfig().set("startTimeDate.common", System.currentTimeMillis());
                        plugin.getShopInventory().randomize(1);
                        plugin.saveConfig();
                    }

                    int id1 = 0;
                    int id2 = 0;
                    int[] rareOrder = {19,28,37};
                    int[] commonOrder = {23,24,25,32,33,34,41,42,43};
                    try {
                        for (int i : rareOrder) {
                            String s = plugin.getConfig().getStringList("shopOrder.rare").get(id1);
                            if (s != null) {
                                CShopItem item = sm.getItemFromName(s, 0);
                                if (item != null) {
                                    inv.setItem(i, item.getItemStack());
                                }
                            }
                            id1++;
                        }

                    } catch (IndexOutOfBoundsException ignored) {}

                    try {
                        for (int i : commonOrder) {
                            String s = plugin.getConfig().getStringList("shopOrder.common").get(id2);
                            if (s != null) {
                                CShopItem item = sm.getItemFromName(s, 1);
                                if (item != null) {
                                    inv.setItem(i, item.getItemStack());
                                }
                            }
                            id2++;
                        }
                    } catch (IndexOutOfBoundsException ignored) {}
                }, 0L, 20L);
    }


    public static String getTimeRemaining(long futureTime) {

        long currentDate = System.currentTimeMillis();
        long millis = futureTime - currentDate;

        return String.format("%02dd %02dh %02dm %02ds"
                , TimeUnit.MILLISECONDS.toDays(millis)
                , TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis))
                , TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
                , TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static Date getNewDate(Date d1, int seconds){

        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.SECOND, seconds);
        return c.getTime();

    }

    public static boolean isExpired(Date future) {
        return (System.currentTimeMillis() > future.getTime());
    }

    private static ItemStack setWatch(String name, List<String> lores) {
        ItemStack is = new ItemStack(Material.WATCH, 1);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(MsgUtil.colorize(name));
        ArrayList<String> newLore = new ArrayList<>();
        for (String lore : lores) {
            if (Bukkit.getOnlinePlayers().size() > 0) {
                newLore.add(MsgUtil.parsePAPI((Player) Bukkit.getOnlinePlayers().toArray()[0], MsgUtil.colorize(lore)));
            }
        }
        meta.setLore(newLore);
        is.setItemMeta(meta);
        return is;
    }



}
