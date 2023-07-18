package org.kasun.opprotector.VerificationProcess;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.kasun.opprotector.Configs.OperatorConfig;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Ban;
import org.kasun.opprotector.Punishments.Lockdown;
import org.kasun.opprotector.Utils.CommandExecutor;
import org.kasun.opprotector.inventories.FactorsGuI;

import java.util.HashMap;
import java.util.List;

public class VerificationProcessManager {
    static private OPProtector plugin = OPProtector.getInstance();
    private List<String> blacklistedPermissions;
    static PasswordFlash passwordFlash;
    private HashMap<String, VerificationStatus> verificationStatusMap;
    private boolean allowScanBlackListedPerms;
    private boolean allowScanCreative;



    public VerificationProcessManager(){
        verificationStatusMap = new HashMap<>();
    }

    public void start(Player player){
        blacklistedPermissions = plugin.getMainManager().getConfigManager().getMainConfig().blacklisted_permissions;
        allowScanCreative = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_gamemode_creative;
        allowScanBlackListedPerms = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_blacklisted_permissions;

        List<String> opnames = OperatorConfig.getOperatorNames();

        //checking for blacklisted permissions
        if (allowScanBlackListedPerms){
            for (String permission : blacklistedPermissions){
                try{
                    if (player.hasPermission(permission)){
                        if (!opnames.contains(player.getName())){
                            List<String> commands = plugin.getMainManager().getConfigManager().getMainConfig().have_blacklisted_perms;
                            CommandExecutor commandExecutor = new CommandExecutor(player, commands);
                            Ban ban = new Ban(player, "You arent listed in OPProtector/operators.yml", "Unautharized Access");
                        }
                    }
                }catch (NullPointerException ignored){}
            }
        }

        //checking for op
        if (!player.isOp() && !(player.getGameMode() == GameMode.CREATIVE && allowScanCreative)){
            return;
        }

        //checking if player is listed in operators.yml
        if (!opnames.contains(player.getName())){
            List<String> commands = plugin.getMainManager().getConfigManager().getMainConfig().not_in_operators_list;
            CommandExecutor commandExecutor = new CommandExecutor(player, commands);
            Ban ban = new Ban(player, "You arent listed in OPProtector/operators.yml", "Unautharized Access");
        }

        //checking if player is already verified
        if (plugin.getMainManager().getAuthorizedPlayers().isAuthorizedPlayer(player)){
            if (verificationStatusMap.containsKey(player.getName()) && verificationStatusMap.get(player.getName()) == VerificationStatus.VERIFIED){
                VerifiedAnnouncer verifiedAnnouncer = new VerifiedAnnouncer(player);
                return;
            }
        }
        System.out.println("doing");

        //checking if player is in verification process
        if (verificationStatusMap.containsKey(player.getName()) && verificationStatusMap.get(player.getName()) == VerificationStatus.IN_PASSWORD_VERIFICATION || verificationStatusMap.get(player.getName()) == VerificationStatus.IN_FACTOR_VERIFICATION || verificationStatusMap.get(player.getName()) == VerificationStatus.DOING_FACTOR_VERIFICATION){
            return;
        }

        System.out.println("bad");

        //start password verification process
        verificationStatusMap.put(player.getName(), VerificationStatus.IN_PASSWORD_VERIFICATION);
        Lockdown lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        lockdown.lockPlayer(player);
        PasswordVerification passwordVerification = new PasswordVerification(player);
        passwordFlash = new PasswordFlash(player);
        passwordFlash.start();

    }

    public void setTo2FA(Player player){
        passwordFlash.stopTasks();
        verificationStatusMap.put(player.getName(), VerificationStatus.IN_FACTOR_VERIFICATION);
        plugin.getMainManager().getAuthorizedPlayers().addAuthorizedPlayer(player);
        FactorsGuI factorsGuI = new FactorsGuI();
        factorsGuI.show(player);
    }

    public void setVerified(Player player){
        Lockdown lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        lockdown.unlockPlayer(player);
        VerifiedAnnouncer verifiedAnnouncer = new VerifiedAnnouncer(player);
        verificationStatusMap.put(player.getName(), VerificationStatus.VERIFIED);
    }


    public PasswordFlash getPasswordFlash() {
        return passwordFlash;
    }

    public HashMap<String, VerificationStatus> getVerificationStatusMap() {
        return verificationStatusMap;
    }

}
