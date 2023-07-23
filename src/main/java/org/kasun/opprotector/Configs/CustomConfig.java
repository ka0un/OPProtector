package org.kasun.opprotector.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.opprotector.OPProtector;
import java.io.*;
import java.util.HashMap;

public class CustomConfig {
    OPProtector plugin = OPProtector.getInstance();
    //private HashMap<String, Object> Messages;
    private HashMap<String, Object> IpList;
    private File fileIp;
    //private File fileMsg;
    private YamlConfiguration yamlConfigurationIp;
    //private YamlConfiguration yamlConfigurationMsg;


    public CustomConfig() {
        if (plugin.isFirstTime()) {
            createconfigfiles();
        }
        loadConfig();
    }

    private void loadConfig() {
        //Messages = new HashMap<>();
        IpList = new HashMap<>();
        fileIp = new File(plugin.getDataFolder() + "/iplist.yml");
        //fileMsg = new File(plugin.getDataFolder() + "/messages.yml");
        yamlConfigurationIp = YamlConfiguration.loadConfiguration(fileIp);
        //yamlConfigurationMsg = YamlConfiguration.loadConfiguration(fileMsg);
        yamlConfigurationIp.getKeys(false).forEach(key -> {
            IpList.put(key, yamlConfigurationIp.get(key));
        });
        //yamlConfigurationMsg.getKeys(false).forEach(key -> {
        //    Messages.put(key, yamlConfigurationMsg.get(key));
        //});
    }


    private void createconfigfiles() {
        plugin.saveResource("iplist.yml", false);
        //plugin.saveResource("messages.yml", false);
        plugin.saveResource("operators.yml", false);
    }

    public void saveIpList() {
        IpList.forEach((key, value) -> {
            yamlConfigurationIp.set(key, value);
        });
        try {
            yamlConfigurationIp.save(fileIp);
        } catch (Exception ignored) {}
    }

    /*public void saveMessages() {
        Messages.forEach((key, value) -> {
            yamlConfigurationMsg.set(key, value);
        });
        try {
            yamlConfigurationMsg.save(fileMsg);
        } catch (Exception ignored) {}
    }

     */

    public HashMap<String, Object> getIpList() {
        return IpList;
    }

    /*
    public HashMap<String, Object> getMessages() {
        return Messages;
    }

     */

}