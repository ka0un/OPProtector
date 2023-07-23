package org.kasun.opprotector.AuthObjects;

import org.bukkit.entity.Player;
import org.kasun.opprotector.Configs.CustomConfig;
import org.kasun.opprotector.OPProtector;

import java.util.HashMap;

public class IpTable {
    HashMap<String, Object> ipTable;
    CustomConfig customConfig;
    OPProtector plugin = OPProtector.getInstance();
    public IpTable() {
        customConfig = new CustomConfig();
        ipTable = customConfig.getIpList();
    }

    public boolean IsContains(Player player){
        String playerName = player.getName();
        if (ipTable.containsKey(playerName)){
            return true;
        }
        return false;
    }

    public void addIp(Player player) {
        String playerName = player.getName();
        String playerIp = player.getAddress().getAddress().getHostAddress();
        ipTable.put(playerName, playerIp);
        saveIp();
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
        saveIp();
    }

    private void saveIp() {
        customConfig.saveIpList();
    }

}
