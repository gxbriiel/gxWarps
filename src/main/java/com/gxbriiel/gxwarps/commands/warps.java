package com.gxbriiel.gxwarps.commands;

import com.gxbriiel.gxwarps.menus.WarpsMenu;
import com.gxbriiel.gxwarps.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class warps implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            String is_console_error = "§cThis command is available only for players.";
            if(Utils.getMessage("is_console") != null) is_console_error = Utils.getMessage("is_console");
            sender.sendMessage(is_console_error);
            return true;
        }

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("warps")) {
            if(player.hasPermission("gxwarps.acess")) {
                WarpsMenu.open(player);
            } else {
                String no_permission_error = "§cYou don't have permission to use this command.";
                if(Utils.getMessage("no_permission") != null) no_permission_error = Utils.getMessage("no_permission");
                player.sendMessage(no_permission_error);
            }
        }

        return false;
    }
}
