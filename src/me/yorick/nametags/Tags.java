package me.yorick.nametags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Tags implements CommandExecutor {

    private Plugin plugin;
    public static ConfigManager cfmg;


    public Tags() {
        plugin = Main.getPlugin(Main.class);
        cfmg = new ConfigManager((Main) plugin);
    }

    public String formatChat(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            if(cmd.getName().equalsIgnoreCase("tags")){

                if(args.length > 0) {
                    if(args[0].equalsIgnoreCase("help")) {
                        sender.sendMessage(formatChat(plugin.getConfig().getString("help.title")));
                        sender.sendMessage(formatChat(plugin.getConfig().getString("help.menu")));
                        sender.sendMessage(formatChat(plugin.getConfig().getString("help.clear")));
                        sender.sendMessage(formatChat(plugin.getConfig().getString("help.help")));
                        sender.sendMessage(formatChat(plugin.getConfig().getString("help.reload")));
                        sender.sendMessage(formatChat(plugin.getConfig().getString("help.title")));
                        return  true;
                    }

                    if(args[0].equalsIgnoreCase("reload")) {

                        try {
                            plugin.reloadConfig();
                            cfmg.reloadConfig();
                            sender.sendMessage(formatChat(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("messages.reload")));
                        } catch (Exception e) {
                            sender.sendMessage(formatChat(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("messages.error")));
                        }

                        return true;
                    }

                    if(args[0].equalsIgnoreCase("clear")) {


                            Player p = (Player) sender;

                            YamlConfiguration c = new YamlConfiguration();
                            c.set("active-tag", "none");
                            try {
                                c.save(new File(plugin.getDataFolder() + "/playerdata", p.getUniqueId() + ".yml"));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            sender.sendMessage(formatChat(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("messages.clear-tag")));


                        return true;
                    }

                    return false;
                }

                Inventory tagMENU = Bukkit.createInventory(null, 27, formatChat(plugin.getConfig().getString("menu.title")));
                //InventoryMethods.createGuiItem(Material.WORKBENCH, (byte) 0, plugin.getConfig().getString("inventories.add-trade"), plugin.getConfig().getString("inventories.lore-add"))

                // Getting Filling Items bottom row
                Material orange_glass_mat = Material.getMaterial(plugin.getConfig().getString("menu.glass-type-one"));
                Material yellow_glass_mat = Material.getMaterial(plugin.getConfig().getString("menu.glass-type-two"));
                Material empty_slot_mat = Material.getMaterial(plugin.getConfig().getString("menu.empty-slot"));
                Material close_item_mat = Material.getMaterial(plugin.getConfig().getString("menu.close-btn.item"));


                // Creating ItemStack from filler Materials
                ItemStack orange_glass = InventoryMethods.createGuiItem(orange_glass_mat, (byte) 0, " ");
                ItemStack yellow_glass = InventoryMethods.createGuiItem(yellow_glass_mat, (byte) 0, " ");
                ItemStack empty_slot = InventoryMethods.createGuiItem(empty_slot_mat, (byte) 0, " ");
                ItemStack close_item = InventoryMethods.createGuiItemWithLore(close_item_mat, (byte) 0, plugin.getConfig().getString("menu.close-btn.name"), plugin.getConfig().getStringList("menu.close-btn.lore"));

                // Adding Filler items to menu
                tagMENU.setItem(18, orange_glass);
                tagMENU.setItem(19, yellow_glass);
                tagMENU.setItem(20, orange_glass);
                tagMENU.setItem(21, yellow_glass);

                tagMENU.setItem(22, close_item);

                tagMENU.setItem(23, yellow_glass);
                tagMENU.setItem(24, orange_glass);
                tagMENU.setItem(25, yellow_glass);
                tagMENU.setItem(26, orange_glass);



                // Fetch tags out of tags.yml
                Set<String> tags = cfmg.getConfig().getConfigurationSection("tags").getKeys(false);

                // Get Tag material
                Material tag_mat = Material.getMaterial(plugin.getConfig().getString("menu.tag-item"));

                int counter = 0;
                for (String key : tags) {
                    counter++;
                    String display = cfmg.getConfig().getString("tags." + key + ".display");
                    String permission = cfmg.getConfig().getString("tags." + key + ".permission");

                    List<String> configLore = cfmg.getConfig().getStringList("tags." + key + ".lore");

                    List<String> lore = new ArrayList<String>();
                    for(String line : configLore) {
                        if(line.contains("{bool}")) {

                            if(sender.hasPermission(permission)) lore.add(line.replace("{bool}", "&a✔"));
                            else lore.add(line.replace("{bool}", "&c✘"));

                        }else{
                            lore.add(line);
                        }
                    }

                    if(counter <= 18) {
                        ItemStack tag = InventoryMethods.createGuiItemWithLore(tag_mat, (byte) 0, display, lore);
                        tagMENU.addItem(tag);
                    }

                }

                // Empty Tag slots
                for(int i = tags.size(); i < 18; i++){
                    tagMENU.setItem(i, empty_slot);
                }

                ((Player) sender).openInventory(tagMENU);


            }
        }
        return false;
    }
}
