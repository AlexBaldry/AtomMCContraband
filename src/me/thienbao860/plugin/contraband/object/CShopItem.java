package me.thienbao860.plugin.contraband.object;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CShopItem {

    private final ItemStack is;
    private final int type;
    private final int price;
    private final List<String> commands;
    private final String name;

    //0 = rare, 1 = normal
    public CShopItem(ItemStack is, String name, int type, int price, List<String> commands) {
        this.is = is;
        this.name = name;
        this.type = type;
        this.price = price;
        this.commands = commands;

    }

    public ItemStack getItemStack() {
        return is;
    }

    public int getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getCommands() {
        return commands;
    }

    public String getName() {
        return name;
    }
}
