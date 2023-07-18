package org.kasun.opprotector.VerificationProcess;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.entity.Player;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Utils.Colors;
import org.kasun.opprotector.inventories.ConsoleAccessVerificationGui;

import java.util.ArrayList;

public class ConsoleAccessVerification {
    private OPProtector plugin = OPProtector.getInstance();
    private String otp;

    public ConsoleAccessVerification(Player player) {
        otp = RandomStringUtils.randomAlphanumeric(5);
        sendOTP(player);
        ConsoleAccessVerificationGui consoleAccessVerificationGui = new ConsoleAccessVerificationGui(player, otp);
        consoleAccessVerificationGui.show();
    }

    private void sendOTP(Player player) {
        plugin.getServer().getConsoleSender().sendMessage(Colors.DARK_GRAY + "================================================");
        plugin.getServer().getConsoleSender().sendMessage(Colors.GREEN + "OPProtector Console Access Verification");
        plugin.getServer().getConsoleSender().sendMessage(Colors.GREEN + "Name: " + player.getName());
        plugin.getServer().getConsoleSender().sendMessage(Colors.GREEN+ "OTP: " + Colors.BLINK + "► " + Colors.RESET + Colors.ENCIRCLED + otp + Colors.GREEN + Colors.BLINK + " ◄");
        plugin.getServer().getConsoleSender().sendMessage(Colors.DARK_GRAY + "================================================");
    }


}
