package org.kasun.opprotector.Configs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.opprotector.OPProtector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainConfig {
    private final OPProtector plugin = OPProtector.getInstance();
    private File configFile;
    private FileConfiguration config;

    public String
    prefix,
    pas_command;

    public int
    session_hours,
    interval_secounds,
    live_scanner_interval_secounds;

    public boolean
    use_gui,
    block_player_move,
    block_break_block,
    block_place_block,
    block_item_drop,
    block_item_pickup,
    block_damage,
    allow_flight,
    scan_on_player_op_command,
    scan_on_console_op_command,
    scan_from_live_scanner,
    scan_for_blacklisted_permissions,
    scan_for_gamemode_creative,
    scan_on_join;



    public List<String>
    commands_whitelist,
    not_in_operators_list,
    have_blacklisted_perms,
    admin_ip_changed,
    failed_password_timeout,
    blacklisted_permissions;




    public MainConfig() {
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        loadMainSettings();
        loadPasswordSettings();
        loadLockdownSettings();
        loadScannerSettings();
        loadCommandsSettings();
    }

    public void loadMainSettings(){
        ConfigurationSection main = config.getConfigurationSection("main-settings");
        prefix = main.getString("prefix");
    }

    public void loadPasswordSettings(){
        ConfigurationSection password = config.getConfigurationSection("password-settings");
        pas_command = password.getString("pas-command");
        session_hours = password.getInt("session-hours");
        interval_secounds = password.getInt("interval-secounds");
        use_gui = password.getBoolean("use-gui");
    }

    public void loadLockdownSettings(){
        ConfigurationSection lockdown = config.getConfigurationSection("lockdown-settings");
        block_player_move = lockdown.getBoolean("block-player-move");
        block_break_block = lockdown.getBoolean("block-break-block");
        block_place_block = lockdown.getBoolean("block-place-block");
        block_item_drop = lockdown.getBoolean("block-item-drop");
        block_item_pickup = lockdown.getBoolean("block-item-pickup");
        block_damage = lockdown.getBoolean("block-damage");
        allow_flight = lockdown.getBoolean("allow-flight");

        commands_whitelist = new ArrayList<>(lockdown.getStringList("commands-whitelist"));
    }

    public void loadScannerSettings(){
        ConfigurationSection scanner = config.getConfigurationSection("scanner-settings");
        scan_on_player_op_command = scanner.getBoolean("scan-on-player-op-command");
        scan_on_console_op_command = scanner.getBoolean("scan-on-console-op-command");
        scan_from_live_scanner = scanner.getBoolean("scan-from-live-scanner");
        scan_for_blacklisted_permissions = scanner.getBoolean("scan-for-blacklisted-permissions");
        scan_for_gamemode_creative = scanner.getBoolean("scan-for-gamemode-creative");
        scan_on_join = scanner.getBoolean("scan-on-join");
        blacklisted_permissions = new ArrayList<>(scanner.getStringList("blacklisted-permissions"));
        live_scanner_interval_secounds = scanner.getInt("live-scanner-interval-secounds");
    }

    public void loadCommandsSettings(){
        ConfigurationSection commands = config.getConfigurationSection("commands-settings");
        not_in_operators_list = new ArrayList<>(commands.getStringList("not-in-operators-list"));
        have_blacklisted_perms = new ArrayList<>(commands.getStringList("have-blacklisted-perms"));
        admin_ip_changed = new ArrayList<>(commands.getStringList("admin-ip-changed"));
        failed_password_timeout = new ArrayList<>(commands.getStringList("failed-password-timeout"));

    }




}


