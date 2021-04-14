package com.gxbriiel.gxwarps.menus;

import com.gxbriiel.gxwarps.utils.ConfigManager;
import com.gxbriiel.gxwarps.utils.Heads;
import com.gxbriiel.gxwarps.utils.ItemCreator;
import com.gxbriiel.gxwarps.utils.Utils;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Data
public class WarpCreator {

    public static ConfigManager warps = new ConfigManager("warps.yml");
    public static ConfigManager config = new ConfigManager("config.yml");
    public static ConfigManager messages = new ConfigManager("messages.yml");

    private String name = null;
    private String description = null;
    private ItemStack itemStack;
    private GameMode gamemode;
    private Location location;
    private boolean onNameChange, onDescriptionChange, onItemChange, onPermissionChange;
    public static Map<Player, WarpCreator> creationMap = new HashMap<>();

    public WarpCreator(Player player) {
        creationMap.put(player, this);
        gamemode = player.getGameMode();
        location = player.getLocation();
    }

    public void open(Player player) {

        String setwarp_menu_name = "§8WARP CREATOR";
        if(Utils.getConfigInfo("setwarp_menu_name") != null) setwarp_menu_name = Utils.getConfigInfo("setwarp_menu_name");
        Inventory inventory = Bukkit.createInventory(null, 9*5, setwarp_menu_name);

        inventory.setItem(4, ItemCreator.createCustomHead(
                Heads.getHeadId(Heads.questionhead), "§6Information", 1, new String[]{
                        "§7§lWORLD: §f" + player.getWorld().getName(),
                        "§7§lLOCATION: §f" + getLocation(player)
                })
        );

        //Name
        if(name == null) {
            inventory.setItem(20, ItemCreator.createCustomHead(
                    Heads.getHeadId(Heads.redhead), "§cName", 1, new String[]{"", "§aClick here to set the §nname§a of the warp", ""})
            );
        } else {
            inventory.setItem(20, ItemCreator.createCustomHead(
                    Heads.getHeadId(Heads.greenhead), "§aName", 1, new String[]{"", "§aYou have set the name of the warp as §n" + name, ""})
            );
        }

        //Description
        if(description == null) {
            inventory.setItem(22, ItemCreator.createCustomHead(
                    Heads.getHeadId(Heads.redhead), "§cDescription", 1, new String[]{"", "§aClick here to set the §ndescription§a of the warp", ""})
            );
        } else {
            inventory.setItem(22, ItemCreator.createCustomHead(
                    Heads.getHeadId(Heads.greenhead), "§aDescription", 1, new String[]{"", "§aYou have set the description of the warp as:", "", description})
            );
        }

        //Item
        if(itemStack == null) {
            inventory.setItem(24, ItemCreator.createCustomHead(
                    Heads.getHeadId(Heads.redhead), "§cItem", 1, new String[]{"", "§aClick here to set the §nitem§a of the warp", ""})
            );
        } else {
            inventory.setItem(24, ItemCreator.createCustomHead(
                    Heads.getHeadId(Heads.greenhead), "§aItem", 1, new String[]{"", "§aYou have set the item of the warp as §n" + itemStack.getType().toString(), ""})
            );
        }

        //Creation
        if((name != null) && (description != null) && itemStack != null) {
            inventory.setItem(42, ItemCreator.createItem(Material.WOOL, "§c§lCANCEL", 1, (short) 14, new String[]{}));
            inventory.setItem(43, ItemCreator.createItem(Material.WOOL, "§a§lCREATE", 1, (short) 5, new String[]{}));
            inventory.setItem(44, ItemCreator.createItem(itemStack.getType(), name, itemStack.getAmount(), itemStack.getDurability(), new String[]{"", description}));
        } else {
            inventory.setItem(44, ItemCreator.createItem(Material.WOOL, "§c§lCANCEL", 1, (short) 14, new String[]{}));
        }

        player.openInventory(inventory);
    }

    public static WarpCreator getWarpCreator(Player player) { return creationMap.get(player); }

    private static String getLocation(Player player) {

        DecimalFormat decimalFormat = new DecimalFormat("#");

        return decimalFormat.format(player.getLocation().getX()) + ", " +
                decimalFormat.format(player.getLocation().getY()) + ", " +
                decimalFormat.format(player.getLocation().getZ());
    }

}
