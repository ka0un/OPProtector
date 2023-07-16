package org.kasun.opprotector.Listners;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.kasun.opprotector.OPProtector;

public class ListnerManager {
    OPProtector plugin = OPProtector.getInstance();
    public ListnerManager() {
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerMove(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerCommand(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ServerCommand(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerBlockBreak(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerBlockPlace(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerItemDrop(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerLeave(), plugin);

    }
}
