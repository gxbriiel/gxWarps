package com.gxbriiel.gxwarps.commands;

import com.gxbriiel.gxwarps.menus.WarpCreator;
import com.gxbriiel.gxwarps.menus.WarpEditor;
import com.gxbriiel.gxwarps.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setwarp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            String is_console_error = "§cThis command is available only for players.";
            if(Utils.getMessage("is_console") != null) is_console_error = Utils.getMessage("is_console");
            sender.sendMessage(is_console_error);
            return true;
        }

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("setwarp")) {
            if(player.hasPermission("gxwarps.setwarp")) {
                if(WarpEditor.editorMap.containsKey(player)) {
                    player.sendMessage("§cYou have an open edition menu.");
                    return true;
                }
                WarpCreator warpCreator;
                if(WarpCreator.getWarpCreator(player) == null) {
                    warpCreator = new WarpCreator(player);
                } else {
                    warpCreator = WarpCreator.getWarpCreator(player);
                    if(!(warpCreator.isOnNameChange() || warpCreator.isOnDescriptionChange() || warpCreator.isOnItemChange() || warpCreator.isOnPermissionChange())) {
                        warpCreator.setLocation(player.getLocation());
                    }
                }
                warpCreator.open(player);
            } else {
                String no_permission_error = "§cYou don't have permission to use this command.";
                if(Utils.getMessage("no_permission") != null) no_permission_error = Utils.getMessage("no_permission");
                player.sendMessage(no_permission_error);
            }
        }

        return false;
    }
}
