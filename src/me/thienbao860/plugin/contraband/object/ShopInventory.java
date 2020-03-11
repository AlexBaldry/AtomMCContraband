package me.thienbao860.plugin.contraband.object;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.manager.ShopManager;
import me.thienbao860.plugin.contraband.utils.MathUtil;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopInventory implements InventoryHolder {

    private final ShopManager sm;
    HashMap<Integer, CShopItem> map = new HashMap<>();
    Inventory inv;
    AtomMcContraband plugin;
    int[] rareOrder = {19,28,37};
    int[] commonOrder = {23,24,25,32,33,34,41,42,43};
    int[] backgrounds = {0,1,2,3,4,5,6,7,8,9,11,12,13,14,16,17,18,20,21,22,26,27,29,30,31,35,36,38,39,40,44,45,46,47,48,49,50,51,52,53};

    public ShopInventory() {
        plugin = AtomMcContraband.getInstance();
        String name = MsgUtil.colorize(AtomMcContraband.getInstance().getConfig().getString("title"));
        inv = Bukkit.createInventory(this, 54, name);
        for (int b : backgrounds) {
            inv.setItem(b, new ItemStack(Material.STAINED_GLASS_PANE,1, (short) 15));
        }
        sm = plugin.getShopManager();
        load();
    }

    public void load() {
        int id1 = 0;
        int id2 = 0;
        map.clear();
        try {
            for (int i : rareOrder) {
                String s = plugin.getConfig().getStringList("shopOrder.rare").get(id1);
                if (s != null) {
                    CShopItem item = sm.getItemFromName(s, 0);
                    if (item != null) {
                        map.put(i, item);
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
                        map.put(i, item);
                    }
                }
                id2++;
            }
        } catch (IndexOutOfBoundsException ignored) {}
    }

    //0 = rare, 1 = common
    public void randomize(int typeId) {
        ArrayList<String> a = new ArrayList<>();
        switch (typeId) {
            case 0:
                for (CShopItem si : MathUtil.randomArray(sm.getRareItems(), 3)) {
                    a.add(si.getName());
                }
                plugin.getConfig().set("shopOrder.rare", a);
                break;
            case 1:
                for (CShopItem si : MathUtil.randomArray(sm.getRareItems(), 6)) {
                    a.add(si.getName());
                }
                plugin.getConfig().set("shopOrder.common", a);
                break;
        }
        plugin.saveConfig();
    }


    @Override
    public Inventory getInventory() {
        load();
        return inv;
    }

    public HashMap<Integer, CShopItem> getInventorySlot() {
        return map;
    }
}
