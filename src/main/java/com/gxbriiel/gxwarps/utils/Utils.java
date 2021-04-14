package com.gxbriiel.gxwarps.utils;

import com.gxbriiel.gxwarps.menus.WarpCreator;

public class Utils {

    public static String removeColor(String text) {
        return text
                .replace("§0", "")
                .replace("§1", "")
                .replace("§2", "")
                .replace("§3", "")
                .replace("§4", "")
                .replace("§5", "")
                .replace("§6", "")
                .replace("§7", "")
                .replace("§8", "")
                .replace("§9", "")
                .replace("§a", "")
                .replace("§b", "")
                .replace("§c", "")
                .replace("§d", "")
                .replace("§e", "")
                .replace("§f", "")
                .replace("§l", "")
                .replace("§o", "")
                .replace("§n", "");
    }

    public static String getMessage(String path) {
        String message = null;
        if(WarpCreator.messages.get(path) != null) message = String.valueOf(WarpCreator.messages.get(path)).replace("&", "§");
        return message;
    }

    public static String getConfigInfo(String path) {
        String configInfo = null;
        if(WarpCreator.config.get(path) != null) configInfo = String.valueOf(WarpCreator.config.get(path)).replace("&", "§");
        return configInfo;
    }

    public static String getConfigName(String name) {
        return Utils.removeColor(name)
                .replace(" ", "_")
                .replace(".", "_")
                .replace(",", "_")
                .toLowerCase();
    }

}
