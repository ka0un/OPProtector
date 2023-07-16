package org.kasun.opprotector.inventories;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.plugin.Plugin;
import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.VerificationProcess.VerificationProcessManager;

public class PasswordGui {
    private String title;
    private String correctPassword;
    private Player player;
    private boolean isPasswordCorrect = false;
    private int attempts = 0;
    private VerificationProcessManager verificationProcessManager;

    public PasswordGui(String title, String correctPassword, Player player) {
        this.title = title;
        this.correctPassword = correctPassword;
        this.player = player;
        verificationProcessManager = OPProtector.getInstance().getMainManager().getVerificationProcessManager();
    }

    public void show(){
        AnvilGui gui = new AnvilGui(title);
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
            if (gui.getRenameText().equals(correctPassword) || gui.getRenameText().equals(" " + correctPassword)){

                isPasswordCorrect = true;
                player.closeInventory();
                verificationProcessManager.next(player);

            }else{
                isPasswordCorrect = false;
                player.sendMessage(ChatColor.RED + "You have entered the wrong password.");
            }
        });
        StaticPane pane2 = new StaticPane(0, 0, 1, 1);
        pane2.addItem(item2, 0, 0);
        gui.getResultComponent().addPane(pane2);

        gui.setOnNameInputChanged(event -> {
            attempts++;
            if (event.equals(correctPassword) || event.equals(" " + correctPassword)){
                if (attempts < 20){
                    isPasswordCorrect = true;
                    player.closeInventory();
                    verificationProcessManager.next(player);
                }
            }
        });
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnClose(event -> {
            OPProtector plugin = OPProtector.getInstance();
            if (!plugin.getMainManager().getAuthorizedPlayers().isAuthorizedPlayer(player)){
                gui.show(player);
            }
        });
        gui.show(player);

    }


}
