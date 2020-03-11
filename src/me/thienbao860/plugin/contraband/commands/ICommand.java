package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface ICommand {

    void execute(AtomMcContraband plugin, CommandSender sender, String[] args);

    boolean canConsole();

    List<String> getPermissions();
}
