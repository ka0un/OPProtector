package org.kasun.opprotector.Configs;

public class ConfigManager {

    MainConfig mainConfig;
    CustomConfig customConfig;
    OperatorConfig operatorConfig;
    public ConfigManager() {
        mainConfig = new MainConfig();
        customConfig = new CustomConfig();
        operatorConfig = new OperatorConfig();
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public CustomConfig getCustomConfig() {
        return customConfig;
    }

    public OperatorConfig getOperatorConfig() {
        return operatorConfig;
    }
}
