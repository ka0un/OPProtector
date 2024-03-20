package org.kasun.opprotector.VerificationProcess;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.kasun.opprotector.AuthObjects.IpTable;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Ban;
import org.kasun.opprotector.Punishments.Lockdown;
import org.kasun.opprotector.Utils.CommandExecutor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class VerificationProcessManager {
    private static final OPProtector plugin = OPProtector.getInstance();
    private List<String> blacklistedPermissions;
    private PasswordFlash passwordFlash;
    private ConcurrentHashMap<String, VerificationStatus> verificationStatusMap;
    private boolean allowScanBlackListedPerms;
    private boolean allowScanCreative;
    private IpTable ipTable;

    public VerificationProcessManager() {
        verificationStatusMap = new ConcurrentHashMap<>();
    }

    public void start(Player player) {
        blacklistedPermissions = plugin.getMainManager().getConfigManager().getMainConfig().blacklisted_permissions;
        allowScanCreative = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_gamemode_creative;
        allowScanBlackListedPerms = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_blacklisted_permissions;
        boolean opContainsInYml = plugin.getMainManager().getConfigManager().getOperatorConfig().isContains(player.getName());

        if (allowScanBlackListedPerms) {
            checkForBlacklistedPermissions(player, opContainsInYml);
        }

        if (player.isOp() || (player.getGameMode() == GameMode.CREATIVE && allowScanCreative)) {
            checkForOp(player, opContainsInYml);
        }
    }

    private void checkForBlacklistedPermissions(Player player, boolean opContainsInYml) {
        for (String permission : blacklistedPermissions) {
            if (player.hasPermission(permission) && !opContainsInYml) {
                executeCommandsAndBanPlayer(player, "You aren't listed in OPProtector/operators.yml", "Unauthorized Access");
            }
        }
    }

    private void checkForOp(Player player, boolean opContainsInYml) {
        if (!opContainsInYml) {
            executeCommandsAndBanPlayer(player, "You aren't listed in OPProtector/operators.yml", "Unauthorized Access");
        } else if (plugin.getMainManager().getAuthorizedPlayers().isAuthorizedPlayer(player) && isPlayerVerified(player)) {
            announceVerified(player);
        } else if (isPlayerInPasswordVerification(player)) {
            // Do nothing
        } else if (isPlayerLoggedInUsingSameIp(player)) {
            announceVerified(player);
            verificationStatusMap.put(player.getName(), VerificationStatus.VERIFIED);
        } else if (!plugin.getMainManager().getConfigManager().getMainConfig().password_enabled) {
            announceVerified(player);
            verificationStatusMap.put(player.getName(), VerificationStatus.VERIFIED);
        } else {
            startPasswordVerificationProcess(player);
        }
    }

    private void executeCommandsAndBanPlayer(Player player, String banReason, String logReason) {
        List<String> commands = plugin.getMainManager().getConfigManager().getMainConfig().not_in_operators_list;
        CommandExecutor commandExecutor = new CommandExecutor(player, commands);
        Ban ban = new Ban(player, banReason, logReason);
        plugin.getMainManager().getLog().banned(player, logReason);
    }

    private boolean isPlayerVerified(Player player) {
        return verificationStatusMap.containsKey(player.getName()) && verificationStatusMap.get(player.getName()) == VerificationStatus.VERIFIED;
    }

    private void announceVerified(Player player) {
        VerifiedAnnouncer verifiedAnnouncer = new VerifiedAnnouncer(player);
    }

    private boolean isPlayerInPasswordVerification(Player player) {
        return verificationStatusMap.containsKey(player.getName()) && verificationStatusMap.get(player.getName()) == VerificationStatus.IN_PASSWORD_VERIFICATION;
    }

    private boolean isPlayerLoggedInUsingSameIp(Player player) {
        ipTable = plugin.getMainManager().getIpTable();
        return ipTable.IsContains(player) && ipTable.isIp(player);
    }

    private void startPasswordVerificationProcess(Player player) {
        verificationStatusMap.put(player.getName(), VerificationStatus.IN_PASSWORD_VERIFICATION);
        Lockdown lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        lockdown.lockPlayer(player);
        PasswordVerification passwordVerification = new PasswordVerification(player);
        passwordFlash = new PasswordFlash(player);
        passwordFlash.start();
    }

    public void setVerified(Player player) {
        passwordFlash.stopTasks();
        plugin.getMainManager().getAuthorizedPlayers().addAuthorizedPlayer(player);

        ipTable = plugin.getMainManager().getIpTable();
        ipTable.addIp(player);

        Lockdown lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        lockdown.unlockPlayer(player);

        announceVerified(player);
        plugin.getMainManager().getLog().authorized(player);
        verificationStatusMap.put(player.getName(), VerificationStatus.VERIFIED);
    }

    public PasswordFlash getPasswordFlash() {
        return passwordFlash;
    }

    public ConcurrentHashMap<String, VerificationStatus> getVerificationStatusMap() {
        return verificationStatusMap;
    }
}
