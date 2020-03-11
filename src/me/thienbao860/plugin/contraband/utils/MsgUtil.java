package me.thienbao860.plugin.contraband.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.thienbao860.plugin.contraband.AtomMcContraband;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MsgUtil {

    public static String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String confMessage(String confLoc) {
        return colorize(AtomMcContraband.getInstance().getConfig().getString("messages." + confLoc));
    }

    public static String parsePAPI(Player player, String msg) {
        return PlaceholderAPI.setPlaceholders(player, msg);
    }
}
