package me.yorick.nametags;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class InventoryMethods {

    static public ItemStack createGuiItemWithLore(Material material, Byte ex, String name, List<String> lores) {
        ItemStack item = new ItemStack(material, 1, ex);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        ArrayList<String> metalore = new ArrayList<>();
        for (String lore: lores) {
            metalore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }




        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    static public ItemStack createGuiItem(Material material, Byte ex, String name) {
        ItemStack item = new ItemStack(material, 1, ex);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));


        item.setItemMeta(meta);
        return item;
    }
}
