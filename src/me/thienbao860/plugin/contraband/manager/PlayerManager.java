package me.thienbao860.plugin.contraband.manager;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.object.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    List<CPlayer> players = new ArrayList<>();

    public void addAmount(Player player, int amount) {
        CPlayer cp = getPlayer(player);
        if (cp != null) {
            cp.setCont(cp.getCont() + amount);
            AtomMcContraband.getInstance().getCConfig().savePlayer();
        }
    }

    public CPlayer getPlayer(Player player) {
        for (CPlayer cp : players) {
            if (player.getUniqueId().equals(cp.getUUID())) {
                return cp;
            }
        }
        return null;
    }

    public List<CPlayer> getPlayers() {
        return players;
    }

    public void load() {
        FileConfiguration conf = AtomMcContraband.getInstance().getCConfig().getPlayerConf();
        players.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (conf.isSet("players." + player.getUniqueId().toString())) {
                int cont = conf.getInt("players." + player.getUniqueId().toString() + ".contraband");
                players.add(new CPlayer(player.getUniqueId(), cont));
            } else {
                players.add(new CPlayer(player.getUniqueId(), 0));
            }
        }
        AtomMcContraband.getInstance().getCConfig().savePlayer();

    }


}
