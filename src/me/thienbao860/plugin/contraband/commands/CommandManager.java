package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.*;

public class CommandManager implements CommandExecutor {

    private final AtomMcContraband plugin;
    Map<String, ICommand> cmds = new HashMap<>();

    public CommandManager(AtomMcContraband plugin) {
        this.plugin = plugin;
        cmds.put("msg", new MessageDisableCommand());
        cmds.put("pay", new PayCommand());
        cmds.put("admin", new AdminPayCommand());
        cmds.put("reload", new ReloadCommand());
        cmds.put("balance", new BalanceCommand());
        cmds.put("bal", new BalanceCommand());
        cmds.put("shop", new ShopCommand());
        cmds.put("top", new TopCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        boolean isExist = false;
        if (args.length == 0) {
            for (String st : plugin.getConfig().getStringList("messages.helpMessage")) {
                sender.sendMessage(MsgUtil.colorize(st));
            }
            return true;
        }
        for (String cmd : cmds.keySet()) {
            if (cmd.equalsIgnoreCase(args[0])) {
                ICommand icmd = cmds.get(cmd);
                if (!icmd.canConsole()) {
                    if (sender instanceof ConsoleCommandSender) {
                        return true;
                    }
                }
                if (ifHasPermission(sender, icmd.getPermissions())) {
                    cmds.get(cmd).execute(plugin, sender, args);
                } else sender.sendMessage(MsgUtil.confMessage("noPermission"));
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            sender.sendMessage(MsgUtil.confMessage("unknownCommand"));
        }
        return true;
    }

    public boolean ifHasPermission(CommandSender sender, List<String> permission) {

        if (sender.isOp() || permission == null || permission.size() == 0) return true;
        for (String perm : permission) {
            if (sender.hasPermission(perm)) return true;
        }
        return false;

    }


}
