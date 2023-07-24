package org.kasun.opprotector.Listners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Lockdown;

import java.util.List;

public class PlayerMessage implements org.bukkit.event.Listener{
    OPProtector plugin = OPProtector.getInstance();
    Lockdown lockdown;

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

        if (e.getPlayer().isOp()){
            boolean blockCommand = false;
            List<String> commandsBlacklist = plugin.getMainManager().getConfigManager().getMainConfig().commands_whitelist;
            for (String command : commandsBlacklist){
                if (e.getMessage().startsWith("/" + command)){
                    blockCommand = true;
                    break;
                }
                if (!blockCommand){
                    plugin.getMainManager().getLog().message(e.getPlayer(), e.getMessage());
                }
            }
        }
    }
}
