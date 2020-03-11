package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageDisableCommand implements ICommand {


    @Override
    public void execute(AtomMcContraband plugin, CommandSender sender, String[] args) {

        Player player = (Player) sender;
        FileConfiguration cacheConf = plugin.getCConfig().getCacheConf();
        if (!cacheConf.contains("disabledCollectMsg")) plugin.getCConfig().getCacheConf().set("disabledCollectMsg", new ArrayList<>());
        List<String> getCache = cacheConf.getStringList("disabledCollectMsg");
        if (!getCache.contains(player.getUniqueId().toString())) {
            getCache.add(player.getUniqueId().toString());
            player.sendMessage(MsgUtil.confMessage("disableCollectMsg"));
            plugin.getCConfig().saveCache();
        } else {
            player.sendMessage(MsgUtil.confMessage("enableCollectMsg"));
            getCache.remove(player.getUniqueId().toString());

        }
        cacheConf.set("disabledCollectMsg", getCache);
        plugin.getCConfig().saveCache();

    }

    @Override
    public boolean canConsole() {
        return false;
    }

    @Override
    public List<String> getPermissions() {
        return null;
    }
}
