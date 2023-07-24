package org.kasun.opprotector;


import org.bukkit.plugin.java.JavaPlugin;

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
