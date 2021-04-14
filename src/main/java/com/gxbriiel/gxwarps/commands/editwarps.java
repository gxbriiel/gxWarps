package com.gxbriiel.gxwarps.commands;

import com.gxbriiel.gxwarps.menus.WarpCreator;
import com.gxbriiel.gxwarps.menus.WarpEditor;
import com.gxbriiel.gxwarps.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class editwarps implements CommandExecutor {

    public static WarpEditor warpEditor;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            String is_console_error = "§cThis command is available only for players.";
            if(Utils.getMessage("is_console") != null) is_console_error = Utils.getMessage("is_console");
            sender.sendMessage(is_console_error);
            return true;
        }

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("editwarps")) {
            if(player.hasPermission("gxwarps.editwarp")) {
                if(WarpCreator.creationMap.containsKey(player)) {
                    player.sendMessage("§cYou have an open creation menu.");
                    return true;
                }
                if(WarpEditor.getWarpEditor(player) == null || !warpEditor.isOnEdit()) {
                    warpEditor = new WarpEditor(player);
                } else {
                    warpEditor = WarpEditor.getWarpEditor(player);
                    warpEditor.openEditor(player, warpEditor.getCurrentEdition());
                    return true;
                }
                WarpEditor.openSelector(player);
            } else {
                String no_permission_error = "§cYou don't have permission to use this command.";
                if(Utils.getMessage("no_permission") != null) no_permission_error = Utils.getMessage("no_permission");
                player.sendMessage(no_permission_error);
            }
        }

        return false;
    }

}
