package me.yorick.withdrawexp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class WithdrawExp implements CommandExecutor {

    private Plugin plugin;

    public WithdrawExp(){
        plugin = Main.getPlugin(Main.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("withdrawexp")){

            if(args.length == 0) {
                List<String> messages = plugin.getConfig().getStringList("messages.help");
                for(String m : messages) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
                }
                return false;
            }

            // /exp give ClickTheLantern 5
            if(args.length == 3) {
                // Send Error Because User is not a Server
                if(sender instanceof Player){
                    String prefix = plugin.getConfig().getString("prefix");
                    String error = plugin.getConfig().getString("messages.not-server");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + " " + error));
                    return false;
                }
                // Create Bottle From Server
                try{

                    int toWithdraw = Integer.parseInt(args[2]);

                    Player receiver = plugin.getServer().getPlayer(args[1]);

                    // Create ItemStack and add Meta data
                    ItemStack expBottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
                    ItemMeta meta = expBottle.getItemMeta();

                    // Get Displayname and other settings from config
                    String itemName = plugin.getConfig().getString("expitem.name");
                    List<String> loreText = plugin.getConfig().getStringList("expitem.lore");


                    // Use Settings to add Meta data to ItemStack
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemName));

                    ArrayList<String> Lore = new ArrayList<String>();
                    for(String l : loreText) {
                        String loreItem = l.replace("%LEVELS%", Integer.toString(toWithdraw)).replace("%CREATOR%", sender.getName());
                        Lore.add(ChatColor.translateAlternateColorCodes('&', loreItem));
                    }

                    meta.setLore(Lore);
                    expBottle.setItemMeta(meta);

                    try {
                        // Give ItemStack to Player
                        receiver.getInventory().addItem(expBottle);
                    }catch(Exception e){

                        // Send Not a Player Error
                        String prefix = plugin.getConfig().getString("prefix");
                        String error = plugin.getConfig().getString("messages.not-a-player");
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + error));
                        return false;
                    }



                    // Send Success Message to command sender
                    String prefix = plugin.getConfig().getString("prefix");
                    String successMessage = plugin.getConfig().getString("messages.exp-withdraw").replace("%LEVELS%", Integer.toString(toWithdraw));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + successMessage));

                    // Send Success Message to receiver
                    String successMessageServer = plugin.getConfig().getString("messages.exp-from-server").replace("%LEVELS%", Integer.toString(toWithdraw));
                    receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + successMessageServer));

                    return true;


                }catch(NumberFormatException e){
                    // The amount you want to withdraw is not a number.
                    String prefix = plugin.getConfig().getString("prefix");
                    String error = plugin.getConfig().getString("messages.not-a-number");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + error));
                    return false;
                }
            }

            // /exp withdraw 5
            if(args.length == 2) {
                // Withdraw EXP from User
                    try{

                        int toWithdraw = Integer.parseInt(args[1]);

                        Player p = (Player) sender;
                        int level = p.getLevel();

                        // The Amount you want to withdraw is higher than the exp levels you have.
                        if (toWithdraw > level) {
                            String prefix = plugin.getConfig().getString("prefix");
                            String error = plugin.getConfig().getString("messages.not-enough-exp");
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + error));
                            return false;
                        }

                        // Create ItemStack and add Meta data
                        ItemStack expBottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
                        ItemMeta meta = expBottle.getItemMeta();

                        // Get Displayname and other settings from config
                        String itemName = plugin.getConfig().getString("expitem.name");
                        List<String> loreText = plugin.getConfig().getStringList("expitem.lore");


                        // Use Settings to add Meta data to ItemStack
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemName));

                        ArrayList<String> Lore = new ArrayList<String>();
                        for(String l : loreText) {
                            String loreItem = l.replace("%LEVELS%", Integer.toString(toWithdraw)).replace("%CREATOR%", sender.getName());
                            Lore.add(ChatColor.translateAlternateColorCodes('&', loreItem));
                        }

                        meta.setLore(Lore);
                        expBottle.setItemMeta(meta);

                        try{
                            // Give ItemStack to Player
                            ((Player) sender).getInventory().addItem(expBottle);

                            // Calculate New EXP level value & Remove from player
                            int newLevel = level - toWithdraw;
                            ((Player) sender).setLevel(newLevel);

                        }catch (Exception e) {
                            // Send Not a Player Error
                            String prefix = plugin.getConfig().getString("prefix");
                            String error = plugin.getConfig().getString("messages.not-a-player");
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + error));
                        }


                        // Send Success Message
                        String prefix = plugin.getConfig().getString("prefix");
                        String successMessage = plugin.getConfig().getString("messages.exp-withdraw").replace("%LEVELS%", Integer.toString(toWithdraw));
                        ((Player) sender).sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + successMessage));

                        return true;


                    }catch(NumberFormatException e){
                        // The amount you want to withdraw is not a number.
                        String prefix = plugin.getConfig().getString("prefix");
                        String error = plugin.getConfig().getString("messages.not-a-number");
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + error));
                        return false;
                    }


            }
            List<String> messages = plugin.getConfig().getStringList("messages.help");
            for(String m : messages) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
            }
            return false;


        }



        return false;
    }
}
