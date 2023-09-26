package org.kasun.opprotector.Commands;

import org.kasun.opprotector.Configs.MainConfig;
import org.kasun.opprotector.OPProtector;

public class CommandsManager {
    public CommandsManager() {
        OPProtector plugin = OPProtector.getInstance();
        plugin.getCommand("pas").setExecutor(new Pas());
        plugin.getCommand("opp").setExecutor(new OppCommand());
    }
}
