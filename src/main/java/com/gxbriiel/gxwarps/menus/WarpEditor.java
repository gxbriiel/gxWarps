package com.gxbriiel.gxwarps.menus;

import com.gxbriiel.gxwarps.events.InventoryClick;
import com.gxbriiel.gxwarps.utils.ConfigManager;
import com.gxbriiel.gxwarps.utils.Heads;
import com.gxbriiel.gxwarps.utils.ItemCreator;
import com.gxbriiel.gxwarps.utils.Utils;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class WarpEditor {

    public static ConfigManager warps = new ConfigManager("warps.yml");
    public static ConfigManager config = new ConfigManager("config.yml");
    public static ConfigManager messages = new ConfigManager("messages.yml");

    private String name = null;
    private String oldName = null;
    private String description = null;
    private ItemStack itemStack;
    private GameMode gamemode;
    private Location location;
    private boolean onNameChange, onDescriptionChange, onItemChange, onLocationChange, onEdit;
    public static Map<Player, WarpEditor> editorMap = new HashMap<>();
    private String currentEdition;

    public WarpEditor(Player player) {
        editorMap.put(player, this);
        gamemode = player.getGameMode();
    }

    public static void openSelector(Player player) {

        String warp_selector_menu_name = "§8SELECT A WARP";
        if(Utils.getConfigInfo("warp_selector_menu_name") != null) warp_selector_menu_name = Utils.getConfigInfo("warp_selector_menu_name");
        Inventory inventory = Bukkit.createInventory(null, 9*5, warp_selector_menu_name);
        ConfigurationSection warps = WarpCreator.warps.getConfig().getConfigurationSection("warps");
        if(warps == null) {
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
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(warps.getString(warp + ".name").replace("&", "§"));
            itemMeta.setLore(Arrays.asList("", warps.getString(warp + ".description").replace("&", "§")));
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

    public void openEditor(Player player, String configSectionName) {

        ConfigurationSection warps = WarpCreator.warps.getConfig().getConfigurationSection("warps");

        System.out.println("Nome: " + name);
        if(name == null) name = warps.getString(configSectionName + ".name").replace("&", "§");
        oldName = warps.getString(configSectionName + ".name").replace("&", "§");
        if(description == null) description = warps.getString(configSectionName + ".description").replace("&", "§");
        if(itemStack == null) {
            itemStack = new ItemStack(Material.matchMaterial(warps.getString(configSectionName + ".item")), (Integer) warps.get(configSectionName + ".item_amount"));
            if(Integer.parseInt(warps.getString(configSectionName + ".item_durability")) > 0)
                itemStack.setDurability(Short.parseShort(warps.getString(configSectionName + ".item_durability")));
        }
        if(location == null) location = InventoryClick.getLocation(configSectionName);
        if(currentEdition == null) currentEdition = configSectionName;

        String warp_editor_menu_name = "§8WARP EDITOR";
        if(Utils.getConfigInfo("warp_editor_menu_name") != null) warp_editor_menu_name = Utils.getConfigInfo("warp_editor_menu_name");
        Inventory inventory = Bukkit.createInventory(null, 9*5, warp_editor_menu_name);

        inventory.setItem(4, ItemCreator.createCustomHead(
                Heads.getHeadId(Heads.questionhead), "§aLocation", 1, new String[]{
                        "§a§lWORLD: §f" + player.getWorld().getName(),
                        "§a§lLOCATION: §f" + getLocation(player),
                        "",
                        "§aClick to change the location!"
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
        if((name != null) && (description != null) && itemStack != null && location != null) {
            inventory.setItem(41, ItemCreator.createItem(Material.BARRIER, "§c§lREMOVE WARP", 1, null, new String[]{}));
            inventory.setItem(42, ItemCreator.createItem(Material.WOOL, "§c§lCANCEL", 1, (short) 14, new String[]{}));
            inventory.setItem(43, ItemCreator.createItem(Material.WOOL, "§a§lSET CHANGES", 1, (short) 5, new String[]{}));
            inventory.setItem(44, ItemCreator.createItem(itemStack.getType(), name, itemStack.getAmount(), itemStack.getDurability(), new String[]{"", description}));
        } else {
            inventory.setItem(44, ItemCreator.createItem(Material.WOOL, "§c§lCANCEL", 1, (short) 14, new String[]{}));
        }

        player.closeInventory();
        player.openInventory(inventory);
    }

    public static WarpEditor getWarpEditor(Player player) { return editorMap.get(player); }

    private static String getLocation(Player player) {

        DecimalFormat decimalFormat = new DecimalFormat("#");

        return decimalFormat.format(player.getLocation().getX()) + ", " +
                decimalFormat.format(player.getLocation().getY()) + ", " +
                decimalFormat.format(player.getLocation().getZ());
    }

}
