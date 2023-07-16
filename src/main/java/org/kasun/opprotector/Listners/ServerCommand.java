package org.kasun.opprotector.Listners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

public class ServerCommand implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerCommand(org.bukkit.event.server.ServerCommandEvent e){
        if (e.getCommand().startsWith("op ")) {
            String str = e.getCommand();
            int index = str.indexOf("op ");
            String result = str.substring(index + 3);
            Player player = Bukkit.getPlayer(result);
            if (player != null){
                OPProtector plugin = OPProtector.getInstance();
                VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
                verificationProcessManager.start(player);
            }
        }
    }
}
