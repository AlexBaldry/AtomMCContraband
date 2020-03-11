package me.thienbao860.plugin.contraband;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.thienbao860.plugin.contraband.object.CTimer;
import org.bukkit.entity.Player;

import java.util.Date;

public class ContrabandPlaceholder extends PlaceholderExpansion {

    private final AtomMcContraband plugin;
    Date d1;
    Date d2;

    public ContrabandPlaceholder(AtomMcContraband plugin) {
        this.plugin = plugin;

    }

    @Override
    public String getIdentifier() {
        return "contraband";
    }

    @Override
    public String getAuthor() {
        return "thienbao860";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        String[] args = params.split("_");
        d1 = CTimer.getNewDate(new Date(plugin.getConfig().getLong("startTimeDate.rare")), plugin.getConfig().getInt("countdownTime.rare"));
        d2 = CTimer.getNewDate(new Date(plugin.getConfig().getLong("startTimeDate.common")), plugin.getConfig().getInt("countdownTime.common"));
        if (params.equalsIgnoreCase("balance")) {
            try {
                return String.valueOf(AtomMcContraband.getInstance().getPlayerManager().getPlayer(p).getCont());
            } catch (NullPointerException e) {
                return " ";
            }
        }

        if (args[0].equalsIgnoreCase("timer")) {
            if (args[1].equalsIgnoreCase("rare")) {
                return CTimer.getTimeRemaining(d1.getTime());
            }
            if (args[1].equalsIgnoreCase("common")) {
                return CTimer.getTimeRemaining(d2.getTime());
            }
        }

        return null;
    }
}
