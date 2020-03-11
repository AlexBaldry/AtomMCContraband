package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminPayCommand implements ICommand {

    @Override
    public void execute(AtomMcContraband plugin, CommandSender sender, String[] args) {
        if (args.length == 4 && args[1].equalsIgnoreCase("pay")) {
            Player target = Bukkit.getPlayer(args[2]);
            if (target != null) {
                try {
                    if (Integer.parseInt(args[3]) <= 0) {
                        sender.sendMessage(MsgUtil.confMessage("invalidNumber"));
                        return;
                    }
                    AtomMcContraband.getInstance().getPlayerManager().addAmount(target, Integer.parseInt(args[3]));
                    sender.sendMessage(MsgUtil.confMessage("giveAddSuccess")
                            .replaceAll("%contraband%", String.valueOf(args[3]))
                            .replaceAll("%target%", target.getName()));
                } catch (NumberFormatException exc) {
                    sender.sendMessage(MsgUtil.confMessage("giveError"));
                }
            } else sender.sendMessage(MsgUtil.confMessage("unknownPlayer"));
        } else if (args[1].equalsIgnoreCase("remove")) {
            if (!sender.hasPermission("contraband.admin") || !sender.isOp()) {
                sender.sendMessage(MsgUtil.confMessage("giveError"));
                return;
            }
            Player target = Bukkit.getPlayer(args[2]);
            if (target != null) {
                try {
                    if (Integer.parseInt(args[3]) <= 0) {
                        sender.sendMessage(MsgUtil.confMessage("invalidNumber"));
                        return;
                    }
                    AtomMcContraband.getInstance().getPlayerManager().addAmount(target, -1 * Integer.parseInt(args[3]));
                    sender.sendMessage(MsgUtil.confMessage("giveRemoveSuccess")
                            .replaceAll("%contraband%", String.valueOf(args[3]))
                            .replaceAll("%target%", target.getName()));
                } catch (NumberFormatException exc) {
                    sender.sendMessage(MsgUtil.confMessage("giveError"));
                }
            } else sender.sendMessage(MsgUtil.confMessage("unknownPlayer"));
        } else sender.sendMessage(MsgUtil.confMessage("giveError"));
    }

    @Override
    public boolean canConsole() {
        return false;
    }

    @Override
    public List<String> getPermissions() {
        List<String> arr = new ArrayList<>();
        arr.add("contraband.admin");
        return arr;
    }
}
