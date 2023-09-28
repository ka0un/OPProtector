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

import java.util.HashMap;
import java.util.List;

public class LiveScanner {
    private BukkitTask liveScannerTask;

    public LiveScanner() {
    }

    public void start() {
        OPProtector plugin = OPProtector.getInstance();
        int seconds = plugin.getMainManager().getConfigManager().getMainConfig().live_scanner_interval_secounds;
        Long period = (long) (seconds * 20);
        liveScannerTask = new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<String, VerificationStatus> verificationStatusMap = plugin.getMainManager().getVerificationProcessManager().getVerificationStatusMap();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    VerificationStatus verificationStatus = verificationStatusMap.getOrDefault(player.getName(), VerificationStatus.NOT_VERIFIED);

                    if (verificationStatus != VerificationStatus.VERIFIED && verificationStatus != VerificationStatus.IN_PASSWORD_VERIFICATION
                            && verificationStatus != VerificationStatus.IN_FACTOR_VERIFICATION && verificationStatus != VerificationStatus.DOING_FACTOR_VERIFICATION) {

                        List<String> blacklistedPermissions = plugin.getMainManager().getConfigManager().getMainConfig().blacklisted_permissions;
                        boolean allowScanCreative = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_gamemode_creative;
                        boolean allowScanBlacklistedPerms = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_blacklisted_permissions;
                        boolean opContainsInYml = plugin.getMainManager().getConfigManager().getOperatorConfig().isContains(player.getName());

                        // Check for blacklisted permissions
                        if (allowScanBlacklistedPerms) {
                            for (String permission : blacklistedPermissions) {
                                try {
                                    if (player.hasPermission(permission)) {
                                        if (!opContainsInYml) {
                                            if (!player.isOp()){
                                                List<String> haveBlacklistedPermsCommands = plugin.getMainManager().getConfigManager().getMainConfig().have_blacklisted_perms;
                                                CommandExecutor commandExecutor = new CommandExecutor(player, haveBlacklistedPermsCommands);
                                                Ban ban = new Ban(player, "You aren't listed in OPProtector/operators.yml", "Unauthorized Access");
                                                plugin.getMainManager().getLog().banned(player, "Blacklisted Permission : " + permission);
                                            }else{
                                                List<String> commands = plugin.getMainManager().getConfigManager().getMainConfig().not_in_operators_list;
                                                CommandExecutor commandExecutor = new CommandExecutor(player, commands);
                                                Ban ban = new Ban(player, "You aren't listed in OPProtector/operators.yml", "Unauthorized Access");
                                                plugin.getMainManager().getLog().banned(player, "Not listed in operators.yml");
                                            }

                                        }
                                    }
                                } catch (NullPointerException ignored) {
                                }
                            }
                        }

                        // Check if player is an OP
                        if (!player.isOp() && !(player.getGameMode() == GameMode.CREATIVE && allowScanCreative)) {
                            continue;
                        }

                        // Check if player is listed in operators.yml
                        if (!opContainsInYml) {
                            List<String> commands = plugin.getMainManager().getConfigManager().getMainConfig().not_in_operators_list;
                            CommandExecutor commandExecutor = new CommandExecutor(player, commands);
                            Ban ban = new Ban(player, "You aren't listed in OPProtector/operators.yml", "Unauthorized Access");
                            plugin.getMainManager().getLog().banned(player, "Not listed in operators.yml");
                        }

                        verify(player);
                    }
                }
            }
        }.runTaskTimer(plugin, 5L, period); // Run every second (20 ticks)
    }

    private void verify(Player player) {
        OPProtector plugin = OPProtector.getInstance();
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


