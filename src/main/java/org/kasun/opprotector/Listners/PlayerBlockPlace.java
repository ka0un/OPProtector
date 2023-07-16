package org.kasun.opprotector.Listners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Lockdown;

public class PlayerBlockPlace implements Listener {
    Lockdown lockdown;
    OPProtector plugin = OPProtector.getInstance();
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerBlockPlace(org.bukkit.event.block.BlockPlaceEvent e){
        lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        boolean allow = plugin.getMainManager().getConfigManager().getMainConfig().block_place_block;
        if (allow && lockdown.isPlayerLocked(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
