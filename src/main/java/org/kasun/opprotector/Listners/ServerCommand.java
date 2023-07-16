package org.kasun.opprotector.Listners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

public class ServerCommand implements Listener {
    @EventHandler
    public void onServerCommand(org.bukkit.event.server.ServerCommandEvent e){
        if (e.getCommand().startsWith("op ")) {
            String str = e.getCommand();
            int index = str.indexOf("op ");
            String result = str.substring(index + 3);
            Player player = Bukkit.getPlayer(result);
            if (player != null){
                VerificationProcessManager verificationProcessManager = new VerificationProcessManager();
                verificationProcessManager.start(player);
            }
        }
    }
}
