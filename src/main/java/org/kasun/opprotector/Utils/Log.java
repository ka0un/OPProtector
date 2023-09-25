package org.kasun.opprotector.Utils;

import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Log {
    private File logFile;

    private HashMap<Timestamp, String> cache;




    OPProtector plugin = OPProtector.getInstance();
    public Log() {
        cache = new HashMap<>();
        // Get the current date and time
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = dateFormat.format(currentDate);

        // Create the logs folder if it doesn't exist
        File logsFolder = new File(plugin.getDataFolder(), "logs");
        if (!logsFolder.exists()) {
            logsFolder.mkdirs();
        }

        // Create the file with the current date and time as the name
        logFile = new File(logsFolder, formattedDate + ".txt");

        try {
            // Create the file and write some initial content
            logFile.createNewFile();
            FileWriter writer = new FileWriter(logFile);
            writer.write("This is your log file for " + formattedDate + "\n");
            writer.close();
            // You can continue writing to the log file as needed.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message, boolean timestamp, boolean border) {
        try {
            FileWriter writer = new FileWriter(logFile, true); // Open the file in append mode
            if (border) {
                writer.write("--------------------------------------------------\n");
            }
            if (timestamp) {
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                String formattedDate = dateFormat.format(currentDate);
                writer.write("[" + formattedDate + "] " + message + "\n");
            } else {
                writer.write(message + "\n");
            }
            if (border) {
                writer.write("--------------------------------------------------\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void authorized(Player player) {
        log("Operator [" + player.getName() + "] [" + player.getAddress().getHostName() + "] has been authorized.", true, false);
    }

    public void banned(Player player, String reason) {
        log("[" + player.getName() + "] [" + player.getAddress().getHostName() + "] has been banned. Reason: " + reason, true, true);
    }

    public void failedPassword(Player player) {
        log("Operator [" + player.getName() + "] [" + player.getAddress().getHostName() + "] has failed to enter the password.", true, false);
    }

    public void failedFactor(Player player) {
        log("Operator [" + player.getName() + "] [" + player.getAddress().getHostName() + "] has failed to enter the factor.", true, false);
    }

    public void loginfromDifferntIP(Player player, String oldIP, String newIP) {
        log("Operator [" + player.getName() + "] [" + oldIP + "] has logged in from a different IP [" + newIP + "] and Forced to perform Factor Verification.", true, false);
    }

    public void login(Player player) {
        log("Operator [" + player.getName() + "] [" + player.getAddress().getHostName() + "] has logged in.", true, false);
    }

    public void logout(Player player) {
        log("Operator [" + player.getName() + "] [" + player.getAddress().getHostName() + "] has logged out.", true, false);
    }

    public void command(Player player, String command) {

        if (cached("[" + player.getName() + "] >> " + command)){
            return;
        }

        for (String commandBlacklist : plugin.getMainManager().getConfigManager().getMainConfig().commands_whitelist){
            if (command.startsWith(commandBlacklist)){
                return;
            }
        }

        log("[" + player.getName() + "] >> " + command, true, false);
    }

    public void message(Player player, String message) {
        if (cached("[" + player.getName() + "] >> " + message)){
            return;
        }
        log("[" + player.getName() + "] >> " + message, true, false);
    }

    private boolean cached(String message){

        //if theres something older than 3 secounds in the cache remove them
        if (cache.keySet() != null){
            for (Timestamp timestamp : cache.keySet()){
                if (timestamp.getTime() < System.currentTimeMillis() - 3000){
                    cache.remove(timestamp);
                }
            }
        }


        for (String cachedMessage : cache.values()){
            if (cachedMessage.equals(message)){
                return true;
            }
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cache.put(timestamp, message);

        return false;
    }

}
