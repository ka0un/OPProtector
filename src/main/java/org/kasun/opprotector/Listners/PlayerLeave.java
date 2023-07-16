package org.kasun.opprotector.Listners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.VerificationProcess.PasswordFlash;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

public class PlayerLeave implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLeave(org.bukkit.event.player.PlayerQuitEvent e){
        OPProtector plugin = OPProtector.getInstance();
        VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
        if (plugin.getServer().getOnlinePlayers().size() == 1) {
            plugin.getMainManager().getLiveScanner().stop();
        }
        boolean isinverification = verificationProcessManager.isInVerification(e.getPlayer());
        if (isinverification) {
            PasswordFlash passwordFlash = verificationProcessManager.getPasswordFlash();
            passwordFlash.stopTasks();
            verificationProcessManager.getInVerification().remove(e.getPlayer());
        }
    }
}
