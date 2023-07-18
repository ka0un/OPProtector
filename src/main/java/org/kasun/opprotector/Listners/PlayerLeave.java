package org.kasun.opprotector.Listners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.VerificationProcess.PasswordFlash;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;
import org.kasun.opprotector.VerificationProcess.VerificationStatus;

import java.util.HashMap;

public class PlayerLeave implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLeave(org.bukkit.event.player.PlayerQuitEvent e){
        OPProtector plugin = OPProtector.getInstance();
        VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
        HashMap<Player, VerificationStatus> verificationStatusMap = verificationProcessManager.getVerificationStatusMap();
        if (plugin.getServer().getOnlinePlayers().size() == 1) {
            plugin.getMainManager().getLiveScanner().stop();
        }
        boolean isinverification = verificationStatusMap.containsKey(e.getPlayer()) && verificationStatusMap.get(e.getPlayer()) == VerificationStatus.IN_PASSWORD_VERIFICATION;
        if (isinverification) {
            PasswordFlash passwordFlash = verificationProcessManager.getPasswordFlash();
            passwordFlash.stopTasks();
            verificationStatusMap.remove(e.getPlayer());
        }
    }
}
