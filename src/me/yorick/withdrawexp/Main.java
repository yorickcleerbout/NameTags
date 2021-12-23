package me.yorick.withdrawexp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    Plugin plugin;

    @Override
    public void onEnable() {


        Bukkit.getPluginManager().registerEvents(new ExpThrowEvent(), this);

        getCommand("withdrawexp").setExecutor(new WithdrawExp());
        loadConfig();

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m========[&6&lWithdraw&e&lExp&8&l]&m========"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Status: &aEnabled"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Author: &eDiscord -> Yorick#1907"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Version: &e1.0.0"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m========[&6&lWithdraw&e&lExp&8&l]&m========"));



    }

    @Override
    public void onDisable() {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m========[&6&lWithdraw&e&lExp&8&l]&m========"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Status: &cDisabled"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Author: &eDiscord -> Yorick#1907"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l> Version: &e1.0.0"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m========[&6&lWithdraw&e&lExp&8&l]&m========"));
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
