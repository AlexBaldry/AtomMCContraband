package me.thienbao860.plugin.contraband.config;

import me.thienbao860.plugin.contraband.AtomMcContraband;
import me.thienbao860.plugin.contraband.object.CPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CConfig {

    private final AtomMcContraband plugin;
    private File genFile, playerFile, cacheFile;
    private FileConfiguration shopFile, playerConf, cacheConf;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public CConfig(AtomMcContraband plugin) {
        this.plugin = plugin;
        try {
            this.genFile = new File(plugin.getDataFolder(), "shop.yml");
            this.playerFile = new File(plugin.getDataFolder(), "player.yml");
            this.cacheFile = new File(plugin.getDataFolder(), "cache.yml");
            if (!this.genFile.exists()) {
                this.genFile.createNewFile();
            }
            if (!this.playerFile.exists()) {
                this.playerFile.createNewFile();
            }
            if (!this.cacheFile.exists()) {
                this.cacheFile.createNewFile();
            }
            this.playerConf = YamlConfiguration.loadConfiguration(playerFile);
            this.shopFile = YamlConfiguration.loadConfiguration(genFile);
            this.cacheConf = YamlConfiguration.loadConfiguration(cacheFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getShopConf() {
        return shopFile;
    }

    public FileConfiguration getPlayerConf() {
        return playerConf;
    }

    public FileConfiguration getCacheConf() {
        return cacheConf;
    }

    public void savePlayer() {
        try {
            for (CPlayer player : plugin.getPlayerManager().getPlayers()) {
                playerConf.set("players." + player.getUUID().toString() + ".contraband", player.getCont());
            }
            playerConf.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveShop() {
        try {
            shopFile.save(genFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveCache() {
        try {
            cacheConf.save(cacheFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
