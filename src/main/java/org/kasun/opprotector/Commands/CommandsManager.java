package org.kasun.opprotector.Commands;

import org.kasun.opprotector.OPProtector;

public class CommandsManager {
    OPProtector plugin = OPProtector.getInstance();
    public CommandsManager() {
        String pas = plugin.getMainManager().getConfigManager().getMainConfig().pas_command;
        plugin.getCommand(pas).setExecutor(new Pas());
    }
}
