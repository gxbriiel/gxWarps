package com.gxbriiel.gxwarps;

import com.gxbriiel.gxwarps.commands.editwarps;
import com.gxbriiel.gxwarps.commands.setwarp;
import com.gxbriiel.gxwarps.commands.warps;
import com.gxbriiel.gxwarps.events.InventoryClick;
import com.gxbriiel.gxwarps.events.PlayerChat;
import com.gxbriiel.gxwarps.events.PlayerInteract;
import com.gxbriiel.gxwarps.menus.WarpCreator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Warps extends JavaPlugin {

    @Override
    public void onEnable() {

        if(WarpCreator.warps.existConfig()) {
            WarpCreator.warps.saveDefaultConfig();
        }
        if(WarpCreator.config.existConfig()) {
            WarpCreator.config.saveDefaultConfig();
        }
        if(WarpCreator.messages.existConfig()) {
            WarpCreator.messages.saveDefaultConfig();
        }

        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {

    }

    public static Warps getPlugin() { return Warps.getPlugin(Warps.class); }

    public static void registerCommands() {
        //Register Commands in plugin
        Warps.getPlugin().getCommand("setwarp").setExecutor(new setwarp());
        Warps.getPlugin().getCommand("warps").setExecutor(new warps());
        Warps.getPlugin().getCommand("editwarps").setExecutor(new editwarps());

        WarpCreator.warps.saveDefaultConfig();
        WarpCreator.config.saveDefaultConfig();
        WarpCreator.messages.saveDefaultConfig();

    }

    public static void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Warps plugin = getPlugin();

        //Register Events in PluginManager
        pluginManager.registerEvents(new InventoryClick(), plugin);
        pluginManager.registerEvents(new PlayerChat(), plugin);
        pluginManager.registerEvents(new PlayerInteract(), plugin);
    }

}
