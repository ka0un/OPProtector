package org.kasun.opprotector.Commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Scanner.OfflineScanResult;
import org.kasun.opprotector.Scanner.OfflineScanner;
import org.kasun.opprotector.Utils.Prefix;

import java.util.ArrayList;
import java.util.List;

public class OppCommand implements TabExecutor {

    private final OPProtector plugin = OPProtector.getInstance();
    private CommandsManager commandsManager;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if(!sender.hasPermission("opp-admin")) {
                sender.sendMessage(Prefix.ERROR + "No Permission ! [opp-admin]");
                return true;
            }
            sendhelp(sender);
            return true;
        }

        //Scan Command
        if (args[0].equalsIgnoreCase("scan")) {
            if(!sender.hasPermission("opp-admin")) {
                sender.sendMessage(Prefix.ERROR + "No Permission ! [opp-admin]");
                return true;
            }

            OfflineScanner offlineScanner = new OfflineScanner(sender);

        }

        //Help Command
        if (args[0].equalsIgnoreCase("help")) {
            if(!sender.hasPermission("opp-admin")) {
                sender.sendMessage(Prefix.ERROR + "No Permission ! [opp-admin]");
                return true;
            }
            sendhelp(sender);
        }

        //Reload Command
        if (args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission("opp-admin")) {
                sender.sendMessage(Prefix.ERROR + "No Permission ! [opp-admin]");
                return true;
            }
            plugin.getMainManager().reload();
            sender.sendMessage(Prefix.SUCCESS + "OPProtector Reloaded");
        }

        if (args[0].equalsIgnoreCase("banall")) {
            if(!sender.hasPermission("opp-admin")) {
                sender.sendMessage(Prefix.ERROR + "No Permission ! [opp-admin]");
                return true;
            }
            List<OfflineScanResult> resultList =  plugin.getMainManager().getOfflinePlayerScanResultList();
            if (resultList.size() == 0){
                sender.sendMessage(Prefix.SUCCESS + "No Players Detected");
                return true;
            }else{
                for (OfflineScanResult result : resultList) {
                    OfflinePlayer player = result.getPlayer();
                    plugin.getServer().getBanList(org.bukkit.BanList.Type.NAME).addBan(player.getName(), "OPProtector Detected Unauthorized OP", null, "OPProtector");
                }
                sender.sendMessage(Prefix.SUCCESS + "Banned " + resultList.size() + " detected players");
                plugin.getMainManager().getOfflinePlayerScanResultList().clear();
            }
        }
        return true;
    }

    private void sendhelp(CommandSender sender) {
        //Border
        sender.sendMessage(ChatColor.YELLOW + "============================================");

        sender.sendMessage(ChatColor.YELLOW + "");

        //Info
        sender.sendMessage(ChatColor.GOLD +  "OPProtector v" + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.YELLOW + "Author: " + ChatColor.WHITE +  plugin.getDescription().getAuthors());
        sender.sendMessage(ChatColor.YELLOW + "Website: " + ChatColor.WHITE + plugin.getDescription().getWebsite());

        //discord
        sender.sendMessage(ChatColor.YELLOW + "Discord: "+ ChatColor.WHITE +" https://dsc.gg/sundevs");

        sender.sendMessage(ChatColor.YELLOW + "");

        //Commands
        sender.sendMessage(ChatColor.GOLD + "Commands:");
        sender.sendMessage(ChatColor.YELLOW + "/opp help "+ ChatColor.WHITE +"- View This info");
        sender.sendMessage(ChatColor.YELLOW + "/opp scan "+ ChatColor.WHITE +"- Scan all Offline players for Unauthorized OPs");
        sender.sendMessage(ChatColor.YELLOW + "/opp reload "+ ChatColor.WHITE +"- Reload the plugin");

        sender.sendMessage(ChatColor.YELLOW + "");
        //permissions
        sender.sendMessage(ChatColor.GOLD + "Permissions:");
        sender.sendMessage(ChatColor.YELLOW + "opp-admin "+ ChatColor.WHITE +"- Access to all commands");

        sender.sendMessage(ChatColor.YELLOW + "");

        //copyright text
        sender.sendMessage(ChatColor.YELLOW + "OPProtector is licensed under the MIT License");
        sender.sendMessage(ChatColor.YELLOW + "OPProtector@Sundevs 2023");

        sender.sendMessage(ChatColor.YELLOW + "");

        //Border
        sender.sendMessage(ChatColor.YELLOW + "============================================");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("scan");
            arguments.add("reload");
            return arguments;
        }
        return null;
    }
}
