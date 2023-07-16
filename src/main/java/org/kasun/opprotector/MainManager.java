package org.kasun.opprotector;

import org.kasun.opprotector.AuthObjects.TempAuth;
import org.kasun.opprotector.Commands.CommandsManager;
import org.kasun.opprotector.Configs.ConfigManager;
import org.kasun.opprotector.Listners.ListnerManager;
import org.kasun.opprotector.Punishments.PunishmentManager;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

public class MainManager {
    ConfigManager configManager;
    ListnerManager listnerManager;
    PunishmentManager punishmentManager;
    TempAuth tempAuth;
    CommandsManager commandsManager;

    VerificationProcessManager verificationProcessManager;
    OPProtector plugin = OPProtector.getInstance();
    public MainManager() {
        configManager = new ConfigManager();
        listnerManager = new ListnerManager();
        punishmentManager = new PunishmentManager();
        commandsManager = new CommandsManager();
        tempAuth = new TempAuth();
        verificationProcessManager = new VerificationProcessManager();

    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ListnerManager getListnerManager() {
        return listnerManager;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public TempAuth getAuthorizedPlayers() {
        return tempAuth;
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }

    public VerificationProcessManager getVerificationProcessManager() {
        return verificationProcessManager;
    }

}
