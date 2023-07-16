package org.kasun.opprotector.AuthObjects;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.kasun.opprotector.Configs.OperatorConfig;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Ban;
import org.kasun.opprotector.Utils.CommandExecutor;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

import java.util.ArrayList;
import java.util.List;

public class LiveScanner {
    private BukkitTask liveScannerTask;
    public LiveScanner() {

    }
    public void start() {
        OPProtector plugin = OPProtector.getInstance();
        int secounds = plugin.getMainManager().getConfigManager().getMainConfig().live_scanner_interval_secounds;
        Long period = (long) (secounds * 20);
        liveScannerTask =  new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Player> verifiedList = plugin.getMainManager().getVerificationProcessManager().getVerifiedPlayers();
                VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                        Bukkit.getOnlinePlayers().forEach(p -> {
                            if (!verifiedList.contains(p)) {
                                List<String> blacklistedPermissions = plugin.getMainManager().getConfigManager().getMainConfig().blacklisted_permissions;
                                boolean allowScanCreative = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_gamemode_creative;
                                boolean allowScanBlackListedPerms = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_blacklisted_permissions;
                                List<String> opnames = OperatorConfig.getOperatorNames();

                                //no blacklisted permissions
                                if (allowScanBlackListedPerms){
                                    for (String permission : blacklistedPermissions){
                                        try{
                                            if (player.hasPermission(permission)){
                                                if (!opnames.contains(player.getName())){
                                                    List<String> haveBlacklistedPermsCommands = plugin.getMainManager().getConfigManager().getMainConfig().have_blacklisted_perms;
                                                    CommandExecutor commandExecutor = new CommandExecutor(p, haveBlacklistedPermsCommands);
                                                    Ban ban = new Ban(player, "You arent listed in OPProtector/operators.yml", "Unautharized Access");
                                                }
                                            }
                                        }catch (NullPointerException ignored){}
                                    }
                                }

                                //is op
                                if (!player.isOp() && !(player.getGameMode() == GameMode.CREATIVE && allowScanCreative)){
                                    return;
                                }

                                //is listed in operators.yml
                                if (!opnames.contains(player.getName())){
                                    List<String> commands = plugin.getMainManager().getConfigManager().getMainConfig().not_in_operators_list;
                                    CommandExecutor commandExecutor = new CommandExecutor(p, commands);
                                    Ban ban = new Ban(player, "You arent listed in OPProtector/operators.yml", "Unautharized Access");
                                }

                                Bukkit.getScheduler().runTask(plugin, () -> {
                                    verify(player);
                                });


                            }
                        });
                    });
                }
            }
        }.runTaskTimerAsynchronously(plugin, 5L, period); // Run every second (20 ticks)
    }

    private void verify(Player player){
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

