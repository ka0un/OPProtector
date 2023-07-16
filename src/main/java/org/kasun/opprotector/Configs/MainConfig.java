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
    interval_secounds;

    public boolean
    use_gui,
    block_player_move,
    block_break_block,
    block_place_block,
    block_item_drop,
    block_damage,
    allow_flight;

    public List<String>
    commands_whitelist;




    public MainConfig() {
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
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
        block_damage = lockdown.getBoolean("block-damage");
        allow_flight = lockdown.getBoolean("allow-flight");
        commands_whitelist = new ArrayList<>(lockdown.getStringList("commands-whitelist"));
    }




}


