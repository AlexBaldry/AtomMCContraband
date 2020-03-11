package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.object.CPlayer;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BalanceCommand implements ICommand {

    @Override
    public void execute(AtomMcContraband plugin, CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (!(sender instanceof ConsoleCommandSender)) {
                if (!sender.hasPermission("contraband.bal")) {
                    sender.sendMessage(MsgUtil.confMessage("noPermission"));
                    return;
                }
                Player player = (Player) sender;
                CPlayer ct = plugin.getPlayerManager().getPlayer(player);
                sender.sendMessage(MsgUtil.confMessage("balance").replaceAll("%contraband%", String.valueOf(ct.getCont())));
            }
        } else {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                if (!sender.hasPermission("contraband.bal.other")) {
                    sender.sendMessage(MsgUtil.confMessage("noPermission"));
                    return;
                }
                CPlayer ct = plugin.getPlayerManager().getPlayer(target);
                if (ct != null) {
                    sender.sendMessage(MsgUtil.confMessage("balanceOther")
                            .replaceAll("%contraband%", String.valueOf(ct.getCont()))
                            .replaceAll("%target%", target.getName()));
                }
            }
        }
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
