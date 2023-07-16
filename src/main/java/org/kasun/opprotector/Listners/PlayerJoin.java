package org.kasun.opprotector.Listners;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;


public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        VerificationProcessManager verificationProcessManager = new VerificationProcessManager();
        verificationProcessManager.start(player);
    }

}
