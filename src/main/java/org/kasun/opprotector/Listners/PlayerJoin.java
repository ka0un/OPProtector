package org.kasun.opprotector.Listners;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Utils.BungeeGuardChecker;
import org.kasun.opprotector.Utils.Log;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;


public class PlayerJoin implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        OPProtector plugin = OPProtector.getInstance();
        Log log = plugin.getMainManager().getLog();
        boolean allow = plugin.getMainManager().getConfigManager().getMainConfig().scan_on_join;
        boolean scanFromLiveScan = plugin.getMainManager().getConfigManager().getMainConfig().scan_from_live_scanner;

        if (plugin.getServer().getOnlinePlayers().size() == 1 && scanFromLiveScan){
            BungeeGuardChecker.checkAndNotify();
            plugin.getMainManager().getLiveScanner().start();
        }

        if (!allow) {return;}
        Player player = e.getPlayer();
        VerificationProcessManager verificationProcessManager = plugin.getMainManager().getVerificationProcessManager();
        verificationProcessManager.start(player);

        if (player.isOp()) {
            log.login(player);
        }
    }

}
