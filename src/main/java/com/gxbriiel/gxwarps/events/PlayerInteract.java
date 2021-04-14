package com.gxbriiel.gxwarps.events;

import com.gxbriiel.gxwarps.menus.WarpCreator;
import com.gxbriiel.gxwarps.menus.WarpEditor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(
                (WarpCreator.getWarpCreator(event.getPlayer()) != null && WarpCreator.getWarpCreator(event.getPlayer()).isOnItemChange()) ||
                (WarpEditor.getWarpEditor(event.getPlayer()) != null && WarpEditor.getWarpEditor(event.getPlayer()).isOnItemChange())
        ) {
            Player player = event.getPlayer();
            ItemStack itemStack = event.getItem();

            WarpCreator warpCreator = null;
            WarpEditor warpEditor = null;
            if(WarpCreator.creationMap.containsKey(player)) warpCreator = WarpCreator.getWarpCreator(player);
            if(WarpEditor.editorMap.containsKey(player)) warpEditor = WarpEditor.getWarpEditor(player);

            event.setCancelled(true);

            if(itemStack != null) {
                if(warpCreator != null) warpCreator.setItemStack(itemStack);
                if(warpEditor != null) warpEditor.setItemStack(itemStack);
            } else {
                player.sendMessage("§cCancelled the current change.");
            }
            player.setItemInHand(null);
            if(warpCreator != null) {
                System.out.println("eae 2");
                warpCreator.setOnItemChange(false);
                warpCreator.open(player);
                player.setGameMode(warpCreator.getGamemode());
            }
            if(warpEditor != null) {
                System.out.println("eae");
                warpEditor.setOnItemChange(false);
                warpEditor.openEditor(player, warpEditor.getCurrentEdition());
                player.setGameMode(warpEditor.getGamemode());
            }
        }
    }

}
