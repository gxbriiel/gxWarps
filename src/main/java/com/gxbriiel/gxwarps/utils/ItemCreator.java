package com.gxbriiel.gxwarps.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class ItemCreator {

    public static ItemStack createCustomHead(String url, String name, int amount, String[] lore) {

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount);
        itemStack.setDurability((short)3);

        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try{
            Field profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch(IllegalArgumentException | NoSuchFieldException | IllegalAccessException error) {
            error.printStackTrace();
        }

        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);

        return  itemStack;
    }

    public static ItemStack createItem(Material material, String name, int amount, Object durability, String[] lore) {

        ItemStack itemStack = new ItemStack(material, amount);
        if(durability != null) {
            itemStack.setDurability((Short) durability);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);

        return  itemStack;
    }

}
