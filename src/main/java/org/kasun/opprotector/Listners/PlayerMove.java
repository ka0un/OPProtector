package org.kasun.opprotector.Listners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Lockdown;

public class PlayerMove implements Listener {
    Lockdown lockdown;
    OPProtector plugin = OPProtector.getInstance();
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        boolean allow = plugin.getMainManager().getConfigManager().getMainConfig().block_player_move;

        if (allow && lockdown.isPlayerLocked(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}