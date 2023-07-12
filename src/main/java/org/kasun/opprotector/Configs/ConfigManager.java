package org.kasun.opprotector.Configs;

public class ConfigManager {

    MainConfig mainConfig;
    CustomConfig customConfig;
    public ConfigManager() {
        mainConfig = new MainConfig();
        customConfig = new CustomConfig();

    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public CustomConfig getCustomConfig() {
        return customConfig;
    }
}
