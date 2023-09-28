package org.kasun.opprotector.VerificationProcess;



import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.inventories.PasswordGui;




public class PasswordVerification {
    private OPProtector plugin = OPProtector.getInstance();
    private boolean isgui;
    public PasswordVerification(Player player) {
        isgui = plugin.getMainManager().getConfigManager().getMainConfig().use_gui;
        if (isgui) {
            String password = plugin.getMainManager().getConfigManager().getOperatorConfig().getOperator(player.getName()).getPassword();
            PasswordGui passwordGui = new PasswordGui("Enter The Password", password, player);
            passwordGui.show();
        }
    }

}
