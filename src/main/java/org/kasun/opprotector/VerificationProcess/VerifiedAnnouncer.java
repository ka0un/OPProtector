package org.kasun.opprotector.VerificationProcess;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitRunnable;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Utils.Prefix;


import java.util.Set;

public class VerifiedAnnouncer {
    OPProtector plugin = OPProtector.getInstance();

    public VerifiedAnnouncer(Player player) {



        sendTitile(player);


        Set<OfflinePlayer> operatorSet = plugin.getServer().getOperators();

        for (OfflinePlayer offlinePlayer : operatorSet) {
            if (offlinePlayer.isOnline()) {
                Player onlinePlayer = offlinePlayer.getPlayer();
                onlinePlayer.sendMessage(Prefix.ERROR + "[" + player.getName() + "] " + ChatColor.BLUE + "[" + player.getAddress().getHostName() + "]" + ChatColor.GRAY + " is Authorized, and now have Operator Access ✔");

            }
        }
    }


    private void sendTitile(Player player){

        //send animated title
        BukkitRunnable titleTask = new BukkitRunnable() {
            int tick = 0;
            String titlebefore = ChatColor.GOLD + "OP" + ChatColor.YELLOW + "P";
            String title = ChatColor.BOLD + titlebefore;

            @Override
            public void run() {
                if (tick < 1) {

                    player.sendTitle(title, ChatColor.GREEN + "█", 1, 10, 0);
                    sleep(200);

                    player.sendTitle(title, ChatColor.GREEN + "██", 0, 10, 0);
                    sleep(200);

                    player.sendTitle(title, ChatColor.GREEN + "███", 0, 10, 0);
                    sleep(200);

                    player.sendTitle(title, ChatColor.GREEN + "████", 0, 10, 0);
                    sleep(200);

                    player.sendTitle(title, ChatColor.GREEN + "█████", 0, 10, 0);
                    sleep(200);

                    player.sendTitle(title, ChatColor.GREEN + "██████", 0, 10, 0);
                    sleep(200);

                    player.sendTitle(title, ChatColor.GREEN + "███████", 0, 10, 0);
                    sleep(200);

                    player.sendTitle(title, ChatColor.GREEN + "████████", 0, 10, 0);
                    sleep(200);

                    player.sendTitle(title, ChatColor.GREEN + "█████████", 0, 10, 0);
                    sleep(200);

                    Sound sound = Sound.BLOCK_NOTE_BLOCK_HARP;
                    player.playSound(player.getLocation(), sound, 5, 1);

                    player.sendTitle(title, ChatColor.GREEN + "Authenticated!", 0, 100, 20);
                    tick++;
                } else {
                    // Animation is done, so cancel the task
                    this.cancel();
                }
            }

            private void sleep(int ms) {
                try{
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        titleTask.runTaskTimerAsynchronously(plugin, 0, 20*5); // 1 tick interval

    }
}
