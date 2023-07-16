package org.kasun.opprotector.VerificationProcess;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.kasun.opprotector.Commands.Pas;
import org.kasun.opprotector.Configs.OperatorConfig;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Ban;
import org.kasun.opprotector.Punishments.Lockdown;

import java.util.ArrayList;
import java.util.List;

public class VerificationProcessManager {
    static private OPProtector plugin = OPProtector.getInstance();
    private ArrayList<String> blacklistedPermissions;
    static PasswordFlash passwordFlash;
    private ArrayList<Player> inVerification;
    private ArrayList<Player> verifiedPlayers;


    public VerificationProcessManager(){
        inVerification = new ArrayList<>();
        verifiedPlayers = new ArrayList<>();
        blacklistedPermissions = new ArrayList<>();
    }

    public void start(Player player){
        List<String> opnames = OperatorConfig.getOperatorNames();

        //checking for blacklisted permissions
        for (String permission : blacklistedPermissions){
            try{
                if (player.hasPermission(permission)){
                    if (!opnames.contains(player.getName())){
                        Ban ban = new Ban(player, "You arent listed in OPProtector/operators.yml", "Unautharized Access");
                    }
                }
            }catch (NullPointerException ignored){}
        }

        //checking for op
        if (!player.isOp() && !(player.getGameMode() == GameMode.CREATIVE)){
            return;
        }

        //checking if player is listed in operators.yml
        if (!opnames.contains(player.getName())){
            Ban ban = new Ban(player, "You arent listed in OPProtector/operators.yml", "Unautharized Access");
        }

        //checking if player is already verified
        if (plugin.getMainManager().getAuthorizedPlayers().isAuthorizedPlayer(player)){
            VerifiedAnnouncer verifiedAnnouncer = new VerifiedAnnouncer(player);
            verifiedPlayers.add(player);
            return;
        }

        //checking if player is in verification process
        if (inVerification.contains(player)){
            return;
        }

        //start password verification process
        inVerification.add(player);
        Lockdown lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        lockdown.lockPlayer(player);
        PasswordVerification passwordVerification = new PasswordVerification(player);
        passwordFlash = new PasswordFlash(player);
        passwordFlash.start();

    }

    public void next(Player player){
        passwordFlash.stopTasks();
        Lockdown lockdown = plugin.getMainManager().getPunishmentManager().getLockdown();
        lockdown.unlockPlayer(player);
        plugin.getMainManager().getAuthorizedPlayers().addAuthorizedPlayer(player);
        VerifiedAnnouncer verifiedAnnouncer = new VerifiedAnnouncer(player);
        inVerification.remove(player);
        verifiedPlayers.add(player);
    }

    public ArrayList<Player> getInVerification() {
        return inVerification;
    }
    public boolean isInVerification(Player player){
        return inVerification.contains(player);
    }

    public PasswordFlash getPasswordFlash() {
        return passwordFlash;
    }
    public ArrayList<Player> getVerifiedPlayers() {
        return verifiedPlayers;
    }

}
