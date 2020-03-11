package me.thienbao860.plugin.contraband.listener;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.config.CConfig;
import me.thienbao860.plugin.contraband.manager.PlayerManager;
import me.thienbao860.plugin.contraband.object.CPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerManager pm = AtomMcContraband.getInstance().getPlayerManager();
        CConfig conf = AtomMcContraband.getInstance().getCConfig();
        if (pm.getPlayer(event.getPlayer()) == null) {
            if (!conf.getPlayerConf().isSet("players." + event.getPlayer().getUniqueId().toString())) {
                conf.getPlayerConf().set("players." + event.getPlayer().getUniqueId().toString() + ".contraband", 0);
                conf.savePlayer();
            }
            pm.getPlayers().add(new CPlayer(event.getPlayer().getUniqueId(), conf.getPlayerConf().getInt("players." + event.getPlayer().getUniqueId().toString() + ".contraband")));

        }
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent event) {
        PlayerManager pm = AtomMcContraband.getInstance().getPlayerManager();
        CConfig conf = AtomMcContraband.getInstance().getCConfig();

        CPlayer player = pm.getPlayer(event.getPlayer());
        if (player != null) {
            conf.getPlayerConf().set("players." + event.getPlayer().getUniqueId().toString() + ".contraband", player.getCont());
            conf.savePlayer();
        }
    }

}
