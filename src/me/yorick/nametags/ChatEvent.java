package me.yorick.nametags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;

import static org.bukkit.Bukkit.getServer;

public class ChatEvent implements Listener {

    private Plugin plugin;
    public static ConfigManager cfmg;

    public static Chat chat = null;

    public ChatEvent() {
        plugin = Main.getPlugin(Main.class);
        cfmg = new ConfigManager((Main) plugin);

        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

    }

    public String formatChat(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        try {
            Player p = e.getPlayer();
            String msg = e.getMessage();

            String prefix = chat.getPlayerPrefix(p);

            YamlConfiguration c = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder()+ "/playerdata", p.getUniqueId()+".yml"));
            String tagKey = c.get("active-tag").toString();

            if(!tagKey.equalsIgnoreCase("none")) {
                String tag = cfmg.getConfig().getString("tags." + tagKey + ".display");

                String chat_delimiter = plugin.getConfig().getString("chat-delimiter");
                String chat_color = plugin.getConfig().getString("chat-color");
                String player_color = plugin.getConfig().getString("player-color");

                Bukkit.broadcastMessage(formatChat(prefix + tag + " " + player_color + p.getName() + chat_delimiter + " " + chat_color + msg));
                e.setCancelled(true);
            }


        } catch (Exception er) {

        }

    }

}
