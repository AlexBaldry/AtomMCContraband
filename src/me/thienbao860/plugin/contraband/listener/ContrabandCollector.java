package me.thienbao860.plugin.contraband.listener;

import me.clip.autosell.events.DropsToInventoryEvent;
import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.utils.MathUtil;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class ContrabandCollector implements Listener {

    private final AtomMcContraband plugin;

    public ContrabandCollector(AtomMcContraband plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (catchCollected(event, block)) {
            plugin.getPlayerManager().addAmount(player, 1);
            if (!AtomMcContraband.getInstance().getCConfig().getCacheConf().getStringList("disabledCollectMsg").contains(player.getUniqueId().toString())) {
                player.sendMessage(MsgUtil.confMessage("contrabandcollect"));
            }
        }


    }


    public boolean catchCollected(BlockBreakEvent event, Block block) {
        for (String s : plugin.getConfig().getStringList("blocksContraband")) {
            String[] args = s.split(";");
            String[] split = args[0].split(":");

            //noinspection deprecation
            if (block.getType().equals(Material.valueOf(split[0])) && block.getData() == Short.parseShort(split[1])) {
                if (event != null) {
                    event.setDropItems(false);
                }
                return MathUtil.inRandomChance(Double.parseDouble(args[1]));

            }
        }
        return false;
    }
}
