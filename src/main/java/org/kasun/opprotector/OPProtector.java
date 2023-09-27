package org.kasun.opprotector;


import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.opprotector.Utils.Metrics;

import java.util.Map;

public final class OPProtector extends JavaPlugin {
    private static OPProtector instance;
    private boolean isFirstTime;
    MainManager mainManager;


    @Override
    public void onEnable() {
        instance = this;
        fisttimecheck();
        mainManager = new MainManager();
        int pluginId = 19908;
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static OPProtector getInstance() {
        return instance;
    }

    public void fisttimecheck() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            isFirstTime = true;
        }
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public MainManager getMainManager() {
        return mainManager;
    }


}
