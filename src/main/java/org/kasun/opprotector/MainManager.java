package org.kasun.opprotector;

import org.kasun.opprotector.Configs.ConfigManager;
import org.kasun.opprotector.Configs.CustomConfig;

public class MainManager {
    ConfigManager configManager;
    CustomConfig customConfig;
    OPProtector plugin = OPProtector.getInstance();
    public MainManager() {
        configManager = new ConfigManager();
        customConfig = new CustomConfig();
        String string = getConfigManager().getMainConfig().getConfigMap().get("test").toString();
        System.out.println("config test : " + string);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CustomConfig getCustomConfig() {
        return customConfig;
    }
}
