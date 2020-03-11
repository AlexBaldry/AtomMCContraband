package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.object.CPlayer;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.*;

public class TopCommand implements ICommand {


    @Override
    public void execute(AtomMcContraband plugin, CommandSender sender, String[] args) {
        List<CPlayer> players = sorted(plugin.getPlayerManager().getPlayers());

        try {
            int page;
            if (args.length < 2) {
                page = 1;
            } else page = Integer.parseInt(args[1]) <= 0 ? 1 : Integer.parseInt(args[1]);
            sender.sendMessage(MsgUtil.confMessage("contrabandTop.title").replaceAll("%page%", String.valueOf(page)));
            for (int i = 0; i < 10; i++) {
                int playerId = (10 * page) - (10 - i);
                try {
                    sender.sendMessage(MsgUtil.confMessage("contrabandTop.append")
                            .replaceAll("%rank%", String.valueOf(playerId + 1))
                            .replaceAll("%player%", String.valueOf(Bukkit.getOfflinePlayer(players.get(playerId).getUUID()).getName()))
                            .replaceAll("%contraband%", String.valueOf(players.get(playerId).getCont())));
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    sender.sendMessage(MsgUtil.confMessage("contrabandTop.append")
                            .replaceAll("%rank%", String.valueOf(playerId + 1))
                            .replaceAll("%player%", "---")
                            .replaceAll("%contraband%", ""));
                }
            }

        } catch (NumberFormatException ignored) {}
    }

    @Override
    public boolean canConsole() {
        return true;
    }

    @Override
    public List<String> getPermissions() {
        return null;
    }

    public List<CPlayer> sorted(List<CPlayer> users) {
        List<CPlayer> clone = new ArrayList<>(users);
        clone.sort(Comparator.comparing(CPlayer::getCont).reversed());
        return clone;

    }
}
