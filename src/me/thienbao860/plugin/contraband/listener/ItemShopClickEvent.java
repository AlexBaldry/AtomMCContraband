package me.thienbao860.plugin.contraband.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.object.CPlayer;
import me.thienbao860.plugin.contraband.object.CShopItem;
import me.thienbao860.plugin.contraband.object.ShopInventory;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ItemShopClickEvent implements Listener {

    @EventHandler
    public void onClickItemInv(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if (inv != null && inv.getHolder() instanceof ShopInventory) {
            event.setCancelled(true);
        }

        CShopItem item = AtomMcContraband.getInstance().getShopInventory().getInventorySlot().get(event.getSlot());
        if (item != null) {
            CPlayer cp = AtomMcContraband.getInstance().getPlayerManager().getPlayer(player);
            if (cp.getCont() < item.getPrice()) {
                player.sendMessage(MsgUtil.confMessage("notEnoughBal"));
                return;
            }
            for (String cmd : item.getCommands()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, cmd));
            }

            AtomMcContraband.getInstance().getPlayerManager().addAmount(player, -1 * item.getPrice());

        }
    }



}
