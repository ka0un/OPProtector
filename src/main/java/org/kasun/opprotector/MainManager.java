package org.kasun.opprotector;

import org.bukkit.command.ConsoleCommandSender;
import org.kasun.opprotector.AuthObjects.IpTable;
import org.kasun.opprotector.Listners.LuckPermUpdate;
import org.kasun.opprotector.Scanner.LiveScanner;
import org.kasun.opprotector.AuthObjects.TempAuth;
import org.kasun.opprotector.Commands.CommandsManager;
import org.kasun.opprotector.Configs.ConfigManager;
import org.kasun.opprotector.Listners.ListnerManager;
import org.kasun.opprotector.Punishments.PunishmentManager;
import org.kasun.opprotector.Scanner.OfflineScanResult;
import org.kasun.opprotector.Utils.Log;
import org.kasun.opprotector.Utils.UpdateChecker;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class MainManager {
    private ConfigManager configManager;
    private ListnerManager listnerManager;
    private PunishmentManager punishmentManager;
    private TempAuth tempAuth;
    private CommandsManager commandsManager;
    private VerificationProcessManager verificationProcessManager;
    private LiveScanner liveScanner;
    private IpTable ipTable;
    private Log log;
    private UpdateChecker updateChecker;
    private List<OfflineScanResult> offlinePlayerScanResultList;
    private LuckPermUpdate luckPermUpdate;
    OPProtector plugin = OPProtector.getInstance();
    public MainManager() {
        configManager = new ConfigManager();
        listnerManager = new ListnerManager();
        punishmentManager = new PunishmentManager();
        commandsManager = new CommandsManager();
        tempAuth = new TempAuth();
        verificationProcessManager = new VerificationProcessManager();
        liveScanner = new LiveScanner(configManager);
        ipTable = new IpTable();
        log = new Log();
        updateChecker = new UpdateChecker(plugin, "https://raw.githubusercontent.com/ka0un/OPProtector/master/ver.txt", plugin.getDescription().getVersion());
        offlinePlayerScanResultList = new ArrayList<>();
        luckPermUpdate = new LuckPermUpdate();
    }

    public void reload() {
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        setConfigManager(new ConfigManager());
        setListnerManager(new ListnerManager());
        setPunishmentManager(new PunishmentManager());
        setCommandsManager(new CommandsManager());
        setTempAuth(new TempAuth());
        setVerificationProcessManager(new VerificationProcessManager());
        setLiveScanner(new LiveScanner(configManager));
        setIpTable(new IpTable());
        setLog(new Log());
        setUpdateChecker(new UpdateChecker(plugin, "https://github.com/ka0un/OPProtector/blob/master/ver.txt", plugin.getDescription().getVersion()));
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        offlinePlayerScanResultList = new ArrayList<>();
        luckPermUpdate = new LuckPermUpdate();
    }

    public List<OfflineScanResult> getOfflinePlayerScanResultList() {
        return offlinePlayerScanResultList;
    }

    public void setOfflinePlayerScanResultList(List<OfflineScanResult> offlinePlayerScanResultList) {
        this.offlinePlayerScanResultList = offlinePlayerScanResultList;
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

    public LiveScanner getLiveScanner() {
        return liveScanner;
    }

    public IpTable getIpTable() {
        return ipTable;
    }

    public Log getLog() {
        return log;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setListnerManager(ListnerManager listnerManager) {
        this.listnerManager = listnerManager;
    }

    public void setPunishmentManager(PunishmentManager punishmentManager) {
        this.punishmentManager = punishmentManager;
    }

    public void setTempAuth(TempAuth tempAuth) {
        this.tempAuth = tempAuth;
    }

    public void setCommandsManager(CommandsManager commandsManager) {
        this.commandsManager = commandsManager;
    }

    public void setVerificationProcessManager(VerificationProcessManager verificationProcessManager) {
        this.verificationProcessManager = verificationProcessManager;
    }

    public void setLiveScanner(LiveScanner liveScanner) {
        this.liveScanner = liveScanner;
    }

    public void setIpTable(IpTable ipTable) {
        this.ipTable = ipTable;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public void setUpdateChecker(UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
    }
}
