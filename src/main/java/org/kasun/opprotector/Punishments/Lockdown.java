package org.kasun.opprotector.Punishments;
import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;
import java.util.ArrayList;


public class Lockdown {
    private OPProtector plugin = OPProtector.getInstance();
    private ArrayList<Player> lockedPlayers;
    public Lockdown() {
        lockedPlayers = new ArrayList<>();
    }

    public void lockPlayer(Player player){
        lockedPlayers.add(player);
        player.setInvulnerable(true);
        player.setAllowFlight(true);
        player.setCanPickupItems(false);
    }

    public void unlockPlayer(Player player){

        lockedPlayers.remove(player);
        player.setInvulnerable(false);
        player.setAllowFlight(false);
        player.setCanPickupItems(true);
    }

    public boolean isPlayerLocked(Player player){
        if (lockedPlayers.contains(player)){
            return true;
        }else {
            return false;
        }
    }

}
