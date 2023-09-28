package org.kasun.opprotector.Utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;

import java.util.UUID;

public class LuckpermsCheck {
    public static boolean hasPermission(UUID uuid, String permission) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getUserManager().loadUser(uuid).join();

        if (user == null) {
            return false;
        }

        for (Node node : user.getNodes()) {
            if (node.getType() == NodeType.PERMISSION && node.getKey().equals(permission)) {
                return true;
            }
        }

        return false;
    }
}
