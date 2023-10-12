package org.kasun.opprotector.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


import java.util.List;

public class ScanResults {
    private List<OfflineScanResult> resultList;
    private CommandSender sender;

    public ScanResults(List<OfflineScanResult> resultList, CommandSender sender) {
        this.resultList = resultList;
        this.sender = sender;
        showResultsWithChat();
    }


    private void showResultsWithChat(){
        sender.sendMessage(ChatColor.RED + "Total Players Detected: " + resultList.size());
        sender.sendMessage(" ");
        for (OfflineScanResult result : resultList) {
            sender.sendMessage( ChatColor.GOLD + result.getPlayer().getName() + " - " + ChatColor.YELLOW +  result.getResultType().toString() + " - " + ChatColor.WHITE + result.getDescription());
        }
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.RED +  "Use /opp banall to ban all detected players");
    }

}


