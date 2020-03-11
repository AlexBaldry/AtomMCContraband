package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.utils.MsgUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand implements ICommand {

    @Override
    public void execute(AtomMcContraband plugin, CommandSender sender, String[] args) {
        AtomMcContraband.getInstance().reload();
        sender.sendMessage(MsgUtil.confMessage("reload"));
    }

    @Override
    public boolean canConsole() {
        return false;
    }

    @Override
    public List<String> getPermissions() {
        return null;
    }
}
