package me.yorick.nametags;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class InventoryClick implements Listener  {

    private Plugin plugin;
    public static ConfigManager cfmg;

    public InventoryClick() {
        plugin = Main.getPlugin(Main.class);
        cfmg = new ConfigManager((Main) plugin);

    }

    public String formatChat(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e) {
        if(e.getView().getTitle().equalsIgnoreCase(formatChat(plugin.getConfig().getString("menu.title")))){

            Player p = (Player) e.getWhoClicked();
            ItemStack clickItem = e.getCurrentItem();

            if (clickItem != null) {
                if(clickItem.getType() == Material.getMaterial(plugin.getConfig().getString("menu.close-btn.item"))) {
                    p.closeInventory();
                }

                if(clickItem.getType() == Material.getMaterial(plugin.getConfig().getString("menu.tag-item"))) {
                    String selectedTag = clickItem.getItemMeta().getDisplayName();

                    // Get matching tag out of tags.yml
                    Set<String> tags = cfmg.getConfig().getConfigurationSection("tags").getKeys(false);
                    for (String key : tags) {
                        String display = cfmg.getConfig().getString("tags." + key + ".display");
                        String formatted = formatChat(display);
                        if(ChatColor.stripColor(selectedTag).equalsIgnoreCase(ChatColor.stripColor(formatted))) {
                            String permission = cfmg.getConfig().getString("tags." + key + ".permission");

                            if(p.hasPermission(permission)) {
                                p.sendMessage(formatChat(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("messages.select-tag").replace("{tag}", selectedTag)));

                                YamlConfiguration c = new YamlConfiguration();
                                c.set("active-tag", key);
                                try {
                                    c.save(new File(plugin.getDataFolder() + "/playerdata", p.getUniqueId() + ".yml"));
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }


                            }else{
                                p.sendMessage(formatChat(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("messages.no-perm")));
                            }
                        }

                    }


                }
            }



            e.setCancelled(true);
        }
    }
}
