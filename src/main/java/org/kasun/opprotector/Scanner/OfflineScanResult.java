package org.kasun.opprotector.Scanner;

import org.bukkit.OfflinePlayer;
import org.kasun.opprotector.Utils.OfflineScannerResultType;

import java.util.ArrayList;

public class OfflineScanResult {
    private OfflinePlayer player;
    private OfflineScannerResultType resultType;
    private String description;

    public OfflineScanResult(OfflinePlayer player, OfflineScannerResultType resultType, String description) {
        this.player = player;
        this.resultType = resultType;
        this.description = description;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public void setPlayer(OfflinePlayer player) {
        this.player = player;
    }

    public OfflineScannerResultType getResultType() {
        return resultType;
    }

    public void setResultType(OfflineScannerResultType resultType) {
        this.resultType = resultType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
