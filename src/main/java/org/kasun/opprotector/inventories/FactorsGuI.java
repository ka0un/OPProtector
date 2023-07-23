package org.kasun.opprotector.inventories;


import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.HopperGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Utils.Prefix;
import org.kasun.opprotector.VerificationProcess.ConsoleAccessVerification;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;
import org.kasun.opprotector.VerificationProcess.VerificationStatus;

import java.util.ArrayList;
import java.util.List;

public class FactorsGuI {

    ItemStack itemStackC;
    ItemStack itemStackE;
    ItemStack itemStackD;
    VerificationProcessManager verificationProcessManager;

    public FactorsGuI() {

        verificationProcessManager = OPProtector.getInstance().getMainManager().getVerificationProcessManager();

        //console access verification button
        itemStackC = new ItemStack(Material.COAL_BLOCK);
        ItemMeta itemMetaC = itemStackC.getItemMeta();
        itemMetaC.setDisplayName(ChatColor.GREEN + "Verify Console Access");
        List<String> loreC = new ArrayList<>();
        loreC.add(ChatColor.GRAY + "Click to verify console access");
        itemMetaC.setLore(loreC);
        itemStackC.setItemMeta(itemMetaC);


        //Email verification button
        itemStackE = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta itemMetaE = itemStackE.getItemMeta();
        itemMetaE.setDisplayName(ChatColor.RED + "Verify Email");
        List<String> loreE = new ArrayList<>();
        loreE.add(ChatColor.GRAY + "Click to verify Email Address");
        itemMetaE.setLore(loreE);
        itemStackE.setItemMeta(itemMetaE);

        //Discord verification button
        itemStackD = new ItemStack(Material.LAPIS_BLOCK);
        ItemMeta itemMetaD = itemStackD.getItemMeta();
        itemMetaD.setDisplayName(ChatColor.BLUE + "Verify Discord");
        List<String> loreD = new ArrayList<>();
        loreD.add(ChatColor.GRAY + "Click to verify Discord Account");
        itemMetaD.setLore(loreD);
        itemStackD.setItemMeta(itemMetaD);

    }

    public void show(Player player){
        HopperGui hopperGui = new HopperGui("1 More Step Required");
        hopperGui.setOnGlobalClick(event -> event.setCancelled(true));

        GuiItem item1 = new GuiItem(itemStackC, event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            verificationProcessManager.getVerificationStatusMap().put(player.getName(), VerificationStatus.DOING_FACTOR_VERIFICATION);
            ConsoleAccessVerification consoleAccessVerification = new ConsoleAccessVerification(player);
        });
        GuiItem item2 = new GuiItem(itemStackE, event -> {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(Prefix.ERROR + "This feature is not available yet.");
        });
        GuiItem item3 = new GuiItem(itemStackD, event -> {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(Prefix.ERROR + "This feature is not available yet.");
        });

        hopperGui.setOnClose(event -> {
            if (verificationProcessManager.getVerificationStatusMap().get(player.getName()) != VerificationStatus.DOING_FACTOR_VERIFICATION){
                hopperGui.show(player);
            }
        });


        OutlinePane outlinePane = new OutlinePane(0, 0, 5, 1);
        outlinePane.addItem(item1);
        outlinePane.addItem(item2);
        outlinePane.addItem(item3);

        hopperGui.getSlotsComponent().addPane(outlinePane);


        hopperGui.show(player);

    }
}
