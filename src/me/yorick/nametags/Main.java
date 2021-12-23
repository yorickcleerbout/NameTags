package me.yorick.nametags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    Plugin plugin;
    public static ConfigManager cfmg;

    @Override
    public void onEnable() {

        cfmg = new ConfigManager(this);

        getCommand("tags").setExecutor(new Tags());
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        loadConfig();

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m========[&6&lName&e&lTags&8&l]&m========"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Status: &aEnabled"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Author: &eDiscord -> Yorick#1907"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Version: &e1.0.0"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m========[&6&lName&e&lTags&8&l]&m========"));

    }

    @Override
    public void onDisable() {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m========[&6&lName&e&lTags&8&l]&m========"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Status: &cDisabled"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Author: &eDiscord -> Yorick#1907"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Version: &e1.0.0"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m========[&6&lName&e&lTags&8&l]&m========"));
    }



    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();

    }
}
