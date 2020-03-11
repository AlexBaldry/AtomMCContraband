package me.thienbao860.plugin.contraband;

import me.clip.placeholderapi.util.Msg;
import me.thienbao860.plugin.contraband.object.CPlayer;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ContrabandCommand implements CommandExecutor {

    private final AtomMcContraband plugin;

    public ContrabandCommand(AtomMcContraband plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length == 0) {
            for (String st : plugin.getConfig().getStringList("messages.helpMessage")) {
                sender.sendMessage(MsgUtil.colorize(st));
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("shop")) {
            if (isConsole(sender)) {
                return true;
            }
            if (sender.hasPermission("contraband.shop") || !sender.isOp()) {
                ((Player) sender).openInventory(AtomMcContraband.getInstance().getShopInventory().getInventory());
            } else sender.sendMessage(MsgUtil.confMessage("noPermission"));
        }

        if (args[0].equalsIgnoreCase("balance")) {

            if (args.length == 1) {
                if (!isConsole(sender)) {
                    if (!sender.hasPermission("contraband.bal") || !sender.isOp()) {
                        sender.sendMessage(MsgUtil.confMessage("noPermission"));
                        return true;
                    }
                    Player player = (Player) sender;
                    CPlayer ct = plugin.getPlayerManager().getPlayer(player);
                    sender.sendMessage(MsgUtil.confMessage("balance").replaceAll("%contraband%", String.valueOf(ct.getCont())));
                }
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (!sender.hasPermission("contraband.bal.other") || !sender.isOp()) {
                        sender.sendMessage(MsgUtil.confMessage("noPermission"));
                        return true;
                    }
                    CPlayer ct = plugin.getPlayerManager().getPlayer(target);
                    if (ct != null) {
                        sender.sendMessage(MsgUtil.confMessage("balance").replaceAll("%contraband%", String.valueOf(ct.getCont())));
                    }
                }
            }
        }

        if (args[0].equalsIgnoreCase("pay")) {
            if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
                Player player = (Player) sender;
                if (target != null) {
                    try {
                        if (AtomMcContraband.getInstance().getPlayerManager().getPlayer(player).getCont() < Integer.parseInt(args[2])) {
                            sender.sendMessage(MsgUtil.confMessage("tradingError"));
                            return true;
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
                        sender.sendMessage(MsgUtil.confMessage("addedError"));
                    }
                } else sender.sendMessage(MsgUtil.confMessage("unknownPlayer"));
            } else sender.sendMessage(MsgUtil.confMessage("addedError"));
        }

        //contraband give thienbao860 10
        if (args[0].equalsIgnoreCase("admin")) {
            if (args.length == 4 && args[1].equalsIgnoreCase("pay")) {
                if (!sender.hasPermission("contraband.admin") || !sender.isOp()) {
                    sender.sendMessage(MsgUtil.confMessage("noPermission"));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[2]);
                if (target != null) {
                    try {
                        if (Integer.parseInt(args[3]) <= 0) {
                            sender.sendMessage(MsgUtil.confMessage("invalidNumber"));
                            return true;
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
                    return true;
                }
                Player target = Bukkit.getPlayer(args[2]);
                if (target != null) {
                    try {
                        if (Integer.parseInt(args[3]) <= 0) {
                            sender.sendMessage(MsgUtil.confMessage("invalidNumber"));
                            return true;
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

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("contraband.admin") || !sender.isOp()) {
                sender.sendMessage(MsgUtil.confMessage("noPermission"));
                return true;
            }
            AtomMcContraband.getInstance().reload();
            sender.sendMessage(MsgUtil.confMessage("reload"));

        }

        if (args[0].equalsIgnoreCase("msg")) {
            if (isConsole(sender)) return true;
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

        return true;
    }

    public boolean isConsole(CommandSender sender) {
        return sender instanceof ConsoleCommandSender;
    }

}
