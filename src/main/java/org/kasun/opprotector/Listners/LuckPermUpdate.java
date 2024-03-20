package org.kasun.opprotector.Listners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Punishments.Ban;
import org.kasun.opprotector.Utils.CommandExecutor;
import org.kasun.opprotector.VerificationProcess.VerificationStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LuckPermUpdate{

    OPProtector plugin = OPProtector.getInstance();

    public LuckPermUpdate() {

        if (!isLuckPermsInstalledAndEnabled()) {
            return;
        }

        LuckPerms api = LuckPermsProvider.get();
        EventBus eventBus = api.getEventBus();
        eventBus.subscribe(NodeMutateEvent.class, this::onNodeMutateEvent);
    }


    private void onNodeMutateEvent(NodeMutateEvent event) {

        if (event.isUser()) {

            User user = (User) event.getTarget();

            if (Bukkit.getPlayer(user.getUniqueId()) == null) {
                return;
            }


            Player player = Bukkit.getPlayer(user.getUniqueId());

            ConcurrentHashMap<String, VerificationStatus> verificationStatusMap = plugin.getMainManager().getVerificationProcessManager().getVerificationStatusMap();

            VerificationStatus verificationStatus = verificationStatusMap.getOrDefault(player.getName(), VerificationStatus.NOT_VERIFIED);

            if (verificationStatus == VerificationStatus.VERIFIED || verificationStatus == VerificationStatus.IN_PASSWORD_VERIFICATION) {
                return;
            }

            List<String> blacklistedPermissions = plugin.getMainManager().getConfigManager().getMainConfig().blacklisted_permissions;
            boolean allowScanBlacklistedPerms = plugin.getMainManager().getConfigManager().getMainConfig().scan_for_blacklisted_permissions;

            Set<Node> dataBefore = event.getDataBefore();
            Set<Node> dataAfter = event.getDataAfter();

            // Find added permissions
            Set<Node> addedPermissions = new HashSet<>(dataAfter);
            addedPermissions.removeAll(dataBefore);



            for (Node addedPermission : addedPermissions) {
                String permission = addedPermission.getKey();

                if (blacklistedPermissions.contains(permission) && allowScanBlacklistedPerms) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            executeCommandsAndBanPlayer(player, "Blacklisted Permission", "Blacklisted Permission: " + permission, plugin);
                        }
                    }.runTask(plugin);
                }
            }

        }
    }

    private boolean isLuckPermsInstalledAndEnabled() {
        Plugin luckPermsPlugin = Bukkit.getPluginManager().getPlugin("LuckPerms");
        return luckPermsPlugin != null && luckPermsPlugin.isEnabled();
    }

    private void executeCommandsAndBanPlayer(Player player, String banReason, String logReason, OPProtector plugin) {

        if (player == null){
            return;
        }

        List<String> commands = plugin.getMainManager().getConfigManager().getMainConfig().not_in_operators_list;
        CommandExecutor commandExecutor = new CommandExecutor(player, commands);
        Ban ban = new Ban(player, banReason, logReason);
        plugin.getMainManager().getLog().banned(player, logReason);
    }
}
