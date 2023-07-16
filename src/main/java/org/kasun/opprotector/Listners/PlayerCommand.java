package org.kasun.opprotector.Listners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Lockdown;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

import java.util.List;

public class PlayerCommand implements Listener {
    Lockdown lockdown;
    OPProtector plugin = OPProtector.getInstance();
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandProcess(PlayerCommandPreprocessEvent e){
        lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();


        if (lockdown.isPlayerLocked(e.getPlayer())) {
            List<String> commandsWhitelist = plugin.getMainManager().getConfigManager().getMainConfig().commands_whitelist;
            boolean startsWithCommand = false;
            String message = e.getMessage();

            for (String command : commandsWhitelist){
                if (message.startsWith(command)) {
                    startsWithCommand = true;
                    break;
                }
            }

            if (!startsWithCommand) {
                e.setCancelled(true);
            }

        }

        if (e.getMessage().startsWith("/op")){
            String str = e.getMessage();
            int index = str.indexOf("/op ");
            String result = str.substring(index + 4);
            Player player = Bukkit.getPlayer(result);

            if (player != null){
                VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
                verificationProcessManager.start(player);
            }

        }

    }

}
