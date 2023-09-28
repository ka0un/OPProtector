package org.kasun.opprotector.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Utils.Prefix;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

public class Pas implements CommandExecutor {
    OPProtector plugin = OPProtector.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 1) {
                if (plugin.getMainManager().getConfigManager().getOperatorConfig().isContains(player.getName())) {
                    if (plugin.getMainManager().getAuthorizedPlayers().isAuthorizedPlayer(player)) {
                        player.sendMessage(Prefix.SUCCESS + "You are already authorized.");
                        return true;
                    }else {
                        String password = args[0];
                        String correctPassword = plugin.getMainManager().getConfigManager().getOperatorConfig().getOperator(player.getName()).getPassword();
                        if (password.equals(correctPassword)) {
                            VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
                            verificationProcessManager.setTo2FA(player);
                            return true;
                        } else {
                            player.sendMessage(Prefix.ERROR + "Incorrect password.");
                            return true;
                        }
                    }
                }else{
                    player.sendMessage(Prefix.INFO + "You dont have permission to use this command.");
                    return true;
                }
            }
        }
        return true;
    }
}
