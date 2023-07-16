package org.kasun.opprotector.AuthObjects;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.kasun.opprotector.Configs.OperatorConfig;
import org.kasun.opprotector.OPProtector;

import java.util.List;

public class LiveScanner {
    public LiveScanner() {

        OPProtector plugin = OPProtector.getInstance();
        List<String> opList = OperatorConfig.getOperatorNames();


        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

                    });
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20L); // Run every second (20 ticks)
    }

}

