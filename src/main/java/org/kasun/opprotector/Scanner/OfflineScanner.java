package org.kasun.opprotector.Scanner;


import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Utils.LuckpermsCheck;
import org.kasun.opprotector.Utils.OfflineScannerResultType;
import org.kasun.opprotector.Utils.Prefix;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class OfflineScanner {
    private CommandSender sender;
    private HashMap<OfflinePlayer, String> DetectedPlayersWithReason = new HashMap<>();
    private int totalOfflinePlayers;
    private List<String> blacklistedPermissions;
    private boolean allowScanBlacklistedPerms;
    private List<OfflineScanResult> resultList;

    private OPProtector plugin;

    public OfflineScanner(CommandSender sender) {
        resultList = new ArrayList<>();
        plugin = OPProtector.getInstance();
        this.sender = sender;

        blacklistedPermissions = plugin.getMainManager().getConfigManager().getMainConfig().blacklisted_permissions;
        allowScanBlacklistedPerms = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_blacklisted_permissions;

        List<OfflinePlayer> offlinePlayers = Arrays.asList(plugin.getServer().getOfflinePlayers());
        totalOfflinePlayers = offlinePlayers.size();

        if (totalOfflinePlayers == 0) {
            sender.sendMessage(Prefix.ERROR + "No Joined Players Found");
            return;
        }else{
            sender.sendMessage(Prefix.SUCCESS + "Started Scanning " + offlinePlayers.size() + " Players");
            if (totalOfflinePlayers > 100){
                sender.sendMessage(Prefix.WARNING + "This may take a while and might cause lag");
            }
        }

        boolean onlyOP = !isLuckPermsInstalledAndEnabled();

        if (onlyOP){
            sender.sendMessage(Prefix.WARNING + "LuckPerms not found, scanning for OPs only");
        }


        int i = 1;
        for (OfflinePlayer player : offlinePlayers) {
            scan(player, onlyOP);
            if (i != 0 && i % 10 == 0) {
                sender.sendMessage(Prefix.INFO + "Scanned " + i + " / "+totalOfflinePlayers +" Players");
            }
        }

        sender.sendMessage(Prefix.SUCCESS + "Finished Scanning");

        if (resultList == null|| resultList.size() == 0){
            sender.sendMessage(Prefix.SUCCESS + "No Players Detected");
            return;
        }else{
            plugin.getMainManager().setOfflinePlayerScanResultList(resultList);
        }

        new ScanResults(resultList, sender);

    }

    private void scan(OfflinePlayer player, boolean onlyOP){

        if (player.isBanned()){
            return;
        }

        boolean opContainsInYml = plugin.getMainManager().getConfigManager().getOperatorConfig().isContains(player.getName());


        if (onlyOP){
            if (!opContainsInYml){
                if (player.isOp()){
                    OfflineScanResult offlineScanResult = new OfflineScanResult(player, OfflineScannerResultType.UnlistedOP, "Not Listed in operators.yml");
                    resultList.add(offlineScanResult);
                }
            }
        }else{

            for (String permission : blacklistedPermissions) {
                try {
                    if (LuckpermsCheck.hasPermission(player.getUniqueId(), permission)) {
                        if (!opContainsInYml) {
                            if (!player.isOp()){
                                OfflineScanResult offlineScanResult = new OfflineScanResult(player, OfflineScannerResultType.BlackListedPerms, "Permission : " + permission);
                                resultList.add(offlineScanResult);
                                return;
                            }else{
                                OfflineScanResult offlineScanResult = new OfflineScanResult(player, OfflineScannerResultType.UnlistedOP, "Not Listed in operators.yml");
                                resultList.add(offlineScanResult);
                                return;
                            }

                        }else{
                            return;
                        }
                    }
                } catch (NullPointerException ignored) {
                }
            }

            // Check if player is an OP
            if (player.isOp()) {
                if (!opContainsInYml) {
                    OfflineScanResult offlineScanResult = new OfflineScanResult(player, OfflineScannerResultType.UnlistedOP, "Not Listed in operators.yml");
                    resultList.add(offlineScanResult);
                }
            }
        }

    }

    private boolean isLuckPermsInstalledAndEnabled() {
        Plugin luckPermsPlugin = Bukkit.getPluginManager().getPlugin("LuckPerms");
        return luckPermsPlugin != null && luckPermsPlugin.isEnabled();
    }







}
