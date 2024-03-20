package org.kasun.opprotector.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Ban;
import org.kasun.opprotector.Utils.CommandExecutor;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;
import org.kasun.opprotector.VerificationProcess.VerificationStatus;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class LiveScanner {
    private BukkitTask liveScannerTask;

    public LiveScanner() {
    }

    public void start() {
        OPProtector plugin = OPProtector.getInstance();
        Long period = (long) (1 * 20);
        liveScannerTask = new BukkitRunnable() {
            @Override
            public void run() {
                ConcurrentHashMap<String, VerificationStatus> verificationStatusMap = plugin.getMainManager().getVerificationProcessManager().getVerificationStatusMap();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    VerificationStatus verificationStatus = verificationStatusMap.getOrDefault(player.getName(), VerificationStatus.NOT_VERIFIED);

                    if (verificationStatus != VerificationStatus.VERIFIED && verificationStatus != VerificationStatus.IN_PASSWORD_VERIFICATION) {
                        scanPlayer(player, verificationStatusMap, plugin);
                    }
                }
            }
        }.runTaskTimer(plugin, 5L, period); // Run every second (20 ticks)
    }

    private void scanPlayer(Player player, ConcurrentHashMap<String, VerificationStatus> verificationStatusMap, OPProtector plugin) {
        List<String> blacklistedPermissions = plugin.getMainManager().getConfigManager().getMainConfig().blacklisted_permissions;
        boolean allowScanCreative = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_gamemode_creative;
        boolean allowScanBlacklistedPerms = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_blacklisted_permissions;
        boolean opContainsInYml = plugin.getMainManager().getConfigManager().getOperatorConfig().isContains(player.getName());

        // Check for blacklisted permissions
        if (allowScanBlacklistedPerms) {
            for (String permission : blacklistedPermissions) {
                if (player.hasPermission(permission) && !opContainsInYml) {
                    executeCommandsAndBanPlayer(player, "You aren't listed in OPProtector/operators.yml", "Unauthorized Access", plugin);
                }
            }
        }

        // Check if player is an OP
        if (player.isOp() || (player.getGameMode() == GameMode.CREATIVE && allowScanCreative)) {
            if (!opContainsInYml) {
                executeCommandsAndBanPlayer(player, "You aren't listed in OPProtector/operators.yml", "Unauthorized Access", plugin);
            }
        }

        verify(player, plugin);
    }

    private void executeCommandsAndBanPlayer(Player player, String banReason, String logReason, OPProtector plugin) {
        List<String> commands = plugin.getMainManager().getConfigManager().getMainConfig().not_in_operators_list;
        CommandExecutor commandExecutor = new CommandExecutor(player, commands);
        Ban ban = new Ban(player, banReason, logReason);
        plugin.getMainManager().getLog().banned(player, logReason);
    }

    private void verify(Player player, OPProtector plugin) {
        VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
        verificationProcessManager.start(player);
    }

    public void stop() {
        if (liveScannerTask != null) {
            liveScannerTask.cancel();
            liveScannerTask = null;
        }
    }
}


