package org.kasun.opprotector.Listners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Lockdown;

public class PlayerItemDrop implements Listener {
    Lockdown lockdown;
    OPProtector plugin = OPProtector.getInstance();
    @EventHandler
    public void onPlayerItemDrop(org.bukkit.event.player.PlayerDropItemEvent e){
        lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        boolean allow = plugin.getMainManager().getConfigManager().getMainConfig().block_item_drop;
        if (allow && lockdown.isPlayerLocked(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
