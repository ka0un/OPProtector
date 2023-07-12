package org.kasun.opprotector.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.opprotector.OPProtector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MainConfig {
    private final OPProtector plugin = OPProtector.getInstance();
    private File configFile;
    private FileConfiguration config;
    private HashMap<String, Object> configMap = new HashMap<>();

    public MainConfig() {
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        loadConfig();
    }

    public void loadConfig() {
        config.getKeys(false).forEach(key -> {
            configMap.put(key, config.get(key));
        });
    }

    public void saveConfig() {
        configMap.forEach((key, value) -> {
            config.set(key, value);
        });
        try {
            config.save(configFile);
        } catch (IOException ignored) {}
    }

    public HashMap<String, Object> getConfigMap() {
        return configMap;
    }
}


