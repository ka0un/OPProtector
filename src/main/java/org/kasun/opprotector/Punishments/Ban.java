package org.kasun.opprotector.Punishments;

import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;

public class Ban {
    private OPProtector plugin = OPProtector.getInstance();
    public Ban(Player player, String kickMessage, String banMessage){
        player.setOp(false);
        plugin.getServer().getBanList(org.bukkit.BanList.Type.NAME).addBan(player.getName(), banMessage, null, null);
        player.kickPlayer(kickMessage);
    }
}
