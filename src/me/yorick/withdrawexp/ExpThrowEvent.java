package me.yorick.withdrawexp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.List;

public class ExpThrowEvent implements Listener {

    Plugin plugin;

    public ExpThrowEvent(){
        plugin = Main.getPlugin(Main.class);
    }

    @EventHandler
    public void onThrow(PlayerInteractEvent e) throws IOException {
        Player p = e.getPlayer();

        // Check if item in hand is EXP Bottle
        ItemStack itemInHand = p.getInventory().getItemInMainHand();
        if (itemInHand.getType() == Material.EXPERIENCE_BOTTLE) {

            List<String> itemInHandLore = itemInHand.getItemMeta().getLore();

            // Check if Item has a lore
            if(itemInHandLore != null) {
                e.setCancelled(true);

                // Get amount of levels to redeem
                String loreLevelLine = itemInHandLore.get(0);
                String amountString = loreLevelLine.split(" ")[1];
                String cleanString = amountString.substring(2);
                int amountINT = Integer.parseInt(cleanString);

                // Remove one item from hand
                ItemStack hand = p.getInventory().getItemInMainHand();
                hand.setAmount(hand.getAmount() - 1);
                p.getInventory().setItemInMainHand(hand);

                String prefix = plugin.getConfig().getString("prefix");
                String successMessage = plugin.getConfig().getString("messages.exp-redeem").replace("%LEVELS%", cleanString);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + successMessage));


                // Add EXP levels to player
                int currLevel = p.getLevel();
                int newLevel = currLevel + amountINT;
                p.setLevel(newLevel);
            }



        }

    }
}
