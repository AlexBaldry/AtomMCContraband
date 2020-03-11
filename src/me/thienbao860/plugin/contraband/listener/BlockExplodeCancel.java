package me.thienbao860.plugin.contraband.listener;

import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent;
import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.utils.MathUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockExplodeCancel implements Listener {

    private final AtomMcContraband plugin;

    public BlockExplodeCancel(AtomMcContraband plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onExplode(TEBlockExplodeEvent event) {
        for (Block block : event.blockList()) {
            for (String s : plugin.getConfig().getStringList("blocksContraband")) {
                String[] args = s.split(";");
                String[] split = args[0].split(":");

                //noinspection deprecation
                if (block.getType().equals(Material.valueOf(split[0])) && block.getData() == Short.parseShort(split[1])) {
                    if (!MathUtil.inRandomChance(Double.parseDouble(args[1]))) {
                        block.setType(block.getType());
                    }


                }
            }
            return;

        }
    }

}
