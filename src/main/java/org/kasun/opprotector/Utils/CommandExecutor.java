package org.kasun.opprotector.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandExecutor {
    public CommandExecutor(Player player, List<String> commands) {
        if  (commands == null) {
            return;
        }
        for (String command : commands) {
            String replacedCommand = command.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacedCommand);
        }
    }
}

