package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PayCommand implements ICommand {

    @Override
    public void execute(AtomMcContraband plugin, CommandSender sender, String[] args) {
        if (args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            Player player = (Player) sender;
            if (target != null) {
                try {
                    if (AtomMcContraband.getInstance().getPlayerManager().getPlayer(player).getCont() < Integer.parseInt(args[2])) {
                        sender.sendMessage(MsgUtil.confMessage("tradingError"));
                        return;
                    }
                    AtomMcContraband.getInstance().getPlayerManager().addAmount(target, Integer.parseInt(args[2]));
                    sender.sendMessage(MsgUtil.confMessage("tradingSuccess")
                            .replaceAll("%contraband%", String.valueOf(args[2]))
                            .replaceAll("%target%", target.getName()));
                    target.sendMessage(MsgUtil.confMessage("tradingTarget")
                            .replaceAll("%contraband%", String.valueOf(args[2]))
                            .replaceAll("%player%", player.getName()));
                    AtomMcContraband.getInstance().getPlayerManager().addAmount(player, -1 * Integer.parseInt(args[2]));

                } catch (NumberFormatException exc) {
                    sender.sendMessage(MsgUtil.confMessage("tradingCmdError"));
                }
            } else sender.sendMessage(MsgUtil.confMessage("unknownPlayer"));
        } else sender.sendMessage(MsgUtil.confMessage("tradingCmdError"));

    }

    @Override
    public boolean canConsole() {
        return false;
    }

    @Override
    public List<String> getPermissions() {
        List<String> arr = new ArrayList<>();
        arr.add("contraband.pay");
        return arr;
    }
}
