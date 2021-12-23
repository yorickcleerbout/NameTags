package me.yorick.nametags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigManager {

    private Main plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;


    public ConfigManager(Main plugin){
        this.plugin = plugin;
        saveDefaultConfig();
    }


    public void reloadConfig(){
        if(this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), "tags.yml");
        }

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("tags.yml");
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }


    public FileConfiguration getConfig(){
        if(this.dataConfig == null){
            reloadConfig();
        }
        return this.dataConfig;
    }

    public void saveConfig(){
        if(this.dataConfig == null || this.configFile == null){
            return;
        }

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig(){
        if(this.configFile == null) this.configFile = new File(this.plugin.getDataFolder(), "tags.yml");

        if(!this.configFile.exists()){
            this.plugin.saveResource("tags.yml", false);
        }
    }
}
