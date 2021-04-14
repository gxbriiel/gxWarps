package com.gxbriiel.gxwarps.menus;

import com.gxbriiel.gxwarps.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WarpsMenu {

    public static void open(Player player) {

        String warp_menu_name = "§8WARPS";
        if(Utils.getConfigInfo("warp_menu_name") != null) warp_menu_name = Utils.getConfigInfo("warp_menu_name");
        Inventory inventory = Bukkit.createInventory(null, 9*5, warp_menu_name);
        ConfigurationSection warps = WarpCreator.warps.getConfig().getConfigurationSection("warps");
        if(warps.getKeys(true).size() == 0) {
            String no_warps_error = "§cThere isn't any warp defined yet!";
            if(Utils.getMessage("no_warps") != null) no_warps_error = Utils.getMessage("no_warps");
            player.sendMessage(no_warps_error);
            return;
        }
        Set<String> warpsKeys = WarpCreator.warps.getConfig().getConfigurationSection("warps").getKeys(false);

        int i = 10;
        for(String warp : warpsKeys) {
            Material material = Material.matchMaterial(warps.getString(warp + ".item"));

            ItemStack itemStack = new ItemStack(material, (Integer) warps.get(warp + ".item_amount"));
            if(Short.parseShort(warps.getString(warp + ".item_durability")) > 0) itemStack.setDurability(Short.parseShort(warps.getString(warp + ".item_durability")));
            List<String> lore = new ArrayList<>();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(warps.getString(warp + ".name").replace("&", "§"));
            lore.add("");
            lore.add(warps.getString(warp + ".description").replace("&", "§"));
            lore.add("");
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(i, itemStack);
            if(i == 16 || i == 25 || i == 34) {
                if(i == 34) {
                    System.out.println("[WARNING] The number of warps exceeded the limit!");
                    break;
                }
                i += 3;
            } else {
                i++;
            }
        }

        player.openInventory(inventory);

    }

}
