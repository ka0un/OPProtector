package org.kasun.opprotector.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasun.opprotector.Configs.OperatorConfig;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.VerificationProcess.PasswordVerification;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

public class Pas implements CommandExecutor {
    OPProtector plugin = OPProtector.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 1) {
                if (OperatorConfig.getOperatorNames().contains(player.getName())){
                    if (plugin.getMainManager().getAuthorizedPlayers().isAuthorizedPlayer(player)) {
                        player.sendMessage("You are already authorized.");
                    }else {
                        String password = args[0];
                        String correctPassword = OperatorConfig.getOperatorConfig(player.getName()).getPassword();
                        if (password.equals(correctPassword)) {
                            VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
                            verificationProcessManager.next(player);
                            return true;
                        } else {
                            player.sendMessage("Incorrect password.");
                            return true;
                        }
                    }
                }else{
                    player.sendMessage("You dont have permission to use this command.");
                    return true;
                }
            }
        }
        return false;
    }
}
