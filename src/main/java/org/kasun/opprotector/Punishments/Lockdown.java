package org.kasun.opprotector.Punishments;
import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;
import java.util.ArrayList;


public class Lockdown {
    private OPProtector plugin = OPProtector.getInstance();
    private ArrayList<Player> lockedPlayers;
    private boolean allowflight;
    private boolean blockdamage;
    private boolean blockitempickup;
    public Lockdown() {
        lockedPlayers = new ArrayList<>();
    }

    public void lockPlayer(Player player){
        allowflight = plugin.getMainManager().getConfigManager().getMainConfig().allow_flight;
        blockdamage = plugin.getMainManager().getConfigManager().getMainConfig().block_damage;
        blockitempickup = plugin.getMainManager().getConfigManager().getMainConfig().block_item_pickup;

        lockedPlayers.add(player);
        if (allowflight){
            player.setAllowFlight(true);
        }
        if (blockdamage){
            player.setInvulnerable(true);
        }
        if (blockitempickup){
            player.setCanPickupItems(false);
        }
    }

    public void unlockPlayer(Player player){
        allowflight = plugin.getMainManager().getConfigManager().getMainConfig().allow_flight;
        blockdamage = plugin.getMainManager().getConfigManager().getMainConfig().block_damage;
        blockitempickup = plugin.getMainManager().getConfigManager().getMainConfig().block_item_pickup;

        lockedPlayers.remove(player);
        if (allowflight){
            player.setAllowFlight(false);
        }
        if (blockdamage){
            player.setInvulnerable(false);
        }
        if (blockitempickup){
            player.setCanPickupItems(true);
        }
    }

    public boolean isPlayerLocked(Player player){
        if (lockedPlayers.contains(player)){
            return true;
        }else {
            return false;
        }
    }

}
