package me.thienbao860.plugin.contraband.commands;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ShopCommand implements ICommand {

    @Override
    public void execute(AtomMcContraband plugin, CommandSender sender, String[] args) {

        ((Player) sender).openInventory(AtomMcContraband.getInstance().getShopInventory().getInventory());

    }

    @Override
    public boolean canConsole() {
        return false;
    }

    @Override
    public List<String> getPermissions() {
        List<String> arr = new ArrayList<>();
        arr.add("contraband.shop");
        return arr;
    }
}
