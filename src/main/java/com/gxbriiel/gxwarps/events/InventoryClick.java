package com.gxbriiel.gxwarps.events;

import com.gxbriiel.gxwarps.Warps;
import com.gxbriiel.gxwarps.menus.WarpCreator;
import com.gxbriiel.gxwarps.menus.WarpEditor;
import com.gxbriiel.gxwarps.utils.Utils;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InventoryClick implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {

        String setwarp_menu_name = "§8WARP CREATOR";
        if(Utils.getConfigInfo("setwarp_menu_name") != null) setwarp_menu_name = Utils.getConfigInfo("setwarp_menu_name");
        String warp_menu_name = "§8WARPS";
        if(Utils.getConfigInfo("warp_menu_name") != null) warp_menu_name = Utils.getConfigInfo("warp_menu_name");
        String warp_selector_menu_name = "§8SELECT A WARP";
        if(Utils.getConfigInfo("warp_selector_menu_name") != null) warp_selector_menu_name = Utils.getConfigInfo("warp_selector_menu_name");
        String warp_editor_menu_name = "§8WARP EDITOR";
        if(Utils.getConfigInfo("warp_editor_menu_name") != null) warp_editor_menu_name = Utils.getConfigInfo("warp_editor_menu_name");

        Player player = (Player) event.getWhoClicked();
        ItemStack itemClicked = event.getCurrentItem();

        if(itemClicked != null && itemClicked.getType() != Material.AIR && event.getInventory() != null) {
            if(event.getInventory().getName().equalsIgnoreCase(setwarp_menu_name) || event.getInventory().getName().equalsIgnoreCase(warp_editor_menu_name)) {
                event.setCancelled(true);

                WarpCreator warpCreator = null;
                WarpEditor warpEditor = null;
                if(WarpCreator.creationMap.containsKey(player)) warpCreator = WarpCreator.getWarpCreator(player);
                if(WarpEditor.editorMap.containsKey(player)) warpEditor = WarpEditor.getWarpEditor(player);

                switch (Utils.removeColor(itemClicked.getItemMeta().getDisplayName())) {
                    case "Name":
                        player.sendMessage("§aYou have selected the option §lNAME§a!");
                        player.sendMessage("§7Please type in the chat the §nname of the warp§7 or type §lCANCEL §7to cancel the current change.");
                        player.closeInventory();
                        if(warpCreator != null) warpCreator.setOnNameChange(true);
                        if(warpEditor != null) warpEditor.setOnNameChange(true);
                        break;
                    case "Description":
                        player.sendMessage("§aYou have selected the option §lDESCRIPTION§a!");
                        player.sendMessage("§7Please type in the chat the §ndescription of the warp§7 or type §lCANCEL §7to cancel the current change.");
                        player.closeInventory();
                        if(warpCreator != null) warpCreator.setOnDescriptionChange(true);
                        if(warpEditor != null) warpEditor.setOnDescriptionChange(true);
                        break;
                    case "Item":
                        player.sendMessage("§aYou have selected the option §lITEM§a!");
                        player.sendMessage("§7Please §npick the item of the warp and right-click§7 or click without any items to cancel the current change.");
                        player.setGameMode(GameMode.CREATIVE);
                        player.closeInventory();
                        if(warpCreator != null) warpCreator.setOnItemChange(true);
                        if(warpEditor != null) warpEditor.setOnItemChange(true);
                        break;
                    case "Location":
                        player.sendMessage("§aYou have selected the option §lLOCATION§a!");
                        player.sendMessage("§7Please type §lHERE §7in the chat the to change the location of the warp§7 or type §lCANCEL §7to cancel the current change.");
                        player.closeInventory();
                        if(warpEditor != null) warpEditor.setOnLocationChange(true);
                        break;
                    case "CANCEL":
                        if(warpCreator != null) {
                            warpCreator.setName(null);
                            warpCreator.setDescription(null);
                            warpCreator.setItemStack(null);
                            WarpCreator.creationMap.remove(player);
                        }
                        if(warpEditor != null) {
                            warpEditor.setName(null);
                            warpEditor.setDescription(null);
                            warpEditor.setItemStack(null);
                            WarpEditor.editorMap.remove(player);
                        }
                        player.closeInventory();
                        break;
                    case "CREATE":
                        ConfigurationSection warps = WarpCreator.warps.getConfig().getConfigurationSection("warps");
                        Set<String> warpsKeys = new HashSet<>();
                        if(warps != null) {
                            warpsKeys = warps.getKeys(false);
                        }
                        if(!warpsKeys.contains(warpCreator.getName())) {
                            player.sendMessage("§aYou have created the warp " + warpCreator.getName() + "§a!");
                            player.sendMessage("§7Check it using the command §e/warps §7or use the command §e/editwarp §7to edit the warps.");

                            String configSectionName = Utils.removeColor(warpCreator.getName())
                                    .replace(" ", "_")
                                    .replace(".", "_")
                                    .replace(",", "_")
                                    .toLowerCase();

                            WarpCreator.warps.set("warps." + configSectionName + ".name", warpCreator.getName().replace("§", "&"));
                            WarpCreator.warps.set("warps." + configSectionName + ".description", warpCreator.getDescription().replace("§", "&"));
                            WarpCreator.warps.set("warps." + configSectionName + ".item", warpCreator.getItemStack().getType().toString());
                            WarpCreator.warps.set("warps." + configSectionName + ".item_amount", warpCreator.getItemStack().getAmount());
                            WarpCreator.warps.setString("warps." + configSectionName + ".item_durability", String.valueOf(warpCreator.getItemStack().getDurability()));
                            WarpCreator.warps.saveConfig();
                            setLocation(warpCreator.getLocation(), configSectionName);

                            warpCreator.setName(null);
                            warpCreator.setDescription(null);
                            warpCreator.setItemStack(null);
                            WarpCreator.creationMap.remove(player);
                            player.closeInventory();
                        } else {
                            player.sendMessage("§cA warp with this name already exists!");
                        }
                        break;
                    case "REMOVE WARP":
                        player.sendMessage("§aYou have deleted the warp " + warpEditor.getName());
                        warps = WarpCreator.warps.getConfig().getConfigurationSection("warps");
                        warps.set(warpEditor.getCurrentEdition(), null);
                        WarpCreator.warps.saveConfig();
                        warpEditor.setName(null);
                        warpEditor.setDescription(null);
                        warpEditor.setItemStack(null);
                        warpEditor.setLocation(null);
                        WarpEditor.editorMap.remove(player);
                        player.closeInventory();
                        break;
                    case "SET CHANGES":
                        warps = WarpCreator.warps.getConfig().getConfigurationSection("warps");
                        player.sendMessage("§aYou have edited the warp " + warpEditor.getName() + "§a!");
                        player.sendMessage("§7Check it using the command §e/warps §7or use the command §e/editwarp §7to edit the warps.");

                        String configSectionName = Utils.getConfigName(warpEditor.getName());
                        String oldSectionName = Utils.getConfigName(warpEditor.getOldName());

                        int i = 0;
                        boolean toRemove = false;
                        ArrayList<ConfigurationSection> toRenew = new ArrayList<>();

                        //Save and remove others warps
                        for(String paths : warps.getKeys(false)) {
                            if(!paths.equalsIgnoreCase(oldSectionName)) {
                                System.out.println("Found a path: " + paths);
                                if(toRemove) {
                                    System.out.println("Removing " + paths);
                                    toRenew.add(WarpCreator.warps.getConfig().getConfigurationSection("warps." + paths));
                                    WarpCreator.warps.set("warps." + paths, null);
                                }
                            } else {
                                //REMOVE AND SET NEW
                                System.out.println("Removing " + oldSectionName);
                                WarpCreator.warps.set("warps." + oldSectionName, null);
                                System.out.println("Adding " + configSectionName);
                                System.out.println(configSectionName);
                                WarpCreator.warps.set("warps." + configSectionName + ".name", warpEditor.getName().replace("§", "&"));
                                WarpCreator.warps.set("warps." + configSectionName + ".description", warpEditor.getDescription().replace("§", "&"));
                                WarpCreator.warps.set("warps." + configSectionName + ".item", warpEditor.getItemStack().getType().toString());
                                WarpCreator.warps.set("warps." + configSectionName + ".item_amount", warpEditor.getItemStack().getAmount());
                                WarpCreator.warps.setString("warps." + configSectionName + ".item_durability", String.valueOf(warpEditor.getItemStack().getDurability()));
                                WarpCreator.warps.saveConfig();
                                setLocation(warpEditor.getLocation(), configSectionName);
                                toRemove = true;
                            }
                        }
                        WarpCreator.warps.saveConfig();

                        //Set the other warps again
                        for(ConfigurationSection newSections : toRenew) {
                            System.out.println("Adding " + newSections.getName());
                            warps.createSection(newSections.getName(), newSections.getValues(false));
                        }
                        WarpCreator.warps.saveConfig();


                        warpEditor.setName(null);
                        warpEditor.setDescription(null);
                        warpEditor.setItemStack(null);
                        warpEditor.setLocation(null);
                        WarpEditor.editorMap.remove(player);
                        player.closeInventory();
                        break;
                }

            } else if(event.getInventory().getName().equalsIgnoreCase(warp_menu_name) || event.getInventory().getName().equalsIgnoreCase(warp_selector_menu_name)) {

                event.setCancelled(true);

                String configSectionName = Utils.removeColor(itemClicked.getItemMeta().getDisplayName())
                        .replace(" ", "_")
                        .replace(".", "_")
                        .replace(",", "_")
                        .toLowerCase();

                if(event.getInventory().getName().equalsIgnoreCase(warp_selector_menu_name)) {
                    player.closeInventory();
                    WarpEditor.getWarpEditor(player).setOnEdit(true);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Warps.getPlugin(), () -> WarpEditor.getWarpEditor(player).openEditor(player, configSectionName),  1L);
                } else {
                    String teleported_message = "§eYou have been teleported to " + itemClicked.getItemMeta().getDisplayName() + "§e!";
                    if(Utils.getMessage("teleported") != null) teleported_message = Utils.getMessage("teleported").replace("%warp%", itemClicked.getItemMeta().getDisplayName());
                    player.sendMessage(teleported_message);
                    player.teleport(getLocation(configSectionName));
                }
            }
        }

    }

    public static void setLocation(Location location, String name){

        WarpCreator.warps.set("warps." + name + ".x", Double.valueOf(location.getX()));
        WarpCreator.warps.set("warps." + name + ".y", Double.valueOf(location.getY()));
        WarpCreator.warps.set("warps." + name + ".z", Double.valueOf(location.getZ()));

        WarpCreator.warps.set("warps." + name + ".pitch", Float.valueOf(location.getPitch()));
        WarpCreator.warps.set("warps." + name + ".yaw", Float.valueOf(location.getYaw()));

        WarpCreator.warps.set("warps." + name + ".world", location.getWorld().getName());

        WarpCreator.warps.saveConfig();

    }

    public static Location getLocation(String name){
        double x = WarpCreator.warps.getDouble("warps." + name + ".x");
        double y = WarpCreator.warps.getDouble("warps." + name + ".y");
        double z = WarpCreator.warps.getDouble("warps." + name + ".z");
        long pitch = WarpCreator.warps.getLong("warps." + name + ".pitch");
        long yaw = WarpCreator.warps.getLong("warps." + name + ".yaw");
        World world = Bukkit.getWorld(WarpCreator.warps.getString("warps." + name + ".world"));

        return new Location(world, x, y, z, (float) yaw, (float) pitch);
    }

}
