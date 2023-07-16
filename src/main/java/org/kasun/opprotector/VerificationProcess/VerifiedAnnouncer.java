package org.kasun.opprotector.VerificationProcess;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VerifiedAnnouncer {
    OPProtector plugin = OPProtector.getInstance();
    public VerifiedAnnouncer(Player player) {
        player.sendTitle(ChatColor.GREEN + "✔", ChatColor.YELLOW + "OPProtector", 10, 70, 20);
        Sound sound = Sound.BLOCK_NOTE_BLOCK_PLING;
        player.playSound(player.getLocation(), sound, 1, 1);

        Set<OfflinePlayer> operatorSet = plugin.getServer().getOperators();

        for (OfflinePlayer offlinePlayer : operatorSet) {
            if (offlinePlayer.isOnline()) {
                Player onlinePlayer = offlinePlayer.getPlayer();
                onlinePlayer.sendMessage(ChatColor.RED + " [" + player.getName() + "] " + ChatColor.BLUE+ "[" + player.getAddress().getHostName() + "]" + ChatColor.GRAY + " is Authorized by " + ChatColor.GREEN + " [OPProtector]" + ChatColor.GRAY + " and now have Operator Access ✔");
            }
        }
    }
}
