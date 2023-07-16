package org.kasun.opprotector.AuthObjects;

import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;

import java.sql.Timestamp;
import java.util.HashMap;

public class TempAuth {
    HashMap<String, Timestamp> authorizedPlayers;
    private OPProtector plugin = OPProtector.getInstance();
    public TempAuth() {
        authorizedPlayers = new HashMap<>();
    }

    public void addAuthorizedPlayer(Player player){
        authorizedPlayers.put(player.getName(), new Timestamp(System.currentTimeMillis()));
    }

    public boolean isAuthorizedPlayer(Player player){
        int time = (int) plugin.getMainManager().getConfigManager().getMainConfig().session_hours;
        if(authorizedPlayers.containsKey(player.getName())){
            if (authorizedPlayers.get(player.getName()).getTime() + (3600000 * time) > System.currentTimeMillis()) {
                return true;
            }
        }
        return false;
    }


}
