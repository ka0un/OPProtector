package org.kasun.opprotector.VerificationProcess;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.kasun.opprotector.Configs.OperatorConfig;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.inventories.PasswordGui;

import java.util.ArrayList;


public class PasswordVerification {
    private OPProtector plugin = OPProtector.getInstance();
    private boolean isgui;
    public PasswordVerification(Player player) {
        isgui = plugin.getMainManager().getConfigManager().getMainConfig().use_gui;
        if (isgui) {
            String password = OperatorConfig.getOperatorConfig(player.getName()).getPassword();
            PasswordGui passwordGui = new PasswordGui("Enter The Password", password, player);
            passwordGui.show();
        }else{
            player.sendMessage("Enter the password.");

        }
    }

}
