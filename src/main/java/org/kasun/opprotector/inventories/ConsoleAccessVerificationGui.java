package org.kasun.opprotector.inventories;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Utils.Prefix;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConsoleAccessVerificationGui {
    String otp;
    Player player;
    int attempts;

    VerificationProcessManager verificationProcessManager;

    public ConsoleAccessVerificationGui(Player player, String otp){
        this.player = player;
        this.otp = otp;
        verificationProcessManager = OPProtector.getInstance().getMainManager().getVerificationProcessManager();
    }
    public void show(){
        AtomicBoolean lockClose = new AtomicBoolean(true);

        AnvilGui gui = new AnvilGui("Enter The OTP");
        ItemStack itemStack1 = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        itemMeta1.setDisplayName(" ");
        itemStack1.setItemMeta(itemMeta1);

        GuiItem item1 = new GuiItem(itemStack1, event -> event.setCancelled(true));
        StaticPane pane1 = new StaticPane(0, 0, 1, 1);
        pane1.addItem(item1, 0, 0);
        gui.getFirstItemComponent().addPane(pane1);

        ItemStack itemStack2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(ChatColor.GREEN + "Enter");
        itemStack2.setItemMeta(itemMeta2);

        GuiItem item2 = new GuiItem(itemStack2, event -> {
            attempts++;
            if (gui.getRenameText().equals(otp) || gui.getRenameText().equals(" " + otp)){
                lockClose.set(false);
                player.closeInventory();
                verificationProcessManager.setVerified(player);
            }else{
                player.sendMessage(Prefix.ERROR + "You have entered the wrong OTP.");
            }
        });
        StaticPane pane2 = new StaticPane(0, 0, 1, 1);
        pane2.addItem(item2, 0, 0);
        gui.getResultComponent().addPane(pane2);

        gui.setOnNameInputChanged(event -> {
            attempts++;
            if (event.equals(otp) || event.equals(" " + otp)){
                if (attempts < 10){
                    lockClose.set(false);
                    player.closeInventory();
                    verificationProcessManager.setVerified(player);
                }
            }
        });
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnClose(event -> {
            OPProtector plugin = OPProtector.getInstance();
            if (lockClose.get()){
                gui.show(player);
            }
        });
        gui.show(player);

    }
}


