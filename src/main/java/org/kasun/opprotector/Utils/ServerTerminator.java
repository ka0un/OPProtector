package org.kasun.opprotector.Utils;

import org.bukkit.plugin.Plugin;
import org.kasun.opprotector.OPProtector;

public class ServerTerminator {

    OPProtector plugin = OPProtector.getInstance();
    public ServerTerminator(String message) {
        plugin.getLogger().severe(" ");
        plugin.getLogger().severe("==============================================================");
        plugin.getLogger().severe(" ");
        plugin.getLogger().severe("Server is terminated by OPProtector due to a critical error!");
        plugin.getLogger().severe(message);
        plugin.getLogger().severe(" ");
        plugin.getLogger().severe("==============================================================");
        plugin.getLogger().severe(" ");

        plugin.getServer().shutdown();
    }

}
