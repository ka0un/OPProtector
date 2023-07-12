package org.kasun.opprotector.Configs;


import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.opprotector.OPProtector;

import java.io.File;
import java.util.HashMap;

public class CustomConfig {
    OPProtector plugin = OPProtector.getInstance();
    private HashMap<String, Object> Passwords = new HashMap<>();
    private HashMap<String, Object> Messages = new HashMap<>();
    private HashMap<String, Object> IpList = new HashMap<>();

    private File fileIp;
    private File fileMsg;
    private File filePass;
    private YamlConfiguration yamlConfigurationIp;
    private YamlConfiguration yamlConfigurationMsg;
    private YamlConfiguration yamlConfigurationPass;

    public CustomConfig() {
        if (plugin.isFirstTime()) {
            createconfigfiles();
        }
        loadConfig();
    }

    private void loadConfig() {
        fileIp = new File(plugin.getDataFolder() + "iplist.yml");
        fileMsg = new File(plugin.getDataFolder() + "messages.yml");
        filePass = new File(plugin.getDataFolder() + "passwords.yml");
        yamlConfigurationIp = YamlConfiguration.loadConfiguration(fileIp);
        yamlConfigurationMsg = YamlConfiguration.loadConfiguration(fileMsg);
        yamlConfigurationPass = YamlConfiguration.loadConfiguration(filePass);
        yamlConfigurationIp.getKeys(false).forEach(key -> {
            IpList.put(key, yamlConfigurationIp.get(key));
        });
        yamlConfigurationMsg.getKeys(false).forEach(key -> {
            Messages.put(key, yamlConfigurationMsg.get(key));
        });
        yamlConfigurationPass.getKeys(false).forEach(key -> {
            Passwords.put(key, yamlConfigurationPass.get(key));
        });
    }

    private void createconfigfiles() {
        plugin.saveResource("iplist.yml", false);
        plugin.saveResource("messages.yml", false);
        plugin.saveResource("passwords.yml", false);
    }

    public void saveIpList() {
        IpList.forEach((key, value) -> {
            yamlConfigurationIp.set(key, value);
        });
        try {
            yamlConfigurationIp.save(fileIp);
        } catch (Exception ignored) {}
    }

    public void saveMessages() {
        Messages.forEach((key, value) -> {
            yamlConfigurationMsg.set(key, value);
        });
        try {
            yamlConfigurationMsg.save(fileMsg);
        } catch (Exception ignored) {}
    }

    public void savePasswords() {
        Passwords.forEach((key, value) -> {
            yamlConfigurationPass.set(key, value);
        });
        try {
            yamlConfigurationPass.save(filePass);
        } catch (Exception ignored) {}
    }

    public HashMap<String, Object> getIpList() {
        return IpList;
    }

    public HashMap<String, Object> getMessages() {
        return Messages;
    }

    public HashMap<String, Object> getPasswords() {
        return Passwords;
    }



}