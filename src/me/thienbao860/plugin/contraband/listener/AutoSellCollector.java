package me.thienbao860.plugin.contraband.listener;

import me.clip.autosell.events.DropsToInventoryEvent;
import me.thienbao860.plugin.contraband.AtomMcContraband;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class AutoSellCollector implements Listener {

    private final AtomMcContraband plugin;

    public AutoSellCollector(AtomMcContraband plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void dropToInv(DropsToInventoryEvent event) {
        if (catchCollected(event.getBlock())) {
            event.setDrops(new ArrayList<>());
            event.setCancelled(true);
        }
    }


    public boolean catchCollected(Block block) {
        for (String s : plugin.getConfig().getStringList("blocksContraband")) {
            String[] args = s.split(";");
            String[] split = args[0].split(":");

            //noinspection deprecation
            if (block.getType().equals(Material.valueOf(split[0])) && block.getData() == Short.parseShort(split[1])) {
                return true;
            }
        }
        return false;
    }
}
