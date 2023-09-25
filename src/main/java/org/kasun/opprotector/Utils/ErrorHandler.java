package org.kasun.opprotector.Utils;

import org.kasun.opprotector.OPProtector;

public class ErrorHandler{
    private static OPProtector plugin;
    public static void handleError(String code){
        plugin = OPProtector.getInstance();
        plugin.getLogger().severe("=========================================================");
        plugin.getLogger().severe("An error occurred.");
        plugin.getLogger().severe("Error Code : " + code);
        plugin.getLogger().severe("Please check the error and report it to the developer.");
        plugin.getLogger().severe("Discord Server : https://dsc.gg/sundevs");
        plugin.getLogger().severe("=========================================================");
    }
}
