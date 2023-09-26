package org.kasun.opprotector.Scanner;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Ban;
import org.kasun.opprotector.Utils.CommandExecutor;
import org.kasun.opprotector.Utils.OfflineScannerResultType;

import java.util.HashMap;
import java.util.List;

public class OfflineScanner {
    private Player sender;
    private HashMap<OfflinePlayer, OfflineScannerResultType> allDetectedPlayers = new HashMap<>();
    private HashMap<OfflinePlayer, String> DetectedPlayersWithReason = new HashMap<>();

    private OPProtector plugin;

    public OfflineScanner(Player sender) {
        plugin = OPProtector.getInstance();
        this.sender = sender;



    }

    private void scan(OfflinePlayer player){
        List<String> blacklistedPermissions = plugin.getMainManager().getConfigManager().getMainConfig().blacklisted_permissions;
        boolean allowScanCreative = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_gamemode_creative;
        boolean allowScanBlacklistedPerms = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_blacklisted_permissions;
        boolean opContainsInYml = plugin.getMainManager().getConfigManager().getOperatorConfig().isContains(player.getName());

        Player p = (Player) player;

        for (String permission : blacklistedPermissions) {
            try {
                if (((Player) player).hasPermission(permission)) {
                    if (!opContainsInYml) {
                        if (!player.isOp()){
                            //do if permission detected
                        }else{
                            //do if permission not detected
                        }

                    }
                }
            } catch (NullPointerException ignored) {
            }
        }

        // Check if player is an OP
        if (!player.isOp() && !(p.getGameMode() == GameMode.CREATIVE && allowScanCreative)) {
            return;
        }

        // Check if player is listed in operators.yml
        if (!opContainsInYml) {
            //do if player is not listed in operators.yml
        }


    }

}
