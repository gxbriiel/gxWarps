package com.gxbriiel.gxwarps.utils;

import com.gxbriiel.gxwarps.Warps;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private Warps plugin = Warps.getPlugin();

    private String name;

    private File file;

    private YamlConfiguration config;

    public ConfigManager(String name) {
        setName(name);
        reloadConfig();
    }

    public Plugin getPlugin() {
        return (Plugin)this.plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = (Warps) plugin;
    }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public File getFile() { return this.file; }

    public YamlConfiguration getConfig() { return this.config; }

    public void saveConfig() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        getConfig().options().copyDefaults(true);
    }

    public void saveDefaultConfig() {
        if (!existConfig()) {
            getPlugin().saveResource(getName(), false);
            reloadConfig();
        }
    }

    public void reloadConfig() {
        this.file = new File((Warps.getPlugin()).getDataFolder(), getName());
        this.config = YamlConfiguration.loadConfiguration(getFile());
    }

    public void deleteConfig() {
        getFile().delete();
    }

    public boolean existConfig() {
        return getFile().exists();
    }

    public String getString(String path) {
        return getConfig().getString(path);
    }

    public int getInt(String path) {
        return getConfig().getInt(path);
    }

    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public double getDouble(String path) {
        return getConfig().getDouble(path);
    }

    public List<?> getList(String path) {
        return getConfig().getList(path);
    }

    public long getLong(String path) {
        return getConfig().getLong(path);
    }

    public float getFloat(String path) {
        return getConfig().getInt(path);
    }

    public Object get(String path) {
        return getConfig().get(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return getConfig().getMapList(path);
    }

    public List<String> getStringList(String path) {
        return getConfig().getStringList(path);
    }

    public boolean contains(String path) {
        return getConfig().contains(path);
    }

    public void set(String path, Object value) {
        getConfig().set(path, value);
    }

    public void setString(String path, String value) {
        getConfig().set(path, value);
    }

}
