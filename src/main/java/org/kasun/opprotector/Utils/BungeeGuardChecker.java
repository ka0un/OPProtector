package org.kasun.opprotector.Utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.kasun.opprotector.OPProtector;

public class BungeeGuardChecker {

    public static void checkAndNotify() {

        OPProtector plugin = OPProtector.getInstance();

        if (!plugin.getMainManager().getConfigManager().getMainConfig().check_for_bungeeguard) {
            return;
        }

        boolean onlineMode = Bukkit.getServer().getOnlineMode();

        if (!onlineMode && !isBungeeGuardInstalled()) {
            new ServerTerminator("BungeeGuard is not installed. Please Install and Configure the BungeeGuard Properly. Without BungeeGuard, Attackers might get access to your server easily.");
        }
    }

    private static boolean isBungeeGuardInstalled() {
        Plugin bungeeGuard = Bukkit.getPluginManager().getPlugin("BungeeGuard");
        return bungeeGuard != null && bungeeGuard.isEnabled();
    }
}
