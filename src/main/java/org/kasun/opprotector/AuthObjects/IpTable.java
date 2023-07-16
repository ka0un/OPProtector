package org.kasun.opprotector.AuthObjects;

import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;

import java.util.HashMap;

public class IpTable {
    HashMap<String, Object> ipTable;
    OPProtector plugin = OPProtector.getInstance();
    public IpTable() {
        ipTable = plugin.getMainManager().getConfigManager().getCustomConfig().getIpList();
    }

    public void addIp(Player player) {
        String playerName = player.getName();
        String playerIp = player.getAddress().getAddress().getHostAddress();
        ipTable.put(playerName, playerIp);
    }

    public boolean isIp(Player player) {
        String playerName = player.getName();
        String playerIp = player.getAddress().getAddress().getHostAddress();
        if (ipTable.containsKey(playerName)) {
            if (ipTable.get(playerName).equals(playerIp)) {
                return true;
            }
        }
        return false;
    }

    public void removeIp(Player player) {
        String playerName = player.getName();
        ipTable.remove(playerName);
    }

    public void saveIp() {
        plugin.getMainManager().getConfigManager().getCustomConfig().saveIpList();
        plugin.getMainManager().getConfigManager().getCustomConfig().saveIpList();
    }

}
