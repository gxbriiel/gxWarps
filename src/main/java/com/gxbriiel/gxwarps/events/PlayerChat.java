package com.gxbriiel.gxwarps.events;

import com.gxbriiel.gxwarps.menus.WarpCreator;
import com.gxbriiel.gxwarps.menus.WarpEditor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        WarpCreator warpCreator = null;
        WarpEditor warpEditor = null;
        if(WarpCreator.creationMap.containsKey(player)) warpCreator = WarpCreator.getWarpCreator(player);
        if(WarpEditor.editorMap.containsKey(player)) warpEditor = WarpEditor.getWarpEditor(player);

        if(warpCreator == null && warpEditor == null) return;

        if((warpCreator != null && warpCreator.isOnNameChange()) || (warpEditor != null && warpEditor.isOnNameChange())) {
            event.setCancelled(true);
            if(warpCreator != null) warpCreator.setOnNameChange(false);
            if(warpEditor != null) warpEditor.setOnNameChange(false);
            if(event.getMessage().equalsIgnoreCase("cancel")) {
                player.sendMessage("§cCancelled the current change.");
                if(warpCreator != null) warpCreator.open(player);
                if(warpEditor != null) warpEditor.openEditor(player, warpEditor.getCurrentEdition());
                return;
            }
            if(warpCreator != null) {
                warpCreator.setName(event.getMessage().replace("&", "§"));
                warpCreator.open(player);
            }
            if(warpEditor != null) {
                warpEditor.setName(event.getMessage().replace("&", "§"));
                warpEditor.openEditor(player, warpEditor.getCurrentEdition());
            }
            return;
        }

        if((warpCreator != null && warpCreator.isOnDescriptionChange()) || (warpEditor != null && warpEditor.isOnDescriptionChange())) {
            event.setCancelled(true);
            if(warpCreator != null) warpCreator.setOnDescriptionChange(false);
            if(warpEditor != null) warpEditor.setOnDescriptionChange(false);
            if(event.getMessage().equalsIgnoreCase("cancel")) {
                player.sendMessage("§cCancelled the current change.");
                if(warpCreator != null) warpCreator.open(player);
                if(warpEditor != null) warpEditor.openEditor(player, warpEditor.getCurrentEdition());
                return;
            }
            if(warpCreator != null) {
                warpCreator.setDescription(event.getMessage().replace("&", "§"));
                warpCreator.open(player);
            }
            if(warpEditor != null) {
                warpEditor.setDescription(event.getMessage().replace("&", "§"));
                warpEditor.openEditor(player, warpEditor.getCurrentEdition());
            }
            return;
        }

        if((warpEditor != null && warpEditor.isOnLocationChange())) {
            event.setCancelled(true);
            if(warpEditor != null) warpEditor.setOnLocationChange(false);
            if(event.getMessage().equalsIgnoreCase("cancel")) {
                player.sendMessage("§cCancelled the current change.");
                if(warpEditor != null) warpEditor.openEditor(player, warpEditor.getCurrentEdition());
                return;
            } else if(event.getMessage().equalsIgnoreCase("here")) {
                if(warpEditor != null)  {
                    warpEditor.setLocation(player.getLocation());
                    warpEditor.openEditor(player, warpEditor.getCurrentEdition());
                }
            }
        }
    }

}
